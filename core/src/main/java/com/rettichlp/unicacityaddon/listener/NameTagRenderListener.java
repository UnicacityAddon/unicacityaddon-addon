package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;

import java.util.Optional;

/**
 * @author RettichLP
 */
@UCEvent
public class NameTagRenderListener {

    private final UnicacityAddon unicacityAddon;

    public NameTagRenderListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Wenn ich gleich nicht mehr antworte, einfach laut meinen Namen sagen." - Lou, 02.10.2022
     * "Fällst du dann aus dem Bett?" - RettichLP und Ullrich, 02.10.2022
     */
    @Subscribe
    public void onPlayerNameTagRender(PlayerNameTagRenderEvent e) {
        if (e.context().equals(PlayerNameTagRenderEvent.Context.PLAYER_RENDER)) {
            NetworkPlayerInfo networkPlayerInfo = e.playerInfo();

            Optional<ScoreboardTeam> optionalScoreboardTeam = this.unicacityAddon.player().getScoreboard().getTeams().stream()
                    .filter(scoreboardTeam -> scoreboardTeam.getTeamName().contains("MASKED"))
                    .findAny();

            if (optionalScoreboardTeam.isPresent()) {
                this.unicacityAddon.debug("MASKEDLIST=" + optionalScoreboardTeam.get().getEntries());
                this.unicacityAddon.debug("MASKED=" + optionalScoreboardTeam.get().getEntries().contains(networkPlayerInfo.displayName()));
            }

            // TODO: 03.04.2023
            // if (e.nameTag().toString().contains("§k") || e.nameTag().toString().contains("&k"))
            //     UnicacityAddon.debug("NAMETAG: " + e.nameTag() + " " + networkPlayerInfo.profile().getUsername());

            if (networkPlayerInfo != null && !e.nameTag().style().isDecorationSet(TextDecoration.OBFUSCATED)) {
                String playerName = networkPlayerInfo.profile().getUsername();
                String prefix = this.unicacityAddon.nametagService().getPrefix(playerName, false);

                if (!prefix.equals(FormattingCode.RESET.getCode())) {
                    e.setNameTag(Message.getBuilder().add(prefix + playerName).createComponent());
                }
            }
        }
    }

    @Subscribe
    public void onScoreboardTeamUpdate(ScoreboardTeamUpdateEvent e) {
        this.unicacityAddon.player().getScoreboard().getTeams().stream()
                .filter(scoreboardTeam -> scoreboardTeam.getTeamName().equals("nopush"))
                .findFirst()
                .ifPresent(scoreboardTeam -> this.unicacityAddon.nametagService().setNoPushPlayerList(scoreboardTeam.getEntries()));

        this.unicacityAddon.player().getScoreboard().getTeams().stream()
                .filter(scoreboardTeam -> scoreboardTeam.getTeamName().equals("masked"))
                .findFirst()
                .ifPresent(scoreboardTeam -> this.unicacityAddon.nametagService().setMaskedPlayerList(scoreboardTeam.getEntries()));
    }
}