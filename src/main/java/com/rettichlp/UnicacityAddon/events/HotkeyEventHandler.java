package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ImageUploadUtils;
import net.labymod.main.LabyMod;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/AlternateScreenshotEventHandler.java">UCUtils by paulzhng</a>
 */
public class HotkeyEventHandler {

    private String adIssuer;
    private long adTime;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        handleHotkey();
    }

    @SubscribeEvent
    public void onKeyboardInput(GuiScreenEvent.KeyboardInputEvent e) {
        handleHotkey();
    }

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        Matcher matcher = PatternHandler.AD_CONTROL_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (!matcher.find()) return;
        adIssuer = matcher.group(1);
        adTime = System.currentTimeMillis();
    }

    private void handleHotkey() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (KeyBindRegistry.addonScreenshot.isPressed()) {
            handleScreenshot();
        } else if (KeyBindRegistry.adFreigeben.isPressed()) {
            handleAd("freigeben");
        } else if (KeyBindRegistry.adBlockieren.isPressed()) {
            handleAd("blockieren");
        } else if (KeyBindRegistry.acceptReport.isPressed()) {
            p.sendChatMessage("/ar");
        } else if (KeyBindRegistry.cancelReport.isPressed()) {
            if (!ConfigElements.getReportFarewell().isEmpty()) p.sendChatMessage(ConfigElements.getReportFarewell());
            p.sendChatMessage("/cr");
        } else if (KeyBindRegistry.aDuty.isPressed()) {
            p.sendChatMessage("/aduty");
        } else if (KeyBindRegistry.aDutySilent.isPressed()) {
            p.sendChatMessage("/aduty -s");
        }
    }

    private void handleScreenshot() {
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

    private void handleAd(String type) {
        if (adIssuer == null || System.currentTimeMillis() - adTime > TimeUnit.SECONDS.toMillis(20)) return;
        AbstractionLayer.getPlayer().sendChatMessage("/adcontrol " + adIssuer + " " + type);
        adIssuer = null;
    }
}