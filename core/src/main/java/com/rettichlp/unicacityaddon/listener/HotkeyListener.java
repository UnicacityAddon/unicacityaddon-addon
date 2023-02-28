package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ImageUploadUtils;
import com.rettichlp.unicacityaddon.listener.team.AdListener;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.io.File;
import java.io.IOException;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/AlternateScreenshotEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class HotkeyListener {

    private final UnicacityAddon unicacityAddon;

    public HotkeyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        if (e.state().equals(KeyEvent.State.PRESS) && UnicacityAddon.ADDON.configuration().hotkeySetting().enabled().get()) {
            handleHotkey(e.key());
        }
    }

    private void handleHotkey(Key key) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        HotkeySetting hotkeySetting = UnicacityAddon.ADDON.configuration().hotkeySetting();

        if (key.equals(hotkeySetting.screenshot().getOrDefault(Key.NONE))) {
            File file;
            try {
                file = FileManager.getNewImageFile();
                handleScreenshotWithUpload(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!UnicacityAddon.isUnicacity() || Laby.references().chatAccessor().isChatOpen()) {
            return;
        }

        if (key.equals(hotkeySetting.acceptAd().getOrDefault(Key.NONE))) {
            AdListener.handleAd("freigeben");
        } else if (key.equals(hotkeySetting.declineAd().getOrDefault(Key.NONE))) {
            AdListener.handleAd("blockieren");
        } else if (key.equals(hotkeySetting.acceptReport().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/ar");
        } else if (key.equals(hotkeySetting.cancelReport().getOrDefault(Key.NONE))) {
            String farewell = this.unicacityAddon.configuration().reportMessageSetting().farewell().getOrDefault("");
            if (!farewell.isEmpty())
                p.sendServerMessage(farewell);
            p.sendServerMessage("/cr");
        } else if (key.equals(hotkeySetting.aDuty().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/aduty");
        } else if (key.equals(hotkeySetting.aDutySilent().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/aduty -s");
        } else if (key.equals(hotkeySetting.reinforcementFaction().getOrDefault(Key.NONE))) {
            FloatVector3 position = p.getPosition();
            p.sendServerMessage("/f Benötige Verstärkung! -> X: " + (int) position.getX() + " | Y: " + (int) position.getY() + " | Z: " + (int) position.getZ());
        } else if (key.equals(hotkeySetting.reinforcementAlliance().getOrDefault(Key.NONE))) {
            FloatVector3 position = p.getPosition();
            p.sendServerMessage("/d Benötige Verstärkung! -> X: " + (int) position.getX() + " | Y: " + (int) position.getY() + " | Z: " + (int) position.getZ());
        } else if (key.equals(hotkeySetting.publicChannel().getOrDefault(Key.NONE))) {
            if (p.getFaction().equals(Faction.NULL)) {
                p.sendErrorMessage("Du befindest dich in keiner Fraktion.");
                return;
            }

            Channel foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
            ClientMoveCommand clientMoveCommand = new ClientMoveCommand(foundChannel.getChannelID(), TSUtils.getMyClientID());

            CommandResponse commandResponse = clientMoveCommand.getResponse();
            if (!commandResponse.succeeded()) {
                p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
                return;
            }

            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Du bist in deinen").color(ColorCode.GRAY).advance().space()
                    .of("\"Öffentlich Channel\"").color(ColorCode.AQUA).advance()
                    .of(" gegangen.").color(ColorCode.GRAY).advance()
                    .createComponent());
        }
    }

    public static File createScreenshot(File file) {
        if (file != null) {
            // TODO: 28.02.2023
//            try {
//                Framebuffer framebuffer = ReflectionUtils.getValue(UnicacityAddon.MINECRAFT, Framebuffer.class);
//                assert framebuffer != null;
//                BufferedImage image = ScreenShotHelper.createScreenshot(UnicacityAddon.MINECRAFT.displayWidth, UnicacityAddon.MINECRAFT.displayHeight, framebuffer);
//                ImageIO.write(image, "jpg", file);
//                LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot erstellt!", "Wird gespeichert...");
//                return file;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
//        LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Screenshot konnte nicht erstellt werden.");
        return null;
    }

    public static void handleScreenshotWithoutUpload(File file) {
        createScreenshot(file);
    }

    public static void handleScreenshotWithUpload(File file) {
        File screenFile = createScreenshot(file);
        Thread thread = new Thread(() -> uploadScreenshot(screenFile));
        thread.start();
    }

    private static void uploadScreenshot(File screenshotFile) {
        if (screenshotFile == null)
            return;
        String link = ImageUploadUtils.uploadToLink(screenshotFile);
        UnicacityAddon.PLAYER.copyToClipboard(link);
//        LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot hochgeladen!", "Link in Zwischenablage kopiert.");
    }
}