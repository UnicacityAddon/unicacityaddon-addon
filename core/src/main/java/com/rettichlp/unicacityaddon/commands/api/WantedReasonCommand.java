package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.WantedReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "wantedreason", usage = "[add|remove] [Grund] (Wanted-Punkte)")
public class WantedReasonCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public WantedReasonCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
                this.unicacityAddon.api().sendWantedReasonAddRequest(arguments[1], arguments[2]);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                this.unicacityAddon.api().sendWantedReasonRemoveRequest(arguments[1]);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, this.unicacityAddon.api().getWantedReasonList().stream().map(WantedReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}