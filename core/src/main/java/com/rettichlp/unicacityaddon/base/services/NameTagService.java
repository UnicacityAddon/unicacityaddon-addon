package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
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

        NameTagSetting nameTagSetting = this.unicacityAddon.configuration().nameTagSetting();
        if (this.unicacityAddon.api().getPlayerFactionMap().containsKey(playerName)) {
            Faction targetPlayerFaction = this.unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL);

            FactionNameTagSetting factionNameTagSetting = nameTagSetting.factionNameTagSetting();
            if (factionNameTagSetting.enabled().get()) {
                if (targetPlayerFaction.equals(this.unicacityAddon.player().getFaction()))
                    prefix.append(factionNameTagSetting.color().getOrDefault(ColorCode.BLUE).getCode());
            }

            AllianceFactionNameTagSetting allianceFactionNameTagSetting = nameTagSetting.allianceFactionNameTagSetting();
            if (allianceFactionNameTagSetting.enabled().get()) {
                ColorCode allianceColor = allianceFactionNameTagSetting.color().getOrDefault(ColorCode.DARK_PURPLE);
                Faction allianceFaction1 = allianceFactionNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction allianceFaction2 = allianceFactionNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(allianceFaction1) || targetPlayerFaction.equals(allianceFaction2))
                    prefix.append(allianceColor.getCode());
            }

            StreetwarNameTagSetting streetwarNameTagSetting = nameTagSetting.streetwarNameTagSetting();
            if (streetwarNameTagSetting.enabled().get()) {
                ColorCode streetwarColor = streetwarNameTagSetting.color().getOrDefault(ColorCode.RED);
                Faction streetwarFaction1 = streetwarNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction streetwarFaction2 = streetwarNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(streetwarFaction1) || targetPlayerFaction.equals(streetwarFaction2))
                    prefix.append(streetwarColor.getCode());
            }
        }

        SpecificNameTagSetting specificNameTagSetting = nameTagSetting.specificNameTagSetting();
        if (specificNameTagSetting.enabled().get()) {
            WantedListener.Wanted wanted = this.wantedPlayerMap.get(playerName);
            if (wanted != null) {
                prefix.append(this.getWpColor(wanted.getAmount()).getCode());
            }

            if (this.blacklistPlayerMap.get(playerName) != null)
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());

            if (ContractListener.CONTRACT_LIST.contains(playerName))
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());
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
        return clientPacketListener != null && this.unicacityAddon.services().utilService().isUnicacity() && clientPacketListener.getNetworkPlayerInfos().stream()
                .map(networkPlayerInfo -> this.unicacityAddon.services().utilService().textUtils().legacy(networkPlayerInfo.displayName()))
                .anyMatch(s -> s.startsWith("§8[§9UC§8]§c") && s.contains(playerName));
    }
}