package com.rettichlp.UnicacityAddon.base.faction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.ListUtils;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import net.labymod.main.LabyMod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class FactionHandler {

    private static final Map<String,Faction> playerFactionMap = new HashMap<>();
    private static final Map<String,Integer> playerRankMap = new HashMap<>();
    private static String websiteSource = "";

    public static Map<String, Faction> getPlayerFactionMap() {
        if (playerFactionMap.isEmpty()) syncPlayerFactions();
        return playerFactionMap;
    }

    public static Map<String, Integer> getPlayerRankMap() {
        if (playerRankMap.isEmpty()) syncPlayerRanks();
        return playerRankMap;
    }

    @Deprecated
    public static boolean checkPlayerHouseBan(String playerName) { // TODO: 25.09.2022  
        if (websiteSource.isEmpty()) {
            Thread thread = new Thread(() -> websiteSource = WebsiteUtils.websiteToString("https://fuzzlemann.de/commons/houseBans").getKey());
            thread.start();
        }
        return websiteSource.contains(playerName);
    }

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

    public static void syncPlayerFactions() {
        Thread thread = new Thread(() -> {
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> playerFactionMap.put(name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""), faction));
            }
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Fraktionen aktualisiert.");
        });
        thread.start();
    }

    public static void syncPlayerRanks() {
        Thread thread = new Thread(() -> {
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                List<String> rankList = ListUtils.getAllMatchesFromString(PatternHandler.RANK_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> playerRankMap.put(
                        name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""),
                        Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                                .replace("<strong>Rang ", "")
                                .charAt(0)))));
            }
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Ränge aktualisiert.");
        });
        thread.start();
    }
}