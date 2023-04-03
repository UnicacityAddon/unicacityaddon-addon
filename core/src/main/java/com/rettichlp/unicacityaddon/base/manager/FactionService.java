package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.base.utils.WebsiteUtils;
import jdk.internal.joptsimple.internal.Strings;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class FactionService {

    private final UnicacityAddon unicacityAddon;

    public FactionService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public boolean checkPlayerDuty(String playerName) {
        return this.unicacityAddon.isUnicacity() && this.unicacityAddon.labyAPI().minecraft().getClientPacketListener().getNetworkPlayerInfos().stream()
                .map(networkPlayerInfo -> TextUtils.legacy(networkPlayerInfo.displayName()))
                .filter(s -> s.startsWith("&1[UC]") || s.startsWith("&1") || s.startsWith("&9[UC]") || s.startsWith("&9") || s.startsWith("&4[UC]") || s.startsWith("&4") || s.startsWith("&6[UC]") || s.startsWith("&6"))
                .collect(Collectors.toList()).stream()
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .anyMatch(s -> Objects.equals(s, playerName));
    }

    public String getNameTagSuffix(Faction faction) {
        return this.unicacityAddon.configuration().nameTagSetting().factionInfo().get() ? faction.getNameTagSuffix() : "";
    }

    public String getWebsiteSource(Faction faction) {
        try {
            return WebsiteUtils.sendRequest(faction.getWebsiteUrl(), this.unicacityAddon);
        } catch (APIResponseException e) {
            return Strings.EMPTY;
        }
    }
}