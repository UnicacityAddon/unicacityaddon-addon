package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
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

    private final List<String> typeOptions = Arrays.asList("blacklist", "ausraub", "menschenhandel", "transport", "autoverkauf", "drogenhandel", "verhandlung"
                                                            + "sonstiges");
    private final UnicacityAddon unicacityAddon;

    public RoleplayActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {

        new Thread(() -> {

            if (arguments.length < 2) {
                sendUsage();
                return;
            }

            String type = arguments[0];
            if (!typeOptions.contains(type)) type = "sonstiges";

            String screenshot = arguments[2];

            //TODO: API Abfrage senden
            //String response = this.unicacityAddon.api().sendBlacklistReasonAddRequest(type, date, screenshot).getInfo();
            //p.sendAPIMessage(response, true);

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