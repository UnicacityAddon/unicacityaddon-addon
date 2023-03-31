package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
@UCEvent
public class EventListener {

    private final UnicacityAddon unicacityAddon;

    public EventListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_30)) {
            AddonPlayer p = this.unicacityAddon.player();

            if (this.unicacityAddon.isUnicacity() && p.hasGangwar() && p.isPrioritizedMember()) {
                Scoreboard scoreboard = p.getScoreboard();

                ScoreboardScore attackerScore = scoreboard.getScores(scoreboard.getObjective(DisplaySlot.SIDEBAR)).stream()
                        .filter(score -> score.getName().contains("Angreifer"))
                        .findFirst()
                        .orElse(null);

                ScoreboardScore defenderScore = scoreboard.getScores(scoreboard.getObjective(DisplaySlot.SIDEBAR)).stream()
                    .filter(score -> score.getName().contains("Verteidiger"))
                    .findFirst()
                    .orElse(null);

                if (attackerScore != null && defenderScore != null) {
                    this.unicacityAddon.api().sendEventGangwarRequest(attackerScore.getValue(), defenderScore.getValue());
                }
            }
        }
    }
}