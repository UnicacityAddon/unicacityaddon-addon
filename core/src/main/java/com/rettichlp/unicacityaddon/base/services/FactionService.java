package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import net.labymod.api.client.network.ClientPacketListener;

/**
 * @author RettichLP
 */
public class FactionService {

    private final UnicacityAddon unicacityAddon;

    public FactionService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public boolean checkPlayerDuty(String playerName) {
        ClientPacketListener clientPacketListener = this.unicacityAddon.labyAPI().minecraft().getClientPacketListener();
        return clientPacketListener != null && this.unicacityAddon.services().util().isUnicacity() && clientPacketListener.getNetworkPlayerInfos().stream()
                .map(networkPlayerInfo -> this.unicacityAddon.services().util().text().legacy(networkPlayerInfo.displayName()))
                .filter(s -> s.startsWith("§1") || s.startsWith("§9") || s.startsWith("§4") || s.startsWith("§6"))
                .anyMatch(s -> s.contains(playerName));
    }

    public String getNameTagSuffix(Faction faction) {
        return this.unicacityAddon.configuration().nametag().info().get() ? faction.getNameTagSuffix() : "";
    }

    public String getWebsiteSource(Faction faction) {
        try {
            return this.unicacityAddon.services().web().sendRequest(faction.getWebsiteUrl());
        } catch (APIResponseException e) {
            return "";
        }
    }
}