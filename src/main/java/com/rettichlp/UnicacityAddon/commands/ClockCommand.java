package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ClockCommand extends CommandBase {

    @Override @Nonnull
    public String getName() {
        return "clock";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/clock";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Arrays.asList("uhrzeit", "uhr");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        AFbankEinzahlenCommand.sendClockMessage();
    }
}
