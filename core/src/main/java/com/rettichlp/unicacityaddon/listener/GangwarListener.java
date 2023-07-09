package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCEvent
public class GangwarListener {

    private final UnicacityAddon unicacityAddon;

    public GangwarListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.GANGWAR_CAPTURE_START_PATTERN.matcher(msg).find()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    GangwarListener.this.unicacityAddon.player().sendServerMessage("/capture");
                }
            }, TimeUnit.SECONDS.toMillis(17));
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_30)) {
            AddonPlayer p = this.unicacityAddon.player();

            if (this.unicacityAddon.utilService().isUnicacity() && p.hasGangwar() && p.isPrioritizedMember()) {
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