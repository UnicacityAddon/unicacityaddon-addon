package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class MaskInfoCommand implements IClientCommand {

    public static long startTime = 0;

    @Override
    @Nonnull
    public String getName() {
        return "maskinfo";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/maskinfo";
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

        if (startTime > 0) {
            long timeLeft = System.currentTimeMillis() - startTime;
            String maskDuration = TextUtils.parseTimer(1200 - TimeUnit.MILLISECONDS.toSeconds(timeLeft));
            String[] splittedMaskDurytion = maskDuration.split(":");
            int minutes = Integer.parseInt(splittedMaskDurytion[0]);
            int seconds = Integer.parseInt(splittedMaskDurytion[1]);
            p.sendInfoMessage("Du bist noch " + minutes + " Minute" + (minutes != 1 ? "n" : "") + " und " + seconds + " Sekunde" + (seconds != 1 ? "n" : "") + " maskiert.");
        } else {
            p.sendErrorMessage("Du bist nicht maskiert.");
        }
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