package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

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
            boolean isDuty = Optional.ofNullable(clientPacketListener)
                    .map(ClientPacketListener::getNetworkPlayerInfos).orElse(Collections.emptyList()).stream()
                    .map(NetworkPlayerInfo::displayName)
                    .collect(Collectors.toMap(component -> this.unicacityAddon.utilService().text().plain(component).replace(" AFK", ""), component -> this.unicacityAddon.utilService().text().legacy(component)))
                    .entrySet().stream()
                    .filter(plainLegacyEntry -> plainLegacyEntry.getValue().startsWith("§1") || plainLegacyEntry.getValue().startsWith("§9") || plainLegacyEntry.getValue().startsWith("§4") || plainLegacyEntry.getValue().startsWith("§6"))
                    .anyMatch(plainLegacyEntry -> playerName.contains(plainLegacyEntry.getKey()) || plainLegacyEntry.getKey().contains(playerName));

            duty = tempDuty || (this.unicacityAddon.utilService().isUnicacity() && isDuty);
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