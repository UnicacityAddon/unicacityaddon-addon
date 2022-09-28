package com.rettichlp.UnicacityAddon.base.faction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class FactionHandler {

    public static boolean checkPlayerDuty(String playerName) {
        if (UnicacityAddon.MINECRAFT.getConnection() == null) return false;
        return UnicacityAddon.MINECRAFT.getConnection().getPlayerInfoMap().stream()
                .map(TabListEventHandler::getTablistName)
                .filter(s -> s.startsWith("§1[UC]") || s.startsWith("§1") || s.startsWith("§9[UC]") || s.startsWith("§9") || s.startsWith("§4[UC]") || s.startsWith("§4") || s.startsWith("§6[UC]") || s.startsWith("§6"))
                .collect(Collectors.toList()).stream()
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .anyMatch(s -> Objects.equals(s, playerName));
    }
}