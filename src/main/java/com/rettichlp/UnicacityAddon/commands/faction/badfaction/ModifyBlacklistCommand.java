package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.json.BlacklistEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ModifyBlacklistCommand extends CommandBase {

    public static String target;
    public static ModifyBlacklistType type;
    public static BlacklistEntry addReason;
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
        BlacklistEntry ble = BlacklistEventHandler.BLACKLIST.getBlackListEntryByReason(reason);
        if (!reason.equalsIgnoreCase("-v") && ble == null) {
            p.sendErrorMessage("Blacklistgrund wurde nicht gefunden!");
            return;
        }

        target = args[0];
        if (ble != null) {
            addReason = ble;
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
        BlacklistEventHandler.refreshBlacklistReasons();
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        if (args.length > 1 && BlacklistEventHandler.BLACKLIST != null) {
            tabCompletions.addAll(BlacklistEventHandler.BLACKLIST.getBlacklistReasons());
            tabCompletions.add("-v");
        }

        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    public enum ModifyBlacklistType {
        MODIFY_REASON,
        OUTLAW
    }

}
