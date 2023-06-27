package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
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
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));

            Map.Entry<Double, NaviPoint> nearestNaviPoint = this.unicacityAddon.navigationService().getNearestNaviPoint(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
            NaviPoint navipoint = nearestNaviPoint.getValue();
            String navipointString;
            if (navipoint == null) {
                navipointString = "unbekannter Ort";
                p.sendErrorMessage("Navipunkte wurden nicht geladen. Versuche /syncdata um diese neu zu laden!");
            } else {
                navipointString = navipoint.getName().replace("-", " ");
            }
            e.setMessage(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Car").color(ColorCode.GOLD).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Das Fahrzeug befindet sich in der Nähe von").color(ColorCode.GRAY).advance().space()
                    .of(navipointString).color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(carPositionMatcher.group(1) + "/" + carPositionMatcher.group(2) + "/" + carPositionMatcher.group(3)).color(ColorCode.GOLD).advance().createComponent())
                            .advance()
                    .of(".").color(ColorCode.GRAY).advance()
                    .createComponent());


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
                String name = checkKFZMatcher.group(1);
                if (name == null)
                    name = checkKFZMatcher.group(2);
                p.sendServerMessage("/memberinfo " + name);
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
                        p.sendInfoMessage("Dein Tank hat noch " + tank + " Liter.");
                        this.unicacityAddon.soundController().playTankWarningSound();
                        sentTankWarnings.add(tank);
                    }
                }
            }
        }
    }
}