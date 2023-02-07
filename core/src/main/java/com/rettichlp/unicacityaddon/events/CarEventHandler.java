package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class CarEventHandler {

    private static final List<Integer> sentTankWarnings = new ArrayList<>();

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
//            CarOpenModule.info = ColorCode.GREEN.getCode() + "offen";
            FileManager.saveData();
            return;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
//            CarOpenModule.info = ColorCode.RED.getCode() + "zu";
            FileManager.saveData();
            return;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && UnicacityAddon.configuration.carRoute().get()) {
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
            return;
        }

        Matcher checkKFZMatcher = PatternHandler.CAR_CHECK_KFZ_PATTERN.matcher(msg);
        if (checkKFZMatcher.find()) {
            String name = checkKFZMatcher.group(1);
            if (name == null)
                name = checkKFZMatcher.group(2);
            p.sendChatMessage("/memberinfo " + name);
        }
    }

    public static void checkTank() {
        UPlayer p = AbstractionLayer.getPlayer();
        Scoreboard scoreboard = p.getWorldScoreboard();
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