package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.TabCompletionBuilder;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.updater.Updater;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;
import org.apache.commons.lang3.SystemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class UpdateAddonCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "updateunicacityaddon";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/updateunicacityaddon";
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
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (!Updater.latestVersion.equals(UnicacityAddon.VERSION)) {
            if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_UNIX) {
                Updater.update();
            } else p.sendErrorMessage("Dieser Befehl wird nur unter Windows unterstützt.");
        } else p.sendInfoMessage("Du spielst bereits mit der neusten Version.");
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