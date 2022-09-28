package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.api.json.NaviPointEntry;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Syncer {

    public static Map<String,Faction> PLAYERFACTIONMAP;
    public static Map<String,Integer> PLAYERRANKMAP;
    public static List<String> HOUSEBANLIST;
    public static List<NaviPointEntry> NAVIPOINTLIST;

    public static void syncAll() {
        syncPlayerFactionMap();
        syncPlayerRankMap();
        syncHouseBanList();
        syncNaviPointList();
    }

    public static void syncPlayerFactionMap() {
        PLAYERFACTIONMAP = new HashMap<>();
        new Thread(() -> {
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERFACTIONMAP.put(name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""), faction));
            }
        }).start();
    }

    public static void syncPlayerRankMap() {
        PLAYERRANKMAP = new HashMap<>();
        new Thread(() -> {
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                List<String> rankList = ListUtils.getAllMatchesFromString(PatternHandler.RANK_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERRANKMAP.put(
                        name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""),
                        Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                                .replace("<strong>Rang ", "")
                                .charAt(0)))));
            }
        }).start();
    }

    public static void syncHouseBanList() {
        HOUSEBANLIST = new ArrayList<>();
        new Thread(() -> {
            JsonArray response = APIRequest.sendHouseBanRequest(false);
            if (response == null) return;
            response.forEach(jsonElement -> HOUSEBANLIST.add(jsonElement.getAsJsonObject().get("name").getAsString()));
        }).start();
    }

    public static void syncNaviPointList() {
        NAVIPOINTLIST = new ArrayList<>();
        new Thread(() -> {
            JsonArray response = APIRequest.sendNaviPointRequest();
            if (response == null) return;
            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String name = o.get("name").getAsString();
                int x = o.get("x").getAsInt();
                int y = o.get("y").getAsInt();
                int z = o.get("z").getAsInt();
                NAVIPOINTLIST.add(new NaviPointEntry(name, x, y, z));
            });
        }).start();
    }
}