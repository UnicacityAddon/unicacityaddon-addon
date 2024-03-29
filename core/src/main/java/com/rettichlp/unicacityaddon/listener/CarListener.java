package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.GasStation;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class CarListener {

    private static final List<Integer> sentTankWarnings = new ArrayList<>();

    private final UnicacityAddon unicacityAddon;

    public CarListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.fileService().data().setCarOpen(true);
            return;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.fileService().data().setCarOpen(false);
            return;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && this.unicacityAddon.configuration().carRoute().get()) {
            int x = Integer.parseInt(carPositionMatcher.group(1));
            int y = Integer.parseInt(carPositionMatcher.group(2));
            int z = Integer.parseInt(carPositionMatcher.group(3));

            // set route
            p.setNaviRoute(x, y, z);

            // modify message
            Map.Entry<Double, NaviPoint> nearestNaviPoint = this.unicacityAddon.navigationService().getNearestNaviPoint(x, y, z);
            NaviPoint naviPoint = nearestNaviPoint.getValue();

            e.setMessage(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Car").color(ColorCode.GOLD).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Das Fahrzeug befindet sich in der Nähe von").color(ColorCode.GRAY).advance().space()
                    .of(naviPoint != null ? naviPoint.getDisplayName() : "unbekannter Ort").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, this.unicacityAddon.utilService().command().locationHoverMessage(x, y, z))
                            .advance()
                    .of(".").color(ColorCode.GRAY).advance()
                    .createComponent());

            return;
        }

        Matcher carTicketMatcher = PatternHandler.CAR_TICKET_PATTERN.matcher(msg);
        if (carTicketMatcher.find()) {
            int fine = Integer.parseInt(carTicketMatcher.group(2));

            if (this.unicacityAddon.fileService().data().getCashBalance() >= fine) {
                e.setMessage(Message.getBuilder()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Car").color(ColorCode.GOLD).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Dein Fahrzeug hat ein Strafzettel").color(ColorCode.GRAY).advance().space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of(carTicketMatcher.group(1)).color(ColorCode.GOLD).advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(String.valueOf(fine)).color(ColorCode.GOLD).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Bezahlen").color(ColorCode.GREEN)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(fine + "$ bezahlen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/payticket " + fine)
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }

            return;
        }

        Matcher checkKFZMatcher = PatternHandler.CAR_CHECK_KFZ_PATTERN.matcher(msg);
        if (checkKFZMatcher.find()) {
            String group1 = checkKFZMatcher.group(1);
            String name = group1 != null ? group1 : checkKFZMatcher.group(2);

            if (!name.equals(p.getName())) {
                p.sendServerMessage("/memberinfo " + name);
                p.sendServerMessage("/searchtrunk");
            }
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_5)) {
            AddonPlayer p = this.unicacityAddon.player();
            Scoreboard scoreboard = p.getScoreboard();
            ScoreboardScore scoreboardScore = scoreboard.getScores(scoreboard.getObjective(DisplaySlot.SIDEBAR))
                    .stream()
                    .filter(score -> score.getName().equals(ColorCode.GREEN.getCode() + "Tank" + ColorCode.DARK_GRAY.getCode() + ":"))
                    .findFirst()
                    .orElse(null);

            if (scoreboardScore == null)
                return;

            int tank = scoreboardScore.getValue();
            switch (tank) {
                case 100 -> sentTankWarnings.clear();
                case 15, 10, 5 -> {
                    if (!sentTankWarnings.contains(tank)) {
                        Map.Entry<Double, GasStation> nearestGasStation = this.unicacityAddon.navigationService().getNearestGasStation(p.getLocation());
                        p.sendMessage(Message.getBuilder()
                                .info().space()
                                .of("Dein Tank hat noch " + tank + " Liter.").color(ColorCode.WHITE).advance().space()
                                .of("[").color(ColorCode.DARK_GRAY).advance()
                                .of("Nächste Tankstelle").color(ColorCode.DARK_AQUA)
                                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Nächste Tankstelle").color(ColorCode.RED).advance().createComponent())
                                        .clickEvent(ClickEvent.Action.RUN_COMMAND, nearestGasStation.getValue().getNaviCommand())
                                        .advance()
                                .of("]").color(ColorCode.DARK_GRAY).advance()
                                .createComponent());

                        this.unicacityAddon.soundController().playTankWarningSound();
                        sentTankWarnings.add(tank);
                    }
                }
            }
        }
    }
}