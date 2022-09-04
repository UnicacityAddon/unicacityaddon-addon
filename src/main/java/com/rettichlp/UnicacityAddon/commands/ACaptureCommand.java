package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCCommand
public class ACaptureCommand extends CommandBase {

    final Timer timer = new Timer();

    @Override @Nonnull
    public String getName() {
        return "capture";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/capture";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        AbstractionLayer.getPlayer().sendChatMessage("/capture");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AbstractionLayer.getPlayer().sendChatMessage("/capture");
            }
        }, TimeUnit.SECONDS.toMillis(15));
    }
}