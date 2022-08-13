package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
public class CancelCountdownCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "cancelcountdown";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/cancelcountdown";
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
        CountdownCommand.countdown = -1;
        AbstractionLayer.getPlayer().sendInfoMessage("Der Countdown wurde abgebrochen.");
    }
}