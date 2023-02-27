package com.rettichlp.unicacityaddon.listener.faction.terroristen;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.hudwidgets.BombHudWidget;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BombTimerListener {

    private static String location;

    private final UnicacityAddon unicacityAddon;

    public BombTimerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        ChatMessage chatMessage = e.chatMessage();
        String formattedMsg = chatMessage.getFormattedText();
        String msg = chatMessage.getPlainText();

        Matcher bombPlacedMatcher = PatternHandler.BOMB_PLACED_PATTERN.matcher(msg);
        if (bombPlacedMatcher.find()) {
            BombHudWidget.timer = 0;
//            p.playSound(SoundRegistry.BOMB_SOUND, 1, 1); // TODO: 08.02.2023

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

        Matcher m = PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg);
        if (m.find()) {
            String state = m.group(1);

//            String time = BombTimerModule.timer.startsWith(ColorCode.RED.getCode()) ? BombTimerModule.timer.substring(2) : BombTimerModule.timer;
//            e.setMessage(Message.getBuilder()
//                    .add(formattedMsg)
//                    .space()
//                    .of(time.isEmpty() ? "" : "(").color(ColorCode.DARK_GRAY).advance()
//                    .of(time).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
//                    .of(time.isEmpty() ? "" : ")").color(ColorCode.DARK_GRAY).advance()
//                    .space()
//                    .of(location != null ? "[" : "").color(ColorCode.DARK_GRAY).advance()
//                    .of(location != null ? "Sperrgebiet aufheben" : "").color(ColorCode.RED)
//                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet aufheben").color(ColorCode.RED).advance().createComponent())
//                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, location != null ? "/removesperrgebiet " + getLocationWithArticle(location) : "")
//                            .advance()
//                    .of(location != null ? "]" : "").color(ColorCode.DARK_GRAY).advance()
//                    .createComponent());
//            BombTimerModule.stopBombTimer();
        }
    }

    private String getLocationWithArticle(String location) {
        NaviPoint naviPoint = NaviPoint.getNaviPointEntryByTabName(location.replace(" ", "-"));
        String article = "der/die/das";
        if (naviPoint != null)
            article = naviPoint.getArticleFourthCase().replace("none", "");
        return location.startsWith("Haus ") ? location : article + (article.isEmpty() ? "" : " ") + location;
    }
}