package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Activity;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "schutzgeld", aliases = {"sgeld"}, usage = "[Spieler] [Preis] [Screenshot]")
public class ProtectionMoneyCommand extends UnicacityCommand {
    private final UnicacityAddon unicacityAddon;

    public ProtectionMoneyCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 3) {
            sendUsage();
            return true;
        }

        new Thread(() -> {
            String playerName = arguments[0];
            int price = Integer.parseInt(arguments[1]);
            String screenshot = arguments[2];

            String info = ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                    .activity(Activity.PROTECTION_MONEY)
                    .type(playerName)
                    .value(String.valueOf(price))
                    .screenshot(screenshot)
                    .send().getInfo();

            //TODO: Weiterleiten an /activity/add?passwort={pw}&member={uuid}&specialisedType={playerName}&value={price}&screenshot={screenshot}

            p.sendAPIMessage(info, true);
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .build();
    }
}
