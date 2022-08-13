package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
public class BlacklistInfoCommand extends CommandBase {

    public static String target;
    public static long executedTime = -1;
    @Override
    @Nonnull
    public String getName() {
        return "blacklistinfo";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/blinfo [Spieler]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("blinfo");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length != 1) {
            p.sendSyntaxMessage("/blinfo [Spieler]");
            return;
        }

        executedTime = System.currentTimeMillis();
        target = args[0];

        p.sendChatMessage("/bl");
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}
