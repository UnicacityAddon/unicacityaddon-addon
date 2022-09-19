package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.faction.ScreenshotType;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.events.HotkeyEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class ScreenCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "screen";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/screen [Typ]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("activitytest");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        ScreenshotType screenshotType = Arrays.stream(ScreenshotType.values()).filter(st -> st.getDisplayName().equals(args[0])).findFirst().orElse(null);
        if (screenshotType == null) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        try {
            File file = FileManager.getNewActivityImageFile(args[0]);
            HotkeyEventHandler.handleScreenshot(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Arrays.stream(ScreenshotType.values()).map(ScreenshotType::getDisplayName).collect(Collectors.toList());
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}