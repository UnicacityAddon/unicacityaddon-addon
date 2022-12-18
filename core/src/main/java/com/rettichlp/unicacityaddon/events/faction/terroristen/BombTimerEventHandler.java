package com.rettichlp.unicacityaddon.events.faction.terroristen;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.NaviPointEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BombTimerEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ChatMessage chatMessage = e.chatMessage();
        String formattedMsg = chatMessage.getFormattedText();
        String unformattedMsg = chatMessage.getPlainText();

        Matcher bombPlacedMatcher = PatternHandler.BOMB_PLACED_PATTERN.matcher(unformattedMsg);
        if (bombPlacedMatcher.find()) {
//            BombTimerModule.isBomb = true;
//            BombTimerModule.timer = "00:00";
            // TODO: 10.12.2022 p.playSound(SoundRegistry.BOMB_SOUND, 1, 1);

            if (((p.getFaction().equals(Faction.POLIZEI) || p.getFaction().equals(Faction.FBI)) && p.getRank() > 3) || p.isSuperUser()) {
                String location = bombPlacedMatcher.group("location");
                e.setMessage(Message.getBuilder()
                        .add(formattedMsg)
                        .space()
                        .of("➡ Sperrgebiet").color(ColorCode.RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet ausrufen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sperrgebiet " + getLocationWithArticle(location))
                                .advance()
                        .of("ᴮᴱᵀᴬ").color(ColorCode.GREEN).italic().advance()
                        .createComponent());
            }

            return;
        }

        Matcher m = PatternHandler.BOMB_REMOVED_PATTERN.matcher(unformattedMsg);
        if (m.find()) {
            String state = m.group(1);

//            String time = BombTimerModule.timer.startsWith(ColorCode.RED.getCode()) ? BombTimerModule.timer.substring(2) : BombTimerModule.timer;
//            e.setMessage(Message.getBuilder()
//                    .add(formattedMsg)
//                    .space()
//                    .of(time.isEmpty() ? "" : "(").color(ColorCode.DARK_GRAY).advance()
//                    .of(time).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
//                    .of(time.isEmpty() ? "" : ")").color(ColorCode.DARK_GRAY).advance()
//                    .createComponent());
//            BombTimerModule.stopBombTimer();
        }
    }

    private String getLocationWithArticle(String location) {
        NaviPointEntry naviPointEntry = NaviPointEntry.getNaviPointEntryByTabName(location.replace(" ", "-"));
        String article = "der/die/das";
        if (naviPointEntry != null) article = naviPointEntry.getArticleFourthCase().replace("none", "");
        return location.startsWith("Haus ") ? location : article + (article.isEmpty() ? "" : " ") + location;
    }
}