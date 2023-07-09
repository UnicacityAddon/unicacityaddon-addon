package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ActivityCheckBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "roleplayactivity", aliases = {"ract"}, usage = "[type] [screenshot]")
public class RoleplayActivityCommand extends UnicacityCommand {

    private final List<String> typeOptions = Arrays.asList("blacklist", "ausraub", "menschenhandel", "transport", "autoverkauf", "drogenhandel", "verhandlung", "sonstiges");

    private final UnicacityAddon unicacityAddon;

    public RoleplayActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 2) {
            sendUsage();
            return true;
        }

        new Thread(() -> {
            String type = typeOptions.contains(arguments[0]) ? arguments[0] : "sonstiges";
            String screenshot = arguments[1];

            String info = ActivityCheckBuilder.getBuilder(this.unicacityAddon)
                    .activity(ActivityCheckBuilder.Activity.ROLEPLAY)
                    .type(type)
                    .date(System.currentTimeMillis())
                    .screenshot(screenshot)
                    .send().getInfo();

            p.sendAPIMessage(info, true);
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, typeOptions)
                .build();
    }
}