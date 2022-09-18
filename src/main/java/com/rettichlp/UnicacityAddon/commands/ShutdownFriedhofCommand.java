package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.events.ShutDownEventHandler;
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
public class ShutdownFriedhofCommand extends CommandBase {

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
}