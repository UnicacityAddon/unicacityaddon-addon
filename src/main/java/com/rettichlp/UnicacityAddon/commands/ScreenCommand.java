package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScreenCommand extends CommandBase {

    private static List<String> variables = Arrays.asList(
            "kills", "großeinsatz", "drogeneinnahme", "equip", "reinforcement",
            "roleplay", "verhaftung", "korruption", "ticket", "blacklist");

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
        if (args.length != 1 || !variables.contains(args[0].toLowerCase())) { //  || !variables.contains(args[0].toLowerCase())
            AbstractionLayer.getPlayer().sendSyntaxMessage(getUsage(sender));
            return;
        }

        handleScreenshot(args[0]);
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return variables;
    }

    private void handleScreenshot(String type) {
        try {
            File newImageFile = FileManager.getNewActivityImageFile(type);
            if (newImageFile == null) {
                UnicacityAddon.LABYMOD.notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Screenshot konnte nicht erstellt werden.");
                return;
            }

            Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
            assert framebuffer != null;
            BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
            ImageIO.write(image, "jpg", newImageFile);
            UnicacityAddon.LABYMOD.notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot erstellt!", "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
