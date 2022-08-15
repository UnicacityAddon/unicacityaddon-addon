package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
public class SyncPlayerDataCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "syncplayerdata";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/syncplayerdata";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("spd");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        AbstractionLayer.getPlayer().sendInfoMessage("Synchronisierung gestartet.");
        FactionHandler.syncPlayerFactions();
        FactionHandler.syncPlayerRanks();
    }
}