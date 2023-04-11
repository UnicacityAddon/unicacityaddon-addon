package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class BlockCommand extends Command {

    private static final String usage = "/blockieren [Spieler]";

    private final UnicacityAddon unicacityAddon;

    public BlockCommand(UnicacityAddon unicacityAddon) {
        super("blockieren", "block", "blocknumber");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length > 0) {
            String playerName = arguments[0];
            if (MobileListener.blockedPlayerList.contains(playerName)) {
                MobileListener.blockedPlayerList.remove(playerName);
                p.sendInfoMessage("Du hast " + playerName + " wieder freigegeben.");
            } else {
                MobileListener.blockedPlayerList.add(playerName);
                p.sendInfoMessage("Du hast " + playerName + " blockiert.");
            }
        } else
            p.sendSyntaxMessage(usage);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}