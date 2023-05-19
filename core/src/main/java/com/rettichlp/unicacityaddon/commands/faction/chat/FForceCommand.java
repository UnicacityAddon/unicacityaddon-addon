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
public class FForceCommand extends UnicacityCommand {

    private static final String usage = "/fforce [Nachricht]";

    private final UnicacityAddon unicacityAddon;

    public FForceCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "fforce", true);
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
        p.sendServerMessage("/f " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}