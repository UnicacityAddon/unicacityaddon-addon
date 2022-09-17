package com.rettichlp.UnicacityAddon.commands.supporter;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.punish.Punishment;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class PunishCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "punish";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/punish [Spielername] [Grund]";
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

        if (args.length < 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        Punishment punishment = getPunishmentByName(args[1]);
        if (punishment == null) {
            p.sendErrorMessage("Dieser Bangrund existiert nicht");
            return;
        }

        String reason = punishment.getReason();
        int banDuration = punishment.getBanDuration();

        if (punishment.getCheckpoints() > 0)
            p.sendChatMessage("/checkpoints " + args[0] + " " + punishment.getCheckpoints() + " " + reason);
        if (punishment.getBanDuration() > 0)
            p.sendChatMessage("/tban " + args[0] + " " + getBanDurationString(banDuration) + " " + reason);
        if (punishment.getBanDuration() == -1) p.sendChatMessage("/ban " + args[0] + " " + reason);
        if (punishment.isLoyalityPointReset()) p.sendChatMessage("/resettreuebonus " + args[0]);
        if (punishment.getWeaponLock() > 0)
            p.sendChatMessage("/waffensperre " + args[0] + " " + punishment.getWeaponLock() + " 0 0");
        if (punishment.getFactionLock() > 0)
            p.sendChatMessage("/fraksperre " + args[0] + " " + punishment.getFactionLock() + " " + reason);
        if (punishment.isKick()) p.sendChatMessage("/kick " + args[0] + " " + reason);
        if (punishment.getWarnAmmount() > 0)
            for (int i = 0; i < punishment.getWarnAmmount(); i++) {
                p.sendChatMessage("/warn " + args[0] + " " + reason);
            }

    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            List<String> tabCompletions = Arrays.stream(Punishment.values()).map(Punishment::getTabReason).sorted().collect(Collectors.toList());
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        }
    }

    private String getBanDurationString(int banDuration) {
        int minutes = banDuration;
        int hours = minutes / 60;
        minutes -= hours * 60;
        int days = hours / 60;
        hours -= days * 60;

        return days + " " + hours + " " + minutes;
    }

    private Punishment getPunishmentByName(String s) {
        for (Punishment punishment : Punishment.values()) {
            if (punishment.getTabReason().equals(s)) return punishment;
        }
        return null;
    }
}