package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.entries.BlacklistReasonEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.BroadcastEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.HouseBanEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.HouseBanReasonEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.NaviPointEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.PlayerGroupEntry;
import com.rettichlp.UnicacityAddon.base.api.entries.WantedReasonEntry;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ListUtils;
import net.labymod.main.LabyMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Syncer {

    public static final Map<String, Faction> PLAYERFACTIONMAP = new HashMap<>();
    public static final Map<String, Integer> PLAYERRANKMAP = new HashMap<>();
    public static List<HouseBanEntry> HOUSEBANENTRYLIST = new ArrayList<>();
    public static List<NaviPointEntry> NAVIPOINTLIST = new ArrayList<>();

    public static void syncAll() {
        new Thread(() -> {
            Thread t0 = syncPlayerAddonGroupMap();
            Thread t1 = syncPlayerFactionMap();
            Thread t2 = syncPlayerRankMap();
            Thread t3 = syncHousebanEntryList();
            Thread t4 = syncNaviPointEntryList();

            try {
                t0.start();
                t0.join();

                t1.start();
                t1.join();

                t2.start();
                t2.join();

                t3.start();
                t3.join();

                t4.start();
                t4.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static Thread syncPlayerAddonGroupMap() {
        return new Thread(() -> {
            JsonObject response = APIRequest.sendPlayerRequest();
            if (response != null) {
                for (AddonGroup addonGroup : AddonGroup.values()) {
                    addonGroup.getMemberList().clear();
                    for (JsonElement jsonElement : response.getAsJsonArray(addonGroup.getApiName())) {
                        addonGroup.getMemberList().add(jsonElement.getAsJsonObject().get("name").getAsString());
                    }
                }
            }
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Addon Gruppen aktualisiert.");
        });
    }

    public static Thread syncPlayerFactionMap() {
        return new Thread(() -> {
            PLAYERFACTIONMAP.clear();
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERFACTIONMAP.put(name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""), faction));
            }

            getPlayerGroupEntryList("LEMILIEU").forEach(playerGroupEntry -> PLAYERFACTIONMAP.put(playerGroupEntry.getName(), Faction.LEMILIEU));

            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Fraktionen aktualisiert.");
        });
    }

    public static Thread syncPlayerRankMap() {
        return new Thread(() -> {
            PLAYERRANKMAP.clear();
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                List<String> rankList = ListUtils.getAllMatchesFromString(PatternHandler.RANK_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERRANKMAP.put(
                        name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""),
                        Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                                .replace("<strong>Rang ", "")
                                .charAt(0)))));
            }
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Ränge aktualisiert.");
        });
    }

    public static Thread syncHousebanEntryList() {
        return new Thread(() -> {
            HOUSEBANENTRYLIST = getHouseBanEntryList();
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbote aktualisiert.");
        });
    }

    public static Thread syncNaviPointEntryList() {
        return new Thread(() -> {
            NAVIPOINTLIST = getNaviPointEntryList();
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Navipunkte aktualisiert.");
        });
    }

    public static List<BlacklistReasonEntry> getBlacklistReasonEntryList() {
        JsonArray response = APIRequest.sendBlacklistReasonRequest();
        if (response == null) return new ArrayList<>();
        List<BlacklistReasonEntry> blacklistReasonEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            int kills = o.get("kills").getAsInt();
            String reason = o.get("reason").getAsString();
            String issuerUUID = o.get("issuerUUID").getAsString();
            int price = o.get("price").getAsInt();
            String issuerName = o.get("issuerName").getAsString();

            blacklistReasonEntryList.add(new BlacklistReasonEntry(kills, reason, issuerUUID, price, issuerName));
        });
        return blacklistReasonEntryList;
    }

    public static List<BroadcastEntry> getBroadcastEntryList() {
        JsonArray response = APIRequest.sendBroadcastQueueRequest();
        if (response == null) return new ArrayList<>();
        List<BroadcastEntry> broadcastEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String broadcast = o.get("broadcast").getAsString();
            int id = o.get("id").getAsInt();
            String issuerName = o.get("issuerName").getAsString();
            String issuerUUID = o.get("issuerUUID").getAsString();
            long sendTime = o.get("sendTime").getAsLong();
            long time = o.get("time").getAsLong();

            broadcastEntryList.add(new BroadcastEntry(broadcast, id, issuerName, issuerUUID, sendTime, time));
        });
        return broadcastEntryList;
    }

    public static List<HouseBanEntry> getHouseBanEntryList() {
        JsonArray response = APIRequest.sendHouseBanRequest(AbstractionLayer.getPlayer().getFaction().equals(Faction.RETTUNGSDIENST));
        if (response == null) return new ArrayList<>();
        List<HouseBanEntry> houseBanEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            long duration = o.get("duration").getAsLong();
            List<HouseBanReasonEntry> houseBanReasonList = new ArrayList<>();
            long expirationTime = o.get("expirationTime").getAsLong();
            String name = o.get("name").getAsString();
            long startTime = o.get("startTime").getAsLong();
            String uuid = o.get("uuid").getAsString();

            o.get("houseBanReasonList").getAsJsonArray().forEach(jsonElement1 -> {
                JsonObject o1 = jsonElement1.getAsJsonObject();

                String reason = o1.get("reason").getAsString();
                String issuerUUID = o1.has("issuerUUID") ? o1.get("issuerUUID").getAsString() : null;
                String issuerName = o1.has("issuerName") ? o1.get("issuerName").getAsString() : null;
                int days = o1.get("days").getAsInt();

                houseBanReasonList.add(new HouseBanReasonEntry(reason, issuerUUID, issuerName, days));
            });

            houseBanEntryList.add(new HouseBanEntry(duration, houseBanReasonList, expirationTime, name, startTime, uuid));
        });
        return houseBanEntryList;
    }

    public static List<HouseBanReasonEntry> getHouseBanReasonEntryList() {
        JsonArray response = APIRequest.sendHouseBanReasonRequest();
        if (response == null) return new ArrayList<>();
        List<HouseBanReasonEntry> houseBanReasonEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            String creatorUUID = o.has("creatorUUID") ? o.get("creatorUUID").getAsString() : null;
            String creatorName = o.has("creatorName") ? o.get("creatorName").getAsString() : null;
            int days = o.get("days").getAsInt();

            houseBanReasonEntryList.add(new HouseBanReasonEntry(reason, creatorUUID, creatorName, days));
        });
        return houseBanReasonEntryList;
    }

    public static List<NaviPointEntry> getNaviPointEntryList() {
        JsonArray response = APIRequest.sendNaviPointRequest();
        if (response == null) return new ArrayList<>();
        List<NaviPointEntry> naviPointEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String name = o.get("name").getAsString();
            int x = o.get("x").getAsInt();
            int y = o.get("y").getAsInt();
            int z = o.get("z").getAsInt();

            naviPointEntryList.add(new NaviPointEntry(name, x, y, z));
        });
        return naviPointEntryList;
    }

    public static List<String> getPlayerGroupList() {
        JsonArray response = APIRequest.sendPlayerGroupRequest();
        List<String> playerGroupList = new ArrayList<>();
        if (response != null) {
            response.forEach(jsonElement -> playerGroupList.add(jsonElement.getAsString()));
        }
        return playerGroupList;
    }

    public static List<PlayerGroupEntry> getPlayerGroupEntryList(String group) {
        JsonObject response = APIRequest.sendPlayerRequest();
        if (response == null || !response.has(group)) return new ArrayList<>();
        List<PlayerGroupEntry> playerGroupEntryList = new ArrayList<>();
        response.get(group).getAsJsonArray().forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String name = o.get("name").getAsString();
            String uuid = o.get("uuid").getAsString();

            playerGroupEntryList.add(new PlayerGroupEntry(name, uuid));
        });
        return playerGroupEntryList;
    }

    public static List<WantedReasonEntry> getWantedReasonEntryList() {
        JsonArray response = APIRequest.sendWantedReasonRequest();
        if (response == null) return new ArrayList<>();
        List<WantedReasonEntry> wantedReasonEntryList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            String creatorUUID = o.get("creatorUUID").getAsString();
            String creatorName = o.get("creatorName").getAsString();
            int points = o.get("points").getAsInt();

            wantedReasonEntryList.add(new WantedReasonEntry(reason, creatorUUID, creatorName, points));
        });
        return wantedReasonEntryList;
    }
}