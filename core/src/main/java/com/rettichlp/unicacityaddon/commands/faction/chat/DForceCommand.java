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
@UCCommand
public class DForceCommand extends UnicacityCommand {

    private static final String usage = "/dforce [Nachricht]";

    private final UnicacityAddon unicacityAddon;

    public DForceCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "dforce", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String message = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ");
        p.sendServerMessage("/d " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}