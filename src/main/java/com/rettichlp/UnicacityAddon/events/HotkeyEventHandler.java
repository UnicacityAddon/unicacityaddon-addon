package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Channel;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.ImageUploadUtils;
import net.labymod.main.LabyMod;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

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
@UCEvent
public class HotkeyEventHandler {

    private String adIssuer;
    private long adTime;
    private static long lastScreenshot;

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
        if (System.currentTimeMillis() - lastScreenshot < TimeUnit.SECONDS.toMillis(1)) return;
        UPlayer p = AbstractionLayer.getPlayer();

        if (Keyboard.isKeyDown(KeyBindRegistry.addonScreenshot.getKeyCode())) {
            try {
                File file = FileManager.getNewImageFile();
                handleScreenshot(file);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (UnicacityAddon.MINECRAFT.currentScreen != null) return;

        if (Keyboard.isKeyDown(KeyBindRegistry.adFreigeben.getKeyCode())) {
            handleAd("freigeben");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.adBlockieren.getKeyCode())) {
            handleAd("blockieren");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.acceptReport.getKeyCode())) {
            p.sendChatMessage("/ar");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.cancelReport.getKeyCode())) {
            if (!ConfigElements.getReportFarewell().isEmpty()) p.sendChatMessage(ConfigElements.getReportFarewell());
            p.sendChatMessage("/cr");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.aDuty.getKeyCode())) {
            p.sendChatMessage("/aduty");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.aDutySilent.getKeyCode())) {
            p.sendChatMessage("/aduty -s");
        } else if (Keyboard.isKeyDown(KeyBindRegistry.freinforcement.getKeyCode())) {
            BlockPos position = p.getPosition();
            p.sendChatMessage("/f Benötige Verstärkung! -> X: " + position.getX() + " | Y: " + position.getY() + " | Z: " + position.getZ());
        } else if (Keyboard.isKeyDown(KeyBindRegistry.dreinforcement.getKeyCode())) {
            BlockPos position = p.getPosition();
            p.sendChatMessage("/d Benötige Verstärkung! -> X: " + position.getX() + " | Y: " + position.getY() + " | Z: " + position.getZ());
        } else if (Keyboard.isKeyDown(KeyBindRegistry.publicChannelJoin.getKeyCode())) {
            if (p.getFaction().equals(Faction.NULL)) {
                p.sendErrorMessage("Du befindest dich in keiner Fraktion.");
                return;
            }

            Channel foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
            if (foundChannel == null) {
                p.sendErrorMessage("Es wurde kein Channel gefunden.");
                return;
            }

            ClientMoveCommand clientMoveCommand = new ClientMoveCommand(foundChannel.getChannelID(), TSUtils.getMyClientID());

            CommandResponse commandResponse = clientMoveCommand.getResponse();
            if (!commandResponse.succeeded()) {
                p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
                return;
            }

            Message.getBuilder()
                    .prefix()
                    .of("Du bist in deinen").color(ColorCode.GRAY).advance().space()
                    .of("\"Öffentlich Channel\"").color(ColorCode.AQUA).advance()
                    .of(" gegangen.").color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
        }
    }

    public static void handleScreenshot(File file) {
        try {
            if (file == null) {
                LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Screenshot konnte nicht erstellt werden.");
                return;
            }

            Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
            assert framebuffer != null;
            BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
            ImageIO.write(image, "jpg", file);
            LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot erstellt!", "Wird hochgeladen...");

            lastScreenshot = System.currentTimeMillis();

            Thread thread = new Thread(() -> uploadScreenshot(file));
            thread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void uploadScreenshot(File screenshotFile) {
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