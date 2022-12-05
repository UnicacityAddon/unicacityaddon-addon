package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ShareLocationCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "sharelocation";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/sharelocation [Player...] (-d)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("sloc", "shareloc");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (!MobileEventHandler.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            return;
        }

        Set<String> playerNames = new LinkedHashSet<>();
        boolean allianceChat = false;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-d")) {
                allianceChat = true;
                break;
            }
            if (ForgeUtils.getOnlinePlayers().contains(arg)) playerNames.add(arg);
        }

        if (playerNames.isEmpty()) {
            p.sendErrorMessage("Dieser Spieler wurde nicht gefunden!");
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

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}