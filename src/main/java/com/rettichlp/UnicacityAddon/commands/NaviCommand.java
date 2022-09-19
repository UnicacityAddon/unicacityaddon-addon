package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.location.NaviPoint;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
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
 * @author RettichLP
 */
@UCCommand
public class NaviCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "navi";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/navi";
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
        if (args.length < 1) {
            p.sendChatMessage("/navi");
            return;
        }

        if (MathUtils.isInteger(args[0])) {
            p.sendChatMessage("/navi Haus:" + args[0]);
            return;
        }

        NaviPoint naviPoint = getNaviPointByName(args[0]);
        if (naviPoint == null) {
            p.sendChatMessage("/navi " + TextUtils.makeStringByArgs(args, " "));
            return;
        }

        p.setNaviRoute(naviPoint.getBlockPos());
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Arrays.stream(NaviPoint.values()).map(NaviPoint::getName).sorted().collect(Collectors.toList());
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    private NaviPoint getNaviPointByName(String s) {
        for (NaviPoint naviPoint : NaviPoint.values()) {
            if (naviPoint.getName().equals(s)) return naviPoint;
        }
        return null;
    }
}