package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;

/**
 * @author RettichLP
 */
public class GangwarEventHandler {

    public static void sendData() {
        UPlayer p = AbstractionLayer.getPlayer();

        if (UnicacityAddon.isUnicacity() && p.hasGangwar() && p.isPrioritizedMember()) {
            Scoreboard scoreboard = p.getWorldScoreboard();
            Score attackerScore = scoreboard.getScores().stream()
                    .filter(score -> score.getPlayerName().contains("Angreifer"))
                    .findFirst()
                    .orElse(null);

            Score defenderScore = scoreboard.getScores().stream()
                    .filter(score -> score.getPlayerName().contains("Verteidiger"))
                    .findFirst()
                    .orElse(null);

            if (attackerScore != null && defenderScore != null) {
                APIRequest.sendEventGangwarRequest(attackerScore.getScorePoints(), defenderScore.getScorePoints());
            }
        }
    }
}