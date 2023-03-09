package com.rettichlp.unicacityaddon.events.faction.terroristen;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.events.HotkeyEventHandler;
import com.rettichlp.unicacityaddon.modules.BombTimerModule;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BombTimerEventHandler {

    private static String location;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ITextComponent msg = e.getMessage();
        String formattedMsg = msg.getFormattedText();
        String unformattedMsg = msg.getUnformattedText();

        Matcher bombPlacedMatcher = PatternHandler.BOMB_PLACED_PATTERN.matcher(unformattedMsg);
        if (bombPlacedMatcher.find()) {
            BombTimerModule.isBomb = true;
            BombTimerModule.timer = "00:00";
            p.playSound(SoundRegistry.BOMB_SOUND, 1, 1);

            if (((p.getFaction().equals(Faction.POLIZEI) || p.getFaction().equals(Faction.FBI)) && p.getRank() > 3) || p.isSuperUser()) {
                location = bombPlacedMatcher.group("location");
                e.setMessage(Message.getBuilder()
                        .add(formattedMsg)
                        .space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Sperrgebiet ausrufen").color(ColorCode.RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet ausrufen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sperrgebiet " + getLocationWithArticle(location))
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }

            return;
        }

        Matcher m = PatternHandler.BOMB_REMOVED_PATTERN.matcher(unformattedMsg);
        if (m.find()) {
            String state = m.group(1);
            
            if (ConfigElements.getAutomatedBombScreenshot())  {
                try {
                    File file = FileManager.getNewActivityImageFile("großeinsatz");
                    HotkeyEventHandler.handleScreenshot(file);
                } catch (IOException f) {
                    throw new RuntimeException(f);
                }
            }
            
            String time = BombTimerModule.timer.startsWith(ColorCode.RED.getCode()) ? BombTimerModule.timer.substring(2) : BombTimerModule.timer;
            e.setMessage(Message.getBuilder()
                    .add(formattedMsg)
                    .space()
                    .of(time.isEmpty() ? "" : "(").color(ColorCode.DARK_GRAY).advance()
                    .of(time).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
                    .of(time.isEmpty() ? "" : ")").color(ColorCode.DARK_GRAY).advance()
                    .space()
                    .of(location != null ? "[" : "").color(ColorCode.DARK_GRAY).advance()
                    .of(location != null ? "Sperrgebiet aufheben" : "").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet aufheben").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, location != null ? "/removesperrgebiet " + getLocationWithArticle(location) : "")
                            .advance()
                    .of(location != null ? "]" : "").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
            BombTimerModule.stopBombTimer();
        }
    }

    private String getLocationWithArticle(String location) {
        NaviPoint naviPoint = NaviPoint.getNaviPointByTabName(location.replace(" ", "-"));
        String article = "der/die/das";
        if (naviPoint != null)
            article = naviPoint.getArticleFourthCase().replace("none", "");
        return location.startsWith("Haus ") ? location : article + (article.isEmpty() ? "" : " ") + location;
    }
}