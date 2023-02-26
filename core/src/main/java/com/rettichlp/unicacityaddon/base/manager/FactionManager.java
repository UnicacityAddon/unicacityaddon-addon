package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.ListUtils;
import com.rettichlp.unicacityaddon.listener.TabListEventHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class FactionManager {

    private static FactionManager instance;
    private static Map<String, Map.Entry<Faction, Integer>> factionData = new HashMap<>();

    private FactionManager() {
        generateFactionData();
    }

    public static FactionManager getInstance() {
        if (instance == null) {
            instance = new FactionManager();
        }
        return instance;
    }

    public Map<String, Map.Entry<Faction, Integer>> getFactionData() {
        return factionData;
    }

    public static void generateFactionData() {
        new Thread(() -> {

            Future<Map<String, Map.Entry<Faction, Integer>>> factionDataFuture = getFutureFactionData();

            while (!factionDataFuture.isDone()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                factionData = factionDataFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static boolean checkPlayerDuty(String playerName) {
        if (UnicacityAddon.ADDON.labyAPI().minecraft().isSingleplayer())
            return false;
        return UnicacityAddon.ADDON.labyAPI().minecraft().getClientPacketListener().getNetworkPlayerInfos().stream()
                .map(TabListEventHandler::getTablistName)
                .filter(s -> s.startsWith("§1[UC]") || s.startsWith("§1") || s.startsWith("§9[UC]") || s.startsWith("§9") || s.startsWith("§4[UC]") || s.startsWith("§4") || s.startsWith("§6[UC]") || s.startsWith("§6"))
                .collect(Collectors.toList()).stream()
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .anyMatch(s -> Objects.equals(s, playerName));
    }

    private static Future<Map<String, Map.Entry<Faction, Integer>>> getFutureFactionData() {
        return Executors.newSingleThreadExecutor().submit(() -> {
            Map<String, Map.Entry<Faction, Integer>> factionData = new HashMap<>();

            for (Faction faction : Faction.values()) {
                String factionWebsiteSource = faction.getWebsiteSource();
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, factionWebsiteSource);
                List<String> rankList = ListUtils.getAllMatchesFromString(PatternHandler.RANK_PATTERN, factionWebsiteSource);
                nameList.forEach(name -> {
                    String formattedname = name.replace("<h4 class=\"h5 g-mb-5\"><strong>", "");
                    int rank = Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                            .replace("<strong>Rang ", "")
                            .charAt(0)));

                    factionData.put(formattedname, new AbstractMap.SimpleEntry<>(faction, rank));
                });
            }

            return factionData;
        });
    }
}