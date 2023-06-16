package com.rettichlp.unicacityaddon.base.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.BlackMarketLocation;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
import com.rettichlp.unicacityaddon.base.models.Broadcast;
import com.rettichlp.unicacityaddon.base.models.HouseBan;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.models.ManagementUser;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.models.Revive;
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
    public static List<BlackMarketLocation> BLACKMARKETLOCATIONLIST = new ArrayList<>();
    public static List<HouseBan> HOUSEBANLIST = new ArrayList<>();
    public static List<HouseBanReason> HOUSEBANREASONLIST = new ArrayList<>();
    public static List<ManagementUser> MANAGEMENTUSERLIST = new ArrayList<>();
    public static List<NaviPoint> NAVIPOINTLIST = new ArrayList<>();
    public static List<WantedReason> WANTEDREASONLIST = new ArrayList<>();

    public static void syncAll() {
        new Thread(() -> {
            try {
                Thread t1 = syncPlayerAddonGroupMap();
                Thread t2 = syncPlayerFactionData();

                t1.start();
                t1.join();

                t2.start();
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

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
            if (!(BLACKMARKETLOCATIONLIST = getBlackMarketLocationList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Schwarzmarkt-Orte aktualisiert.");
        }).start();

        new Thread(() -> {
            if (!(WANTEDREASONLIST = getWantedReasonList()).isEmpty())
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Wanted-Gründe aktualisiert.");
        }).start();
    }

    private static Thread syncPlayerAddonGroupMap() {
        return new Thread(() -> {
            try {
                JsonObject response = APIRequest.sendPlayerRequest();
                for (AddonGroup addonGroup : AddonGroup.values()) {
                    addonGroup.getMemberList().clear();
                    for (JsonElement jsonElement : response.getAsJsonArray(addonGroup.getApiName())) {
                        addonGroup.getMemberList().add(jsonElement.getAsJsonObject().get("name").getAsString());
                    }
                }
                LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Addon-Gruppen aktualisiert.");
            } catch (APIResponseException e) {
                e.sendInfo();
            }
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

    public static List<BlacklistReason> getBlacklistReasonList() {
        List<BlacklistReason> blacklistReasonList = new ArrayList<>();
        try {
            APIRequest.sendBlacklistReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                int kills = o.get("kills").getAsInt();
                int price = o.get("price").getAsInt();

                blacklistReasonList.add(new BlacklistReason(reason, kills, price));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return blacklistReasonList;
    }

    public static List<BlackMarketLocation> getBlackMarketLocationList() {
        List<BlackMarketLocation> blackMarketLocationList = new ArrayList<>();
        try {
            APIRequest.sendBlackMarketLocationRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                int x = o.get("x").getAsInt();
                int y = o.get("y").getAsInt();
                int z = o.get("z").getAsInt();

                blackMarketLocationList.add(new BlackMarketLocation(name, x, y, z));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return blackMarketLocationList;
    }

    public static List<Broadcast> getBroadcastList() {
        List<Broadcast> broadcastList = new ArrayList<>();
        try {
            APIRequest.sendBroadcastQueueRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String broadcast = o.get("broadcast").getAsString();
                int id = o.get("id").getAsInt();
                String issuerName = o.get("issuerName").getAsString();
                String issuerUUID = o.get("issuerUUID").getAsString();
                long sendTime = o.get("sendTime").getAsLong();
                long time = o.get("time").getAsLong();

                broadcastList.add(new Broadcast(broadcast, id, issuerName, issuerUUID, sendTime, time));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return broadcastList;
    }

    public static List<HouseBan> getHouseBanList() {
        List<HouseBan> houseBanList = new ArrayList<>();
        try {
            APIRequest.sendHouseBanRequest(AbstractionLayer.getPlayer().getFaction().equals(Faction.RETTUNGSDIENST)).forEach(jsonElement -> {
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
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return houseBanList;
    }

    public static List<HouseBanReason> getHouseBanReasonList() {
        List<HouseBanReason> houseBanReasonList = new ArrayList<>();
        try {
            APIRequest.sendHouseBanReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                String creatorUUID = o.has("creatorUUID") ? o.get("creatorUUID").getAsString() : null;
                String creatorName = o.has("creatorName") ? o.get("creatorName").getAsString() : null;
                int days = o.get("days").getAsInt();

                houseBanReasonList.add(new HouseBanReason(reason, creatorUUID, creatorName, days));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return houseBanReasonList;
    }

    private static List<ManagementUser> getManagementUserList() {
        List<ManagementUser> managementUserList = new ArrayList<>();
        try {
            APIRequest.sendManagementUserRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                boolean active = o.get("active").getAsBoolean();
                String uuid = o.get("uuid").getAsString();
                String version = o.get("version").getAsString();

                managementUserList.add(new ManagementUser(active, uuid, version));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return managementUserList;
    }

    private static List<NaviPoint> getNaviPointList() {
        List<NaviPoint> naviPointList = new ArrayList<>();
        try {
            APIRequest.sendNaviPointRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                int x = o.get("x").getAsInt();
                int y = o.get("y").getAsInt();
                int z = o.get("z").getAsInt();
                String article = o.get("article").getAsString();

                naviPointList.add(new NaviPoint(name, x, y, z, article));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return naviPointList;
    }

    public static List<Revive> getReviveList() {
        List<Revive> reviveList = new ArrayList<>();
        try {
            APIRequest.sendReviveRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                int currentWeekReviveAmount = o.get("currentWeekReviveAmount").getAsInt();
                int lastWeekReviveAmount = o.get("lastWeekReviveAmount").getAsInt();
                String minecraftName = o.get("minecraftName").getAsString();
                String minecraftUuid = o.get("minecraftUuid").getAsString();

                reviveList.add(new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return reviveList;
    }

    public static List<Revive> getReviveRankList(int rank) {
        List<Revive> reviveList = new ArrayList<>();
        try {
            APIRequest.sendReviveRankRequest(rank).forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                int currentWeekReviveAmount = o.get("currentWeekReviveAmount").getAsInt();
                int lastWeekReviveAmount = o.get("lastWeekReviveAmount").getAsInt();
                String minecraftName = o.get("minecraftName").getAsString();
                String minecraftUuid = o.get("minecraftUuid").getAsString();

                reviveList.add(new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return reviveList;
    }

    public static Revive getRevivePlayer(String minecraftNameString) {
        try {
            JsonObject jsonObject = APIRequest.sendRevivePlayerRequest(minecraftNameString);

            int currentWeekReviveAmount = jsonObject.get("currentWeekReviveAmount").getAsInt();
            int lastWeekReviveAmount = jsonObject.get("lastWeekReviveAmount").getAsInt();
            String minecraftName = jsonObject.get("minecraftName").getAsString();
            String minecraftUuid = jsonObject.get("minecraftUuid").getAsString();

            return new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid);
        } catch (APIResponseException e) {
            e.sendInfo();
            return null;
        }
    }

    private static List<WantedReason> getWantedReasonList() {
        List<WantedReason> wantedReasonList = new ArrayList<>();
        try {
            APIRequest.sendWantedReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                int points = o.get("points").getAsInt();

                wantedReasonList.add(new WantedReason(reason, points));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return wantedReasonList;
    }
}