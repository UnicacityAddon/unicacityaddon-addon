package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 **/
public class ShareLocationCommand extends CommandBase {

    @Override @Nonnull public String getName() {
        return "sharelocation";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/sharelocation [Player...] (-d) ";
    }

    @Override @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("sloc", "shareloc");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        Set<String> playerNames = new LinkedHashSet<>();
        UPlayer p = AbstractionLayer.getPlayer();
        boolean allianceChat = false;

        //TODO: CommunicationsChecker
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-d")) {
                allianceChat = true;
                break;
            }
            if (ForgeUtils.getOnlinePlayers().contains(arg)) playerNames.add(arg);
        }

        if (playerNames.isEmpty()) {
            Message.getBuilder().error().space().of("Dieser Spieler wurder nicht gefunden.").color(ColorCode.RED).advance().sendTo(p.getPlayer());
            return;
        }

        String playerString = String.join(", ", playerNames);
        String command = allianceChat ? "/d" : "/f";

        BlockPos position = p.getPosition();
        int posX = position.getX();
        int posY = position.getY();
        int posZ = position.getZ();

        p.sendChatMessage(command + " Positionsteilung für " + playerString + "! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);

        Message.getBuilder().prefix()
                .of("Du hast eine Positionsmitteilung an ").color(ColorCode.AQUA).advance()
                .of(playerString).color(ColorCode.DARK_AQUA).advance().space()
                .of("gesendet").color(ColorCode.AQUA).advance()
                .of(".").color(ColorCode.GRAY).advance().sendTo(p.getPlayer());
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}