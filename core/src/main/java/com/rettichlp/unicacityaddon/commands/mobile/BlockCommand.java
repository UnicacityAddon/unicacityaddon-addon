package com.rettichlp.unicacityaddon.commands.mobile;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class BlockCommand extends Command {

    private static final String usage = "/blockieren [Spieler]";

    @Inject
    private BlockCommand() {
        super("blockieren", "block", "blocknumber");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length > 0) {
            String playerName = arguments[0];
            if (MobileEventHandler.blockedPlayerList.contains(playerName)) {
                MobileEventHandler.blockedPlayerList.remove(playerName);
                p.sendInfoMessage("Du hast " + playerName + " wieder freigegeben.");
            } else {
                MobileEventHandler.blockedPlayerList.add(playerName);
                p.sendInfoMessage("Du hast " + playerName + " blockiert.");
            }
        } else
            p.sendSyntaxMessage(usage);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}