package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.events.TabListEventHandler;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class FactionManager {

    public static boolean checkPlayerDuty(String playerName) {
        if (UnicacityAddon.MINECRAFT.isSingleplayer())
            return false;
        return UnicacityAddon.MINECRAFT.clientPacketListener().getNetworkPlayerInfos().stream()
                .map(TabListEventHandler::getTablistName)
                .filter(s -> s.startsWith("§1[UC]") || s.startsWith("§1") || s.startsWith("§9[UC]") || s.startsWith("§9") || s.startsWith("§4[UC]") || s.startsWith("§4") || s.startsWith("§6[UC]") || s.startsWith("§6"))
                .collect(Collectors.toList()).stream()
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .anyMatch(s -> Objects.equals(s, playerName));
    }
}