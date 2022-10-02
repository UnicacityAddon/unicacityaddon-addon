package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.api.entries.BlacklistReasonEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class ModifyBlacklistCommand implements IClientCommand {

    public static String target;
    public static ModifyBlacklistType type;
    public static BlacklistReasonEntry addReason;
    public static long executedTime = -1;

    @Override
    @Nonnull
    public String getName() {
        return "modifyblacklist";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/modifyblacklist [Spieler] [Grund/-v]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("mbl");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length != 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        String reason = args[1];

        BlacklistReasonEntry blacklistReasonEntry = BlacklistReasonEntry.getBlacklistReasonEntryByReason(reason);
        if (!reason.equalsIgnoreCase("-v") && blacklistReasonEntry == null) {
            p.sendErrorMessage("Blacklistgrund wurde nicht gefunden!");
            return;
        }

        target = args[0];
        if (blacklistReasonEntry != null) {
            addReason = blacklistReasonEntry;
            type = ModifyBlacklistType.MODIFY_REASON;
        } else {
            type = ModifyBlacklistType.OUTLAW;
        }

        executedTime = System.currentTimeMillis();

        p.sendChatMessage("/bl");
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        if (args.length > 1) {
            tabCompletions.addAll(Syncer.getBlacklistReasonEntryList().stream().map(BlacklistReasonEntry::getReason).sorted().collect(Collectors.toList()));
            tabCompletions.add("-v");
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
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

    public enum ModifyBlacklistType {
        MODIFY_REASON,
        OUTLAW
    }
}