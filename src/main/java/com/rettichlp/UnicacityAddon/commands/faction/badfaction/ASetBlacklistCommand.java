package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.json.BlacklistEntry;
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
 * @author RettichLP
 */
public class ASetBlacklistCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "asetbl";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/asetbl [Spieler...] [Grund]";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 2) return;

        if (BlacklistEventHandler.BLACKLIST == null) {
            p.sendErrorMessage("Datei 'blacklistData.json' ist falsch formatiert!");
            return;
        }

        String reason = args[args.length - 1];
        BlacklistEntry ble = BlacklistEventHandler.BLACKLIST.getBlackListEntryByReason(reason);

        if (ble == null) {
            p.sendErrorMessage("Blacklistgrund wurde nicht gefunden!");
            return;
        }

        for (int i = 0; i < args.length - 1; i++) {
            p.sendChatMessage("/bl set " + args[i] + " " + ble.getKills() + " " + ble.getPrice() + " " + ble.getReason().replace("-", ""));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        if (args.length > 1 && BlacklistEventHandler.BLACKLIST != null) {
            tabCompletions.addAll(BlacklistEventHandler.BLACKLIST.getBlacklistReasons());
        }
        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}