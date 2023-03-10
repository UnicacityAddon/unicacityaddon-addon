package com.rettichlp.unicacityaddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
import com.rettichlp.unicacityaddon.base.models.Broadcast;
import com.rettichlp.unicacityaddon.base.models.HouseBan;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.models.ManagementUser;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.models.WantedReason;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ListUtils;
import net.labymod.main.LabyMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Syncer {

    public static final Map<String, Faction> PLAYERFACTIONMAP = new HashMap<>();
    public static final Map<String, Integer> PLAYERRANKMAP = new HashMap<>();
    public static List<BlacklistReason> BLACKLISTREASONLIST = new ArrayList<>();
    public static List<HouseBan> HOUSEBANLIST = new ArrayList<>();
    public static List<HouseBanReason> HOUSEBANREASONLIST = new ArrayList<>();
    public static List<ManagementUser> MANAGEMENTUSERLIST = new ArrayList<>();
    public static List<NaviPoint> NAVIPOINTLIST = new ArrayList<>();
    public static List<WantedReason> WANTEDREASONLIST = new ArrayList<>();

    public static void syncAll() {
        Thread t1 = syncPlayerAddonGroupMap();
        Thread t2 = syncPlayerFactionData();

        try {
            t1.start();
            t1.join();

            t2.start();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            if (!(HOUSEBANLIST = getHouseBanList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbote aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(HOUSEBANREASONLIST = getHouseBanReasonList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbot-Gründe aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(MANAGEMENTUSERLIST = getManagementUserList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Management-User aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(NAVIPOINTLIST = getNaviPointList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Navipunkte aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(BLACKLISTREASONLIST = getBlacklistReasonList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Blacklist-Gründe aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(WANTEDREASONLIST = getWantedReasonList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Wanted-Gründe aktualisiert.");
        }).start();
    }

    private static Thread syncPlayerAddonGroupMap() {
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
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Addon-Gruppen aktualisiert.");
        });
    }

    private static Thread syncPlayerFactionData() {
        return new Thread(() -> {
            PLAYERFACTIONMAP.clear();
            PLAYERRANKMAP.clear();
            for (Faction faction : Faction.values()) {
                String factionWebsiteSource = faction.getWebsiteSource();
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, factionWebsiteSource);
                List<String> rankList = ListUtils.getAllMatchesFromString(PatternHandler.RANK_PATTERN, factionWebsiteSource);
                nameList.forEach(name -> {
                    String formattedname = name.replace("<h4 class=\"h5 g-mb-5\"><strong>", "");
                    PLAYERFACTIONMAP.put(formattedname, faction);
                    PLAYERRANKMAP.put(formattedname, Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                            .replace("<strong>Rang ", "")
                            .charAt(0))));
                });
            }

            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Fraktionen aktualisiert.");
        });
    }

    private static List<BlacklistReason> getBlacklistReasonList() {
        JsonArray response = APIRequest.sendBlacklistReasonRequest();
        if (response == null)
            return new ArrayList<>();
        List<BlacklistReason> blacklistReasonList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            int kills = o.get("kills").getAsInt();
            int price = o.get("price").getAsInt();

            blacklistReasonList.add(new BlacklistReason(reason, kills, price));
        });
        return blacklistReasonList;
    }

    public static List<Broadcast> getBroadcastList() {
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

    private static List<HouseBan> getHouseBanList() {
        JsonArray response = APIRequest.sendHouseBanRequest(AbstractionLayer.getPlayer().getFaction().equals(Faction.RETTUNGSDIENST));
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

    private static List<HouseBanReason> getHouseBanReasonList() {
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

    private static List<ManagementUser> getManagementUserList() {
        JsonArray response = APIRequest.sendManagementUserRequest();
        if (response == null)
            return new ArrayList<>();
        List<ManagementUser> managementUserList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            boolean active = o.get("active").getAsBoolean();
            String uuid = o.get("uuid").getAsString();
            String version = o.get("version").getAsString();

            managementUserList.add(new ManagementUser(active, uuid, version));
        });
        return managementUserList;
    }

    private static List<NaviPoint> getNaviPointList() {
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

    private static List<WantedReason> getWantedReasonList() {
        JsonArray response = APIRequest.sendWantedReasonRequest();
        if (response == null)
            return new ArrayList<>();
        List<WantedReason> wantedReasonList = new ArrayList<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();

            String reason = o.get("reason").getAsString();
            int points = o.get("points").getAsInt();

            wantedReasonList.add(new WantedReason(reason, points));
        });
        return wantedReasonList;
    }
}