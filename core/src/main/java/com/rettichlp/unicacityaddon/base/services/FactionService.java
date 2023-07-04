package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.network.ClientPacketListener;

/**
 * @author RettichLP
 */
public class FactionService {

    @Getter
    @Setter
    private boolean tempDuty = false;

    private final UnicacityAddon unicacityAddon;

    public FactionService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public boolean checkPlayerDuty(String playerName) {
        boolean duty = false;

        try {
            ClientPacketListener clientPacketListener = this.unicacityAddon.labyAPI().minecraft().getClientPacketListener();
            duty = tempDuty || (clientPacketListener != null && this.unicacityAddon.utilService().isUnicacity() && clientPacketListener.getNetworkPlayerInfos().stream()
                    .map(networkPlayerInfo -> this.unicacityAddon.utilService().text().legacy(networkPlayerInfo.displayName()))
                    .anyMatch(s -> s.contains(playerName) && (s.startsWith("§1") || s.startsWith("§9") || s.startsWith("§4") || s.startsWith("§6"))));
        } catch (IllegalStateException e) {
            this.unicacityAddon.utilService().debug("Can't retrieve player duty for " + playerName);
            this.unicacityAddon.logger().warn(e.getMessage());
        }

        return duty;
    }

    public String getNameTagSuffix(Faction faction) {
        return this.unicacityAddon.configuration().nametag().info().get() ? faction.getNameTagSuffix() : "";
    }

    public String getWebsiteSource(Faction faction) {
        try {
            return this.unicacityAddon.webService().sendRequest(faction.getWebsiteUrl());
        } catch (APIResponseException e) {
            return "";
        }
    }
}