package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.utils.ImageUploadUtils;
import net.labymod.main.LabyMod;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotEventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        handleScreenshot();
    }

    @SubscribeEvent
    public void onKeyboardInput(GuiScreenEvent.KeyboardInputEvent e) {
        handleScreenshot();
    }

    private void handleScreenshot() {
        if (!KeyBindRegistry.addonScreenshot.isPressed()) return;

        try {
            File newImageFile = FileManager.getNewImageFile();
            if (newImageFile == null) {
                LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Screenshot konnte nicht erstellt werden.");
                return;
            }

            Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
            assert framebuffer != null;
            BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
            ImageIO.write(image, "jpg", newImageFile);
            LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot erstellt!", "Wird hochgeladen...");

            Thread thread = new Thread(() -> uploadScreenshot(newImageFile));
            thread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void uploadScreenshot(File screenshotFile) {
        String link = ImageUploadUtils.uploadToLink(screenshotFile);
        AbstractionLayer.getPlayer().copyToClipboard(link);
        LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot hochgeladen!", "Link in Zwischenablage kopiert.");
    }
}