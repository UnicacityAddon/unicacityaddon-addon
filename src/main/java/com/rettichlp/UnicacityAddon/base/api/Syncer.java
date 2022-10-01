package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.entries.BlacklistReasonEntry;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Syncer {

    private static final Map<String,Faction> PLAYERFACTIONMAP = new HashMap<>();
    private static final Map<String,Integer> PLAYERRANKMAP = new HashMap<>();
    private static final List<BlacklistReasonEntry> BLACKLIST_REASON_ENTRY_LIST = new ArrayList<>();
    private static final List<HouseBanEntry> HOUSE_BAN_ENTRY_LIST = new ArrayList<>();
    private static final List<HouseBanReasonEntry> HOUSE_BAN_REASON_ENTRY_LIST = new ArrayList<>();
    private static final List<NaviPointEntry> NAVI_POINT_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> BLACKLIST_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> DEV_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> LEMILIEU_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> DYAVOL_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> VIP_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<PlayerGroupEntry> BETA_PLAYER_GROUP_ENTRY_LIST = new ArrayList<>();
    private static final List<WantedReasonEntry> WANTED_REASON_ENTRY_LIST = new ArrayList<>();

    public static void syncAll() {
        syncPlayerFactionMap();
        syncPlayerRankMap();

        syncBlacklistReasonEntryList();
        syncHouseBanEntryList();
        syncHouseBanReasonEntryList();
        syncNaviPointEntryList();
        syncPlayerGroupEntryList();
        syncWantedReasonEntryList();
    }

    public static List<String> getPlayerGroups() {
        return Arrays.asList("BLACKLIST", "DEV", "LEMILIEU", "DYAVOL", "VIP", "BETA");
    }

    public static Map<String, Faction> getPlayerFactionMap() {
        if (PLAYERFACTIONMAP.isEmpty()) syncPlayerFactionMap();
        return PLAYERFACTIONMAP;
    }

    public static Map<String, Integer> getPlayerRankMap() {
        if (PLAYERRANKMAP.isEmpty()) syncPlayerRankMap();
        return PLAYERRANKMAP;
    }

    public static List<BlacklistReasonEntry> getBlacklistReasonEntryList() {
        if (BLACKLIST_REASON_ENTRY_LIST.isEmpty()) syncBlacklistReasonEntryList();
        return BLACKLIST_REASON_ENTRY_LIST;
    }

    public static List<HouseBanEntry> getHouseBanEntryList() {
        if (HOUSE_BAN_ENTRY_LIST.isEmpty()) syncHouseBanEntryList();
        return HOUSE_BAN_ENTRY_LIST;
    }

    public static List<HouseBanReasonEntry> getHouseBanReasonEntryList() {
        if (HOUSE_BAN_REASON_ENTRY_LIST.isEmpty()) syncHouseBanReasonEntryList();
        return HOUSE_BAN_REASON_ENTRY_LIST;
    }

    public static List<NaviPointEntry> getNaviPointEntryList() {
        if (NAVI_POINT_ENTRY_LIST.isEmpty()) syncNaviPointEntryList();
        return NAVI_POINT_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getBlacklistPlayerGroupEntryList() {
        if (BLACKLIST_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return BLACKLIST_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getDevPlayerGroupEntryList() {
        if (DEV_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return DEV_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getLeMilieuPlayerGroupEntryList() {
        if (LEMILIEU_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return LEMILIEU_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getDyavolPlayerGroupEntryList() {
        if (DYAVOL_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return DYAVOL_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getVipPlayerGroupEntryList() {
        if (VIP_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return VIP_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<PlayerGroupEntry> getBetaPlayerGroupEntryList() {
        if (BETA_PLAYER_GROUP_ENTRY_LIST.isEmpty()) syncPlayerGroupEntryList();
        return BETA_PLAYER_GROUP_ENTRY_LIST;
    }

    public static List<WantedReasonEntry> getWantedReaonEntryList() {
        if (WANTED_REASON_ENTRY_LIST.isEmpty()) syncWantedReasonEntryList();
        return WANTED_REASON_ENTRY_LIST;
    }

    public static void syncPlayerFactionMap() {
        new Thread(() -> {
            PLAYERFACTIONMAP.clear();
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERFACTIONMAP.put(name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""), faction));
            }

            getDyavolPlayerGroupEntryList().forEach(playerGroupEntry -> PLAYERFACTIONMAP.put(playerGroupEntry.getName(), Faction.LEMILIEU));

            // TODO: 30.09.2022 remove souts überall
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Fraktionen aktualisiert.");
        }).start();
    }

    public static void syncPlayerRankMap() {
        new Thread(() -> {
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
        }).start();
    }

    public static void syncBlacklistReasonEntryList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendBlacklistReasonRequest();
            if (response == null) return;
            BLACKLIST_REASON_ENTRY_LIST.clear();
            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                int kills = o.get("kills").getAsInt();
                String reason = o.get("reason").getAsString();
                String issuerUUID = o.get("issuerUUID").getAsString();
                int price = o.get("price").getAsInt();
                String issuerName = o.get("issuerName").getAsString();

                BLACKLIST_REASON_ENTRY_LIST.add(new BlacklistReasonEntry(kills, reason, issuerUUID, price, issuerName));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Blacklist-Gründe aktualisiert.");
        }).start();
    }

    public static void syncHouseBanEntryList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendHouseBanRequest(AbstractionLayer.getPlayer().getFaction().equals(Faction.RETTUNGSDIENST));
            if (response == null) return;
            HOUSE_BAN_ENTRY_LIST.clear();
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
                    String issuerUUID = o.has("issuerUUID") ? o.get("issuerUUID").getAsString() : null;
                    String issuerName = o.has("issuerName") ? o.get("issuerName").getAsString() : null;
                    int days = o1.get("days").getAsInt();

                    houseBanReasonList.add(new HouseBanReasonEntry(reason, issuerUUID, issuerName, days));
                });

                HOUSE_BAN_ENTRY_LIST.add(new HouseBanEntry(duration, houseBanReasonList, expirationTime, name, startTime, uuid));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbote aktualisiert.");
        }).start();
    }

    public static void syncHouseBanReasonEntryList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendHouseBanReasonRequest();
            if (response == null) return;
            HOUSE_BAN_REASON_ENTRY_LIST.clear();
            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                String creatorUUID = o.has("creatorUUID") ? o.get("creatorUUID").getAsString() : null;
                String creatorName = o.has("creatorName") ? o.get("creatorName").getAsString() : null;
                int days = o.get("days").getAsInt();

                HOUSE_BAN_REASON_ENTRY_LIST.add(new HouseBanReasonEntry(reason, creatorUUID, creatorName, days));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbot-Gründe aktualisiert.");
        }).start();
    }

    public static void syncNaviPointEntryList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendNaviPointRequest();
            if (response == null) return;
            NAVI_POINT_ENTRY_LIST.clear();
            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                int x = o.get("x").getAsInt();
                int y = o.get("y").getAsInt();
                int z = o.get("z").getAsInt();

                NAVI_POINT_ENTRY_LIST.add(new NaviPointEntry(name, x, y, z));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Navipunkte aktualisiert.");
        }).start();
    }

    public static void syncPlayerGroupEntryList() {
        new Thread(() -> {
            JsonObject response = APIRequest.sendPlayerRequest();
            if (response == null) return;
            BLACKLIST_PLAYER_GROUP_ENTRY_LIST.clear();
            DEV_PLAYER_GROUP_ENTRY_LIST.clear();
            LEMILIEU_PLAYER_GROUP_ENTRY_LIST.clear();
            DYAVOL_PLAYER_GROUP_ENTRY_LIST.clear();
            VIP_PLAYER_GROUP_ENTRY_LIST.clear();
            BETA_PLAYER_GROUP_ENTRY_LIST.clear();
            response.get("BLACKLIST").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                BLACKLIST_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            response.get("DEV").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                DEV_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            response.get("LEMILIEU").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                LEMILIEU_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            response.get("DYAVOL").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                DYAVOL_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            response.get("VIP").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                VIP_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            response.get("BETA").getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                String uuid = o.get("uuid").getAsString();

                BETA_PLAYER_GROUP_ENTRY_LIST.add(new PlayerGroupEntry(name, uuid));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Gruppen aktualisiert.");
        }).start();
    }

    public static void syncWantedReasonEntryList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendWantedReasonRequest();
            if (response == null) return;
            WANTED_REASON_ENTRY_LIST.clear();
            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                String creatorUUID = o.get("creatorUUID").getAsString();
                String creatorName = o.get("creatorName").getAsString();
                int points = o.get("points").getAsInt();

                WANTED_REASON_ENTRY_LIST.add(new WantedReasonEntry(reason, creatorUUID, creatorName, points));
            });
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Wanted-Gründe aktualisiert.");
        }).start();
    }
}