package com.rettichlp.UnicacityAddon.events.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.entries.NaviPointEntry;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BombTimerEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        Matcher bombPlacedMatcher = PatternHandler.BOMB_PLACED_PATTERN.matcher(msg);
        if (bombPlacedMatcher.find()) {
            BombTimerModule.isBomb = true;
            BombTimerModule.timer = "00:00";

            if ((p.getFaction().equals(Faction.POLIZEI) || p.getFaction().equals(Faction.FBI)) && p.getRank() > 3) {
                String location = bombPlacedMatcher.group("location");
                e.setMessage(Message.getBuilder()
                        .of("News: Achtung! Es wurde eine Bombe in der Nähe von").color(ColorCode.GOLD).advance().space()
                        .of(location).color(ColorCode.GOLD).bold().advance().space()
                        .of("gefunden!").color(ColorCode.GOLD).advance().space()
                        .of("[Sperrgebiet]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sperrgebiet " + getLocationWithArticle(location))
                                .advance()
                        .of("ᴮᴱᵀᴬ").color(ColorCode.GREEN).italic().advance()
                        .createComponent());
            }

            return;
        }

        Matcher m = PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg);
        if (m.find()) {
            String state = m.group(1);

            String time = BombTimerModule.timer.startsWith(ColorCode.RED.getCode()) ? BombTimerModule.timer.substring(2) : BombTimerModule.timer;
            e.setMessage(Message.getBuilder()
                    .of("News: Die Bombe konnte").color(ColorCode.GOLD).advance().space()
                    .of(state).color(ColorCode.GOLD).advance().space()
                    .of("entschärft werden!").color(ColorCode.GOLD).advance().space()
                    .of(time.isEmpty() ? "" : "(").color(ColorCode.DARK_GRAY).advance()
                    .of(time).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
                    .of(time.isEmpty() ? "" : ")").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
            BombTimerModule.stopBombTimer();
        }
    }

    private String getLocationWithArticle(String location) {
        NaviPointEntry naviPointEntry = NaviPointEntry.getNaviPointEntryByTabName(location.replace(" ", "-"));
        String article = "der/die/das";
        if (naviPointEntry != null) article = naviPointEntry.getArticleFourthCase().replace("none", "");
        return location.startsWith("Haus ") ? location : article + (article.isEmpty() ? "" : " ") + location;
    }
}