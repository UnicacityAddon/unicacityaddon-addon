package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.utils.ImageUploadUtils;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HotkeyEventHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        handleScreenshot();
    }

    @SubscribeEvent
    public void onKeyboardInput(GuiScreenEvent.KeyboardInputEvent e) {
        handleScreenshot();
    }

    private void handleScreenshot() {
        if (!KeyBindRegistry.imgurscreenshot.isPressed()) return;
        UPlayer p = AbstractionLayer.getPlayer();

        try {
            File newImageFile = FileManager.getNewImageFile();
            if (newImageFile == null) {
                p.sendErrorMessage("Screenshot konnte nicht erstellt werden.");
                return;
            }

            Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
            BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
            ImageIO.write(image, "jpg", newImageFile);

            uploadScreenshot(newImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        TODO: Remove after Test
        File unicacityAddonDir = new File(UnicacityAddon.MINECRAFT.mcDataDir.getAbsolutePath() + "/unicacityAddon/");
        if (unicacityAddonDir.exists() || unicacityAddonDir.mkdir()) {
            File screenshotsDir = new File(UnicacityAddon.MINECRAFT.mcDataDir.getAbsolutePath() + "/unicacityAddon/screenshots/");
            if (screenshotsDir.exists() || screenshotsDir.mkdir()) {

                String date = DATE_FORMAT.format(new Date());
                StringBuilder sb = new StringBuilder(date);
                int i = 1;
                while (new File(UnicacityAddon.MINECRAFT.mcDataDir.getAbsolutePath() + "/unicacityAddon/screenshots/" + date + ".jpg").exists()) {
                    if (i == 1) {
                        sb.append("_").append(i++);
                    } else {
                        sb.replace(sb.length() - 1, sb.length(), String.valueOf(i));
                    }
                }

                Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
                BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
                assert framebuffer != null;

                File imageFile = new File(sb.append(".jpg").toString());
                try {
                    if (imageFile.createNewFile()) ImageIO.write(image, "jpg", imageFile);
                    return imageFile;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                    .error().space()
                    .of("Ordner 'screenshots' wurde nicht gefunden!").color(ColorCode.GRAY).advance()
                    .createComponent());
        } else AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                .error().space()
                .of("Ordner 'unicacityAddon' wurde nicht gefunden!").color(ColorCode.GRAY).advance()
                .createComponent());
        return null;*/
    }

    private void uploadScreenshot(File screenshotFile) {
        String link = ImageUploadUtils.uploadToLink(screenshotFile);
        AbstractionLayer.getPlayer().copyToClipboard(link);
    }
}