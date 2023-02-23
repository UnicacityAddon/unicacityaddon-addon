package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
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
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class CarEventHandler {

    private static final List<Integer> sentTankWarnings = new ArrayList<>();

    private final UnicacityAddon unicacityAddon;

    public CarEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setCarInfo(ColorCode.GREEN.getCode() + "offen");
            return;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setCarInfo(ColorCode.RED.getCode() + "zu");
            return;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && unicacityAddon.configuration().carRoute().get()) {
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
            return;
        }

        Matcher carTicketMatcher = PatternHandler.CAR_TICKET_PATTERN.matcher(msg);
        if (carTicketMatcher.find()) {
            int fine = Integer.parseInt(carTicketMatcher.group(2));

            if (FileManager.DATA.getCashBalance() >= fine) {
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

    public static void checkTank() {
        AddonPlayer p = UnicacityAddon.PLAYER;
        Scoreboard scoreboard = p.getScoreboard();
        ScoreboardScore scoreboardScore = scoreboard.getScores(scoreboard.objective(DisplaySlot.SIDEBAR))
                .stream()
                .filter(score -> score.getName().equals(ColorCode.GREEN.getCode() + "Tank" + ColorCode.DARK_GRAY.getCode() + ":"))
                .findFirst()
                .orElse(null);

        if (scoreboardScore == null)
            return;

        int tank = scoreboardScore.getValue();
        switch (tank) {
            case 100:
                sentTankWarnings.clear();
                break;
            case 15:
            case 10:
            case 5:
                if (!sentTankWarnings.contains(tank)) {
                    p.sendInfoMessage("Dein Tank hat noch " + tank + " Liter.");
                    // TODO: 09.12.2022 Implement sounds
                    // p.playSound("block.note.harp");
                    sentTankWarnings.add(tank);
                }
                break;
        }
    }
}