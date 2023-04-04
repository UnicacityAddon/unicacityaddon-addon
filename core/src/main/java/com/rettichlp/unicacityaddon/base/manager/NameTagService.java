package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.listener.faction.ContractListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistListener;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class NameTagService {

    private Collection<String> noPushPlayerList;

    private final UnicacityAddon unicacityAddon;

    public NameTagService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public Collection<String> getNoPushPlayerList() {
        return noPushPlayerList;
    }

    public void setNoPushPlayerList(Collection<String> noPushPlayerList) {
        this.noPushPlayerList = noPushPlayerList;
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
            WantedListener.Wanted wanted = WantedListener.WANTED_MAP.get(playerName);
            if (wanted != null) {
                prefix.append(this.getWpColor(wanted.getAmount()).getCode());
            }

            if (BlacklistListener.BLACKLIST_MAP.get(playerName) != null)
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
        return this.unicacityAddon.isUnicacity() && this.unicacityAddon.labyAPI().minecraft().getClientPacketListener().getNetworkPlayerInfos().stream()
                .map(networkPlayerInfo -> TextUtils.legacy(networkPlayerInfo.displayName()))
                .filter(s -> s.startsWith("&8[&9UC&8]&c"))
                .collect(Collectors.toList()).stream()
                .map(TextUtils::stripColor)
                .map(TextUtils::stripPrefix)
                .anyMatch(s -> Objects.equals(s, playerName));
    }
}