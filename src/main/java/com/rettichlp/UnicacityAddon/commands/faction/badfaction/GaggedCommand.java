package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.events.GaggedEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GaggedCommand implements IClientCommand {

    @Override @Nonnull
    public String getName() {
        return "geknebelt";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/geknebelt";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
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

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        GaggedEventHandler.toggleGagged();

        if (GaggedEventHandler.isGagged()) p.sendInfoMessage("Ab sofort kannst du nur noch flüstern.");
        else p.sendInfoMessage("Ab sofort kannst du wieder normal reden.");
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