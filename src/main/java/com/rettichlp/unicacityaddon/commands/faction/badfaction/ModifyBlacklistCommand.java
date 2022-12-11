package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.models.BlacklistReasonEntry;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
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
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(2, Syncer.getBlacklistReasonEntryList().stream().map(BlacklistReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .addAtIndex(2, "-v")
                .build();
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