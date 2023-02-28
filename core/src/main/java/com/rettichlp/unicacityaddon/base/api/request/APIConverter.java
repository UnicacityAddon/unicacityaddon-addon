package com.rettichlp.unicacityaddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
import com.rettichlp.unicacityaddon.base.models.Broadcast;
import com.rettichlp.unicacityaddon.base.models.HouseBan;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.models.PlayerGroup;
import com.rettichlp.unicacityaddon.base.models.WantedReason;
import com.rettichlp.unicacityaddon.base.text.NotificationCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIConverter {

    public static List<HouseBan> HOUSEBANENTRYLIST = new ArrayList<>();
    public static List<NaviPoint> NAVIPOINTLIST = new ArrayList<>();
    public static Map<PlayerGroup, String> ADDONGROUPMAP = new HashMap<>();

    public static void syncAll() {
        new Thread(() -> {
            Thread t1 = syncPlayerAddonGroupMap();
            Thread t2 = syncHousebanEntryList();
            Thread t3 = syncNaviPointEntryList();

            try {
                t1.start();
                t1.join();

                t2.start();
                t2.join();

                t3.start();
                t3.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private static Thread syncPlayerAddonGroupMap() {
        return new Thread(() -> {
            ADDONGROUPMAP = getPlayerGroupEntryMap();
            NotificationCenter.send(NotificationCenter.SYNCED_ADDON_GROUP_NOTIFICATION);
        });
    }

    private static Thread syncHousebanEntryList() {
        return new Thread(() -> {
            HOUSEBANENTRYLIST = getHouseBanEntryList();
            NotificationCenter.send(NotificationCenter.SYNCED_HOUSE_BANS_NOTIFICATION);
        });
    }

    private static Thread syncNaviPointEntryList() {
        return new Thread(() -> {
            NAVIPOINTLIST = getNaviPointEntryList();
            NotificationCenter.send(NotificationCenter.SYNCED_NAVI_POINTS_NOTIFICATION);
        });
    }

    public static List<BlacklistReason> getBlacklistReasonEntryList() {
        JsonArray response = APIRequest.sendBlacklistReasonRequest();
        if (response == null)
            return new ArrayList<>();
        List<BlacklistReason> blacklistReasonList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            int kills = o.get("kills").getAsInt();
            String reason = o.get("reason").getAsString();
            String issuerUUID = o.get("issuerUUID").getAsString();
            int price = o.get("price").getAsInt();
            String issuerName = o.get("issuerName").getAsString();

            blacklistReasonList.add(new BlacklistReason(kills, reason, issuerUUID, price, issuerName));
        });
        return blacklistReasonList;
    }

    public static List<Broadcast> getBroadcastEntryList() {
        JsonArray response = APIRequest.sendBroadcastQueueRequest();
        if (response == null)
            return new ArrayList<>();
        List<Broadcast> broadcastList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String broadcast = o.get("broadcast").getAsString();
            int id = o.get("id").getAsInt();
            String issuerName = o.get("issuerName").getAsString();
            String issuerUUID = o.get("issuerUUID").getAsString();
            long sendTime = o.get("sendTime").getAsLong();
            long time = o.get("time").getAsLong();

            broadcastList.add(new Broadcast(broadcast, id, issuerName, issuerUUID, sendTime, time));
        });
        return broadcastList;
    }

    public static List<HouseBan> getHouseBanEntryList() {
        JsonArray response = APIRequest.sendHouseBanRequest(UnicacityAddon.PLAYER.getFaction().equals(Faction.RETTUNGSDIENST));
        if (response == null)
            return new ArrayList<>();
        List<HouseBan> houseBanList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            long duration = o.get("duration").getAsLong();
            List<HouseBanReason> houseBanReasonList = new ArrayList<>();
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

                houseBanReasonList.add(new HouseBanReason(reason, issuerUUID, issuerName, days));
            });

            houseBanList.add(new HouseBan(duration, houseBanReasonList, expirationTime, name, startTime, uuid));
        });
        return houseBanList;
    }

    public static List<HouseBanReason> getHouseBanReasonEntryList() {
        JsonArray response = APIRequest.sendHouseBanReasonRequest();
        if (response == null)
            return new ArrayList<>();
        List<HouseBanReason> houseBanReasonList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            String creatorUUID = o.has("creatorUUID") ? o.get("creatorUUID").getAsString() : null;
            String creatorName = o.has("creatorName") ? o.get("creatorName").getAsString() : null;
            int days = o.get("days").getAsInt();

            houseBanReasonList.add(new HouseBanReason(reason, creatorUUID, creatorName, days));
        });
        return houseBanReasonList;
    }

    public static List<NaviPoint> getNaviPointEntryList() {
        JsonArray response = APIRequest.sendNaviPointRequest();
        if (response == null)
            return new ArrayList<>();
        List<NaviPoint> naviPointList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String name = o.get("name").getAsString();
            int x = o.get("x").getAsInt();
            int y = o.get("y").getAsInt();
            int z = o.get("z").getAsInt();
            String article = o.get("article").getAsString();

            naviPointList.add(new NaviPoint(name, x, y, z, article));
        });
        return naviPointList;
    }

    public static List<String> getPlayerGroupList() {
        JsonArray response = APIRequest.sendPlayerGroupRequest();
        List<String> playerGroupList = new ArrayList<>();
        if (response != null) {
            response.forEach(jsonElement -> playerGroupList.add(jsonElement.getAsString()));
        }
        return playerGroupList;
    }

    public static Map<PlayerGroup, String> getPlayerGroupEntryMap() {
        Map<PlayerGroup, String> playerGroupEntryMap = new HashMap<>();
        getPlayerGroupList().forEach(s -> getPlayerGroupEntryList(s).forEach(playerGroup -> playerGroupEntryMap.put(playerGroup, s)));
        NotificationCenter.send(NotificationCenter.SYNCED_ADDON_GROUP_NOTIFICATION);
        return playerGroupEntryMap;
    }

    public static List<PlayerGroup> getPlayerGroupEntryList(String group) {
        JsonObject response = APIRequest.sendPlayerRequest();
        if (response == null || !response.has(group))
            return new ArrayList<>();
        List<PlayerGroup> playerGroupList = new ArrayList<>();
        response.get(group).getAsJsonArray().forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String name = o.get("name").getAsString();
            String uuid = o.get("uuid").getAsString();

            playerGroupList.add(new PlayerGroup(name, uuid));
        });
        return playerGroupList;
    }

    public static List<WantedReason> getWantedReasonEntryList() {
        JsonArray response = APIRequest.sendWantedReasonRequest();
        if (response == null)
            return new ArrayList<>();
        List<WantedReason> wantedReasonList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            String creatorUUID = o.get("creatorUUID").getAsString();
            String creatorName = o.get("creatorName").getAsString();
            int points = o.get("points").getAsInt();

            wantedReasonList.add(new WantedReason(reason, creatorUUID, creatorName, points));
        });
        return wantedReasonList;
    }
}