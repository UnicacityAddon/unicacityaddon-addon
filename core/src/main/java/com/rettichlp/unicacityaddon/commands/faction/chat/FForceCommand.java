package com.rettichlp.unicacityaddon.commands.faction.chat;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class FForceCommand extends Command {

    private static final String usage = "/fforce [Nachricht]";

    @Inject
    private FForceCommand() {
        super("fforce");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 0) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String message = TextUtils.makeStringByArgs(arguments, " ");
        p.sendChatMessage("/f " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}