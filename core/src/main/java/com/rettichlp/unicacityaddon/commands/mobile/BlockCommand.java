package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "blockieren", aliases = {"block", "blocknumber"}, usage = "[Spieler]")
public class BlockCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public BlockCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
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
            sendUsage(p);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}