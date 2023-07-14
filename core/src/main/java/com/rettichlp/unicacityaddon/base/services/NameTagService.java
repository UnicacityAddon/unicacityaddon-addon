package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.alliance.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.faction.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.specific.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.streetwar.Streetwar;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.Laby;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RettichLP
 */
@Getter
@Setter
public class NameTagService {

    private Map<String, Boolean> blacklistPlayerMap;
    private Collection<String> contractList;
    private Collection<WantedListener.Wanted> wantedList;

    private final UnicacityAddon unicacityAddon;

    public NameTagService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
        this.blacklistPlayerMap = new HashMap<>();
        this.contractList = new ArrayList<>();
        this.wantedList = new ArrayList<>();
    }

    public String getPrefix(String playerName, boolean isCorpse) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());
        if (isCorpse)
            prefix.append(ColorCode.GRAY.getCode());

        NameTagConfiguration nameTagConfiguration = this.unicacityAddon.configuration().nametag();
        if (this.unicacityAddon.api().getPlayerFactionMap().containsKey(playerName)) {
            com.rettichlp.unicacityaddon.base.enums.faction.Faction targetPlayerFaction = this.unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, com.rettichlp.unicacityaddon.base.enums.faction.Faction.NULL);

            Faction faction = nameTagConfiguration.faction();
            if (faction.enabled().get()) {
                if (targetPlayerFaction.equals(this.unicacityAddon.player().getFaction()))
                    prefix.append(faction.color().getOrDefault(ColorCode.BLUE).getCode());
            }

            Alliance alliance = nameTagConfiguration.alliance();
            if (alliance.enabled().get()) {
                ColorCode allianceColor = alliance.color().getOrDefault(ColorCode.DARK_PURPLE);
                com.rettichlp.unicacityaddon.base.enums.faction.Faction allianceFaction1 = alliance.faction1().getOrDefault(com.rettichlp.unicacityaddon.base.enums.faction.Faction.NULL);
                com.rettichlp.unicacityaddon.base.enums.faction.Faction allianceFaction2 = alliance.faction2().getOrDefault(com.rettichlp.unicacityaddon.base.enums.faction.Faction.NULL);
                if (targetPlayerFaction.equals(allianceFaction1) || targetPlayerFaction.equals(allianceFaction2))
                    prefix.append(allianceColor.getCode());
            }

            Streetwar streetwar = nameTagConfiguration.streetwar();
            if (streetwar.enabled().get()) {
                ColorCode streetwarColor = streetwar.color().getOrDefault(ColorCode.RED);
                com.rettichlp.unicacityaddon.base.enums.faction.Faction streetwarFaction1 = streetwar.faction1().getOrDefault(com.rettichlp.unicacityaddon.base.enums.faction.Faction.NULL);
                com.rettichlp.unicacityaddon.base.enums.faction.Faction streetwarFaction2 = streetwar.faction2().getOrDefault(com.rettichlp.unicacityaddon.base.enums.faction.Faction.NULL);
                if (targetPlayerFaction.equals(streetwarFaction1) || targetPlayerFaction.equals(streetwarFaction2))
                    prefix.append(streetwarColor.getCode());
            }
        }

        Specific specific = nameTagConfiguration.specific();
        if (specific.enabled().get()) {
            this.wantedList.stream()
                    .filter(wanted -> wanted.getName().equals(playerName))
                    .findFirst()
                    .ifPresent(wanted -> prefix.append(this.getWpColor(wanted.getAmount()).getCode()));

            if (this.blacklistPlayerMap.get(playerName) != null)
                prefix.append(specific.color().getOrDefault(ColorCode.DARK_RED).getCode());

            if (this.contractList.contains(playerName))
                prefix.append(specific.color().getOrDefault(ColorCode.DARK_RED).getCode());
        }

        return prefix.toString();
    }

    public boolean isMasked(String playerName) {
        return this.unicacityAddon.player().getScoreboard().getTeams().stream()
                .filter(scoreboardTeam -> scoreboardTeam.getTeamName().equals("masked"))
                .findFirst()
                .map(ScoreboardTeam::getEntries)
                .map(strings -> strings.contains(playerName))
                .orElse(false);
    }

    public boolean isNoPush(String playerName) {
        return this.unicacityAddon.player().getScoreboard().getTeams().stream()
                .filter(scoreboardTeam -> scoreboardTeam.getTeamName().equals("nopush"))
                .findAny()
                .map(ScoreboardTeam::getEntries)
                .map(strings -> strings.contains(playerName))
                .orElse(false);
    }

    public ColorCode getWpColor(int wantedPointAmount) {
        ColorCode colorCode;

        if (wantedPointAmount >= 60) {
            colorCode = ColorCode.DARK_RED;
        } else if (wantedPointAmount >= 50) {
            colorCode = ColorCode.RED;
        } else if (wantedPointAmount >= 25) {
            colorCode = ColorCode.GOLD;
        } else if (wantedPointAmount >= 15) {
            colorCode = ColorCode.YELLOW;
        } else if (wantedPointAmount >= 2) {
            colorCode = ColorCode.GREEN;
        } else {
            colorCode = ColorCode.DARK_GREEN;
        }

        return colorCode;
    }

    public boolean isAdminDuty(String playerName) {
        boolean duty = false;

        try {
            ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
            duty = clientPacketListener != null && this.unicacityAddon.utilService().isUnicacity() && clientPacketListener.getNetworkPlayerInfos().stream()
                    .map(networkPlayerInfo -> this.unicacityAddon.utilService().text().legacy(networkPlayerInfo.displayName()))
                    .anyMatch(s -> s.contains(playerName) && s.startsWith("§8[§9UC§8]§c"));
        } catch (IllegalStateException e) {
            this.unicacityAddon.utilService().debug("Can't retrieve admin duty for " + playerName);
            this.unicacityAddon.logger().warn(e.getMessage());
        }

        return duty;
    }
}