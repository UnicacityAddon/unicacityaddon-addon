package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.List;
import java.util.Random;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "resetpassword", usage = "[Player]")
public class ResetPasswortCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ResetPasswortCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {

            if (arguments.length < 1) {
                sendUsage();
                return;
            }

            int password = new Random().nextInt(900000) + 100000;

            //TODO: API Abfrage senden
            //this.unicacityAddon.api().sendBannerAddRequest(member, password);
            p.sendMessage(Message.getBuilder().of("Du hast das Passwort von").color(ColorCode.GRAY).advance()
                    .of(arguments[0]).color(ColorCode.GREEN).advance()
                    .of(" zurückgesetzt.").advance().createComponent());
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}