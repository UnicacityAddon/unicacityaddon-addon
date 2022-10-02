package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.events.ShutDownEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ShutdownFriedhofCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "shutdownfriedhof";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/shutdownfriedhof";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("shutdownf");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return ForgeUtils.getOnlinePlayers();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        ShutDownEventHandler.shutdownFriedhof = !ShutDownEventHandler.shutdownFriedhof;

        if (ShutDownEventHandler.shutdownFriedhof)
            Message.getBuilder().prefix().of("Dein Computer fährt nun herunter sobald du wieder lebst.").color(ColorCode.GRAY)
                    .advance().sendTo(p.getPlayer());
        else
            Message.getBuilder().prefix().of("Dein Computer fährt nun").color(ColorCode.GRAY).advance().space()
                    .of("nichtmehr").color(ColorCode.RED).advance().space()
                    .of("herunter sobald du wieder lebst.").color(ColorCode.GRAY)
                    .advance().sendTo(p.getPlayer());
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