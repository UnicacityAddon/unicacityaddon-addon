package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ImageUploadUtils;
import jdk.internal.joptsimple.internal.Strings;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/AlternateScreenshotEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
@NoArgsConstructor
public class HotkeyEventHandler {

    private String adIssuer;
    private long adTime;
    private static long lastHotkeyUse = -1;

//    @Subscribe
//    public void onKey(KeyEvent e) {
//        handleHotkey();
//    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        Matcher matcher = PatternHandler.AD_CONTROL_PATTERN.matcher(e.chatMessage().getPlainText());
        if (!matcher.find()) return;
        adIssuer = matcher.group(1);
        adTime = System.currentTimeMillis();
    }

//    private void handleHotkey() {
//        if (System.currentTimeMillis() - lastHotkeyUse < TimeUnit.SECONDS.toMillis(1) || Keyboard.isKeyDown(0)) return;
//        UPlayer p = AbstractionLayer.getPlayer();
//
//        if (Keyboard.isKeyDown(KeyBindRegistry.addonScreenshot.getKeyCode())) {
//            handleScreenshot();
//            lastHotkeyUse = System.currentTimeMillis();
//        }
//
//        if (UnicacityAddon.MINECRAFT.currentScreen != null) return;
//
//        if (Keyboard.isKeyDown(KeyBindRegistry.adFreigeben.getKeyCode())) {
//            handleAd("freigeben");
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.adBlockieren.getKeyCode())) {
//            handleAd("blockieren");
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.acceptReport.getKeyCode())) {
//            p.sendChatMessage("/ar");
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.cancelReport.getKeyCode())) {
//            handleCancelReport();
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.aDuty.getKeyCode())) {
//            p.sendChatMessage("/aduty");
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.aDutySilent.getKeyCode())) {
//            p.sendChatMessage("/aduty -s");
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.freinforcement.getKeyCode())) {
//            BlockPos position = p.getPosition();
//            p.sendChatMessage("/f FloatVector3erstärkung! -> X: " + position.getX() + " | Y: " + position.getY() + " | Z: " + position.getZ());
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.dreinforcement.getKeyCode())) {
//            BlockPos position = p.getPosition();
//            p.sendChatMessage("/d Benötige Verstärkung! -> X: " + position.getX() + " | Y: " + position.getY() + " | Z: " + position.getZ());
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.publicChannelJoin.getKeyCode())) {
//            handlePublicChannelJoin();
//            lastHotkeyUse = System.currentTimeMillis();
//        } else if (Keyboard.isKeyDown(KeyBindRegistry.damageIndicator.getKeyCode())) {
//            RenderTagEventHandler.showPlayerInfo = !RenderTagEventHandler.showPlayerInfo;
//            NameTagEventHandler.refreshAllDisplayNames();
//        }
//    }

//    private void handleScreenshot() {
//        File file;
//        try {
//            file = FileManager.getNewImageFile();
//            handleScreenshotWithUpload(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public static File handleScreenshot(File file) {
//        if (file != null) {
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
//        }
//        LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "Fehler!", "Screenshot konnte nicht erstellt werden.");
//        return null;
//    }

//    public static void handleScreenshotWithUpload(File file) {
//        File screenFile = handleScreenshot(file);
//        Thread thread = new Thread(() -> uploadScreenshot(screenFile));
//        thread.start();
//    }

//    public static void handleScreenshotWithoutUpload(File file) {
//        handleScreenshot(file);
//    }

    private static void uploadScreenshot(File screenshotFile) {
        if (screenshotFile == null) return;
        String link = ImageUploadUtils.uploadToLink(screenshotFile);
        AbstractionLayer.getPlayer().copyToClipboard(link);
//        LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Screenshot hochgeladen!", "Link in Zwischenablage kopiert.");
    }

    private void handleAd(String type) {
        if (adIssuer == null || System.currentTimeMillis() - adTime > TimeUnit.SECONDS.toMillis(20)) return;
        AbstractionLayer.getPlayer().sendChatMessage("/adcontrol " + adIssuer + " " + type);
        adIssuer = null;
    }

    private void handleCancelReport() {
        UPlayer p = AbstractionLayer.getPlayer();
        String farewell = UnicacityAddon.configuration.reportMessageSetting().farewell().getOrDefault(Strings.EMPTY);
        if (!farewell.isEmpty()) p.sendChatMessage(farewell);
        p.sendChatMessage("/cr");
    }

    private void handlePublicChannelJoin() {
        UPlayer p = AbstractionLayer.getPlayer();
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