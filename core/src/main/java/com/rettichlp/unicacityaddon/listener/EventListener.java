package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.scoreboard.Scoreboard;

/**
 * @author RettichLP
 */
@UCEvent
public class EventListener {

    public static void sendData() {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (UnicacityAddon.isUnicacity() && p.hasGangwar() && p.isPrioritizedMember()) {
            Scoreboard scoreboard = p.getScoreboard();
//            Score attackerScore = scoreboard.getScores().stream()
//                    .filter(score -> score.getPlayerName().contains("Angreifer"))
//                    .findFirst()
//                    .orElse(null);
//
//            Score defenderScore = scoreboard.getScores().stream()
//                    .filter(score -> score.getPlayerName().contains("Verteidiger"))
//                    .findFirst()
//                    .orElse(null);

//            if (attackerScore != null && defenderScore != null) {
//                APIRequest.sendEventGangwarRequest(attackerScore.getScorePoints(), defenderScore.getScorePoints());
//            }
        }
    }
}