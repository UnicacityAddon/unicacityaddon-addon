package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.events.BankRobEndedEvent;
import com.rettichlp.unicacityaddon.base.events.BankRobStartedEvent;
import com.rettichlp.unicacityaddon.base.events.BombPlantedEvent;
import com.rettichlp.unicacityaddon.base.events.BombRemovedEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author RettichLP
 */
@UCEvent
public class MajorEventListener {

    private Long bombPlantedTime;
    private String location;

    private final UnicacityAddon unicacityAddon;

    public MajorEventListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: Rettich: *tiefe Erzähler-Stimme* "Enhanced Conduit!" - RettichLP als Nick ihm Enhanced Energy Conduits gab, 21.03.2023
     */
    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        ChatMessage chatMessage = e.chatMessage();
        String msg = chatMessage.getPlainText();
        String formattedMsg = this.unicacityAddon.utilService().text().legacy(chatMessage.originalComponent());

        Matcher bombPlantedMatcher = PatternHandler.BOMB_PLANTED_PATTERN.matcher(msg);
        if (bombPlantedMatcher.find()) {
            this.bombPlantedTime = System.currentTimeMillis();
            Laby.labyAPI().eventBus().fire(new BombPlantedEvent());
            this.unicacityAddon.soundController().playBombPlantedSound();

            boolean isPoliceOrFbi = p.getFaction().equals(Faction.POLIZEI) || p.getFaction().equals(Faction.FBI) || p.isSuperUser();
            boolean isRank4 = p.getRank() > 3 || p.isSuperUser();

            if (isPoliceOrFbi) {
                Faction allianceFaction = p.getFaction().equals(Faction.POLIZEI) ? Faction.FBI : Faction.POLIZEI;
                this.location = bombPlantedMatcher.group("location");
                e.setMessage(Message.getBuilder()
                        .add(formattedMsg).space()
                        .of("[↑]").color(ColorCode.BLUE)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt den " + allianceFaction.getDisplayName() + "-Öffentlich-Channel").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin id=" + allianceFaction.getPublicChannelId())
                                .advance().space()
                        .of(isRank4 ? "[" : "").color(ColorCode.DARK_GRAY).advance()
                        .of(isRank4 ? "Sperrgebiet ausrufen" : "").color(ColorCode.RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet ausrufen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sperrgebiet " + getLocationWithArticle(this.location))
                                .advance()
                        .of(isRank4 ? "]" : "").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }

            if (p.getFaction().equals(Faction.POLIZEI)) {
                this.unicacityAddon.api().sendEventBombRequest(System.currentTimeMillis());
            }

            return;
        }

        Matcher bombRemovedMatcher = PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg);
        if (bombRemovedMatcher.find()) {
            Long timeSinceBombPlanted = this.bombPlantedTime != null ? System.currentTimeMillis() - this.bombPlantedTime : null;
            String timeString = timeSinceBombPlanted != null ? this.unicacityAddon.utilService().text().parseTimer(TimeUnit.MILLISECONDS.toSeconds(timeSinceBombPlanted)) : "";

            String state = bombRemovedMatcher.group(1);

            e.setMessage(Message.getBuilder()
                    .add(formattedMsg).space()
                    .of(timeString.isEmpty() ? "" : "(").color(ColorCode.DARK_GRAY).advance()
                    .of(timeString).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
                    .of(timeString.isEmpty() ? "" : ")").color(ColorCode.DARK_GRAY).advance().space()
                    .of(this.location != null ? "[" : "").color(ColorCode.DARK_GRAY).advance()
                    .of(this.location != null ? "Sperrgebiet aufheben" : "").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Sperrgebiet aufheben").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, location != null ? "/removesperrgebiet " + getLocationWithArticle(this.location) : "")
                            .advance()
                    .of(this.location != null ? "]" : "").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());

            Laby.labyAPI().eventBus().fire(new BombRemovedEvent());
        }

        Matcher bankRobStartedMatcher = PatternHandler.BANK_ROB_STARTED_PATTERN.matcher(msg);
        if (bankRobStartedMatcher.find()) {
            Laby.labyAPI().eventBus().fire(new BankRobStartedEvent());
            this.unicacityAddon.soundController().playBankRobStartedSound();

            if (p.getFaction().equals(Faction.POLIZEI)) {
                this.unicacityAddon.api().sendEventBankRobRequest(System.currentTimeMillis());
            }

            return;
        }

        Matcher bankRobEndedMatcher = PatternHandler.BANK_ROB_ENDED_PATTERN.matcher(msg);
        if (bankRobEndedMatcher.find()) {
            Laby.labyAPI().eventBus().fire(new BankRobEndedEvent());
        }
    }

    private String getLocationWithArticle(String location) {
        NaviPoint naviPoint = find(this.unicacityAddon.api().getNaviPointList(), n -> n.getName().equalsIgnoreCase(location.replace(" ", "-")));
        String article = "der/die/das";
        if (naviPoint != null)
            article = naviPoint.getArticleFourthCase().replace("none", "");
        return location.startsWith("Haus ") ? location : article + (article.isEmpty() ? "" : " ") + location;
    }
}