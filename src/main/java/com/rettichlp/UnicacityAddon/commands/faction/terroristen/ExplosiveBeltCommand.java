package com.rettichlp.UnicacityAddon.commands.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

/**
 * @author Dimiikou
 */
@UCCommand
public class ExplosiveBeltCommand extends CommandBase {

    @Override @Nonnull
    public String getName() {
        return "sprenggürtel";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/sprenggürtel [Countdown]";
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) { return true; }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        try {
            ExplosiveBeltTimerModule.currentCount = parseInt(args[0]);
        } catch (NumberInvalidException e) {
            p.sendErrorMessage("Der Countdown muss eine Zahl sein!");
            return;
        }

        ExplosiveBeltTimerModule.explosiveBeltStarted = true;
        ExplosiveBeltTimerModule.timer = args[0];
        p.sendChatMessage("/sprenggürtel " + args[0]);
    }
}
