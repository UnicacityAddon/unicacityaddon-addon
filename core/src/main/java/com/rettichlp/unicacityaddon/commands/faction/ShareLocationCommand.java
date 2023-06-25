package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand(prefix = "sharelocation", aliases = {"sloc", "shareloc"}, usage = "[Spieler...] (-d)")
public class ShareLocationCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ShareLocationCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (!MobileListener.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            return true;
        }

        Set<String> playerNames = new LinkedHashSet<>();
        boolean allianceChat = false;

        for (String argument : arguments) {
            if (argument.equalsIgnoreCase("-d")) {
                allianceChat = true;
                break;
            }
            if (this.unicacityAddon.services().util().getOnlinePlayers().contains(argument))
                playerNames.add(argument);
        }

        if (playerNames.isEmpty()) {
            p.sendErrorMessage("Dieser Spieler wurde nicht gefunden!");
            return true;
        }

        String playerString = String.join(", ", playerNames);
        String command = allianceChat ? "/d" : "/f";

        FloatVector3 location = p.getLocation();
        if (location != null) {
            int posX = (int) location.getX();
            int posY = (int) location.getY();
            int posZ = (int) location.getZ();

            p.sendServerMessage(command + " Positionsteilung für " + playerString + "! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);

            p.sendMessage(Message.getBuilder().prefix()
                    .of("Du hast eine Positionsmitteilung an ").color(ColorCode.AQUA).advance()
                    .of(playerString).color(ColorCode.DARK_AQUA).advance().space()
                    .of("gesendet").color(ColorCode.AQUA).advance()
                    .of(".").color(ColorCode.GRAY).advance()
                    .createComponent());
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}