package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.listener.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ShareLocationCommand extends Command {

    private static final String usage = "/sharelocation [Player...] (-d)";

    public ShareLocationCommand() {
        super("sharelocation", "sloc", "shareloc");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (!MobileEventHandler.hasCommunications) {
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
            if (ForgeUtils.getOnlinePlayers().contains(argument))
                playerNames.add(argument);
        }

        if (playerNames.isEmpty()) {
            p.sendErrorMessage("Dieser Spieler wurde nicht gefunden!");
            return true;
        }

        String playerString = String.join(", ", playerNames);
        String command = allianceChat ? "/d" : "/f";

        FloatVector3 position = p.getPosition();
        float posX = position.getX();
        float posY = position.getY();
        float posZ = position.getZ();

        p.sendServerMessage(command + " Positionsteilung für " + playerString + "! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);

        p.sendMessage(Message.getBuilder().prefix()
                .of("Du hast eine Positionsmitteilung an ").color(ColorCode.AQUA).advance()
                .of(playerString).color(ColorCode.DARK_AQUA).advance().space()
                .of("gesendet").color(ColorCode.AQUA).advance()
                .of(".").color(ColorCode.GRAY).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}