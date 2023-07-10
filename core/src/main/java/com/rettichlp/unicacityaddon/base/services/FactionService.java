package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import net.labymod.api.Laby;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;

import java.util.Collections;
import java.util.Optional;
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
        boolean duty = false;

        try {
            ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
            duty = this.unicacityAddon.utilService().isUnicacity() && Optional.ofNullable(clientPacketListener)
                    .map(ClientPacketListener::getNetworkPlayerInfos).orElse(Collections.emptyList()).stream()
                    .map(NetworkPlayerInfo::displayName)
                    .collect(Collectors.toMap(component -> this.unicacityAddon.utilService().text().plain(component), component -> this.unicacityAddon.utilService().text().legacy(component)))
                    .entrySet().stream()
                    .filter(plainLegacyEntry -> plainLegacyEntry.getValue().startsWith("§1") || plainLegacyEntry.getValue().startsWith("§9") || plainLegacyEntry.getValue().startsWith("§4") || plainLegacyEntry.getValue().startsWith("§6"))
                    .anyMatch(plainLegacyEntry -> playerName.contains(plainLegacyEntry.getKey()) || plainLegacyEntry.getKey().contains(playerName));

            // playerName Gelegenheitsdieb matches with plain displayName Gelegenheitsd (short tab name)
            // playerName Joshxa_ matches with plain displayName [UC]Joshxa_ (UC prefix)
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