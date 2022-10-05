package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class SellDrugCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "selldrug";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/selldrug [Spieler] [Droge] [Reinheit] [Menge] [Preis]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions = ForgeUtils.getOnlinePlayers();
        } else if (args.length == 2) {
            tabCompletions.addAll(Arrays.asList("Kokain", "Marihuana", "Methamphetamin", "LSD", "Schmerzmittel", "Antibiotika", "Hustensaft", "Maske"));
        } else if (args.length == 3) {
            tabCompletions.addAll(Arrays.asList("0", "1", "2", "3"));
        }
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 5) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        p.sendChatMessage("/selldrug " + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4]);
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
