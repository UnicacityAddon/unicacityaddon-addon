package com.rettichlp.unicacityaddon.commands.faction.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "dforce", usage = "[Nachricht]")
public class DForceCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DForceCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            sendUsage();
            return true;
        }

        String message = this.unicacityAddon.services().util().textUtils().makeStringByArgs(arguments, " ");
        p.sendServerMessage("/d " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}