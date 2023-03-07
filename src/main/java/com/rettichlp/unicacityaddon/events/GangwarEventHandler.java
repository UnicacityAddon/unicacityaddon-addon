package com.rettichlp.unicacityaddon.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.UpdateUtils;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class GangwarEventHandler {

    public static void sendData() {
        UPlayer p = AbstractionLayer.getPlayer();

        if (UnicacityAddon.isUnicacity() && p.hasGangwar() && isDataSender()) {
            Scoreboard scoreboard = p.getWorldScoreboard();
            if (scoreboard.getObjectiveNames().containsAll(Arrays.asList("Angreifer", "Verteidiger"))) {
                Score attackerScore = scoreboard.getScores().stream()
                        .filter(score -> score.getPlayerName().contains("Angreifer:"))
                        .findFirst()
                        .orElse(null);

                Score defenderScore = scoreboard.getScores().stream()
                        .filter(score -> score.getPlayerName().contains("Verteidiger:"))
                        .findFirst()
                        .orElse(null);

                if (attackerScore != null && defenderScore != null) {
                    APIRequest.sendGangwarDataRequest(attackerScore.getScorePoints(), defenderScore.getScorePoints());
                }
            }
        }
    }

    private static boolean isDataSender() {
        UPlayer p = AbstractionLayer.getPlayer();
        int rank = p.getRank();

        Map<String, Integer> filteredPlayerMap = Syncer.PLAYERFACTIONMAP.entrySet().stream()
                .filter(e -> e.getValue().equals(p.getFaction())) // name and faction from faction
                .map(Map.Entry::getKey) // name of players from faction
                .filter(s -> ForgeUtils.getOnlinePlayers().contains(s)) // is online
                .filter(GangwarEventHandler::hasSupportedAddonVersion) // has supported addon version
                .collect(Collectors.toMap(s -> s, Syncer.PLAYERRANKMAP::get)); // collect name and rank of players from faction

        UnicacityAddon.debug(filteredPlayerMap.keySet().toString());

        boolean hasAnyPlayerHigherRank = filteredPlayerMap.entrySet().stream()
                .anyMatch(stringIntegerEntry -> stringIntegerEntry.getValue() > rank); // has a higher rank than himself

        UnicacityAddon.debug("ANY HIGHER RANK: " + hasAnyPlayerHigherRank);

        boolean hasRankPriority = filteredPlayerMap.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(rank))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList())
                .get(0)
                .equals(p.getName());

        UnicacityAddon.debug("RANK PRIORITY: " + hasRankPriority);

        UnicacityAddon.debug("IS SENDER: " + (!hasAnyPlayerHigherRank && hasRankPriority));
        return !hasAnyPlayerHigherRank && hasRankPriority;
    }

    private static boolean hasSupportedAddonVersion(String name) {
        JsonArray response = APIRequest.sendManagementUserRequest();
        AtomicBoolean accept = new AtomicBoolean(false);
        if (response != null) {
            response.forEach(jsonElement -> {
                if (!accept.get()) {
                    String uuid = UnicacityAddon.MINECRAFT.getConnection().getPlayerInfo(name).getGameProfile().getId().toString().replace("-", "");
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    boolean hasAddon = jsonObject.get("uuid").getAsString().equals(uuid);
                    boolean hasVersion = jsonObject.get("version").getAsString().equals(UpdateUtils.latestVersion);
                    accept.set(hasAddon && hasVersion);
                }
            });
        }
        return accept.get();
    }
}