package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.events.HotkeyEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScreenCommand extends CommandBase {

    private final List<String> SCREENSHOT_TYPE_LIST = new ArrayList<>(Arrays.asList(
            "kills", "großeinsatz", "drogeneinnahme", "equip", "reinforcement",
            "roleplay", "verhaftung", "korruption", "ticket", "blacklist"));

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

        String name = SCREENSHOT_TYPE_LIST.stream().filter(s -> s.contains(args[0])).findFirst().orElse(null);
        if (name == null) {
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
        List<String> tabCompletions = SCREENSHOT_TYPE_LIST;
        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}