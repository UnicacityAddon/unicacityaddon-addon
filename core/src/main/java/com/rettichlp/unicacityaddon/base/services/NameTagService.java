package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.alliance.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.faction.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.specific.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.streetwar.Streetwar;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.listener.faction.ContractListener;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.network.ClientPacketListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RettichLP
 */
@Getter
@Setter
public class NameTagService {

    private Map<String, Boolean> blacklistPlayerMap;
    private Map<String, WantedListener.Wanted> wantedPlayerMap;
    private Collection<String> noPushPlayerList;
    private Collection<String> maskedPlayerList;

    private final UnicacityAddon unicacityAddon;

    public NameTagService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
        this.blacklistPlayerMap = new HashMap<>();
        this.wantedPlayerMap = new HashMap<>();
        this.noPushPlayerList = Collections.emptyList();
        this.maskedPlayerList = Collections.emptyList();
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
            WantedListener.Wanted wanted = this.wantedPlayerMap.get(playerName);
            if (wanted != null) {
                prefix.append(this.getWpColor(wanted.getAmount()).getCode());
            }

            if (this.blacklistPlayerMap.get(playerName) != null)
                prefix.append(specific.color().getOrDefault(ColorCode.DARK_RED).getCode());

            if (ContractListener.CONTRACT_LIST.contains(playerName))
                prefix.append(specific.color().getOrDefault(ColorCode.DARK_RED).getCode());
        }

        return prefix.toString();
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
        ClientPacketListener clientPacketListener = this.unicacityAddon.labyAPI().minecraft().getClientPacketListener();
        return clientPacketListener != null && this.unicacityAddon.services().util().isUnicacity() && clientPacketListener.getNetworkPlayerInfos().stream()
                .map(networkPlayerInfo -> this.unicacityAddon.services().util().textUtils().legacy(networkPlayerInfo.displayName()))
                .anyMatch(s -> s.startsWith("§8[§9UC§8]§c") && s.contains(playerName));
    }
}