package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.api.json.NaviPointEntry;
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

    private static final Map<String,Faction> PLAYERFACTIONMAP = new HashMap<>();
    private static final Map<String,Integer> PLAYERRANKMAP = new HashMap<>();
    private static final List<String> HOUSEBANLIST = new ArrayList<>();
    private static final List<NaviPointEntry> NAVIPOINTLIST = new ArrayList<>();

    public static void syncAll() {
        syncPlayerFactionMap();
        syncPlayerRankMap();
        syncHouseBanList();
        syncNaviPointList();
    }

    public static Map<String, Faction> getPlayerFactionMap() {
        if (PLAYERFACTIONMAP.isEmpty()) syncPlayerFactionMap();
        return PLAYERFACTIONMAP;
    }

    public static Map<String, Integer> getPlayerRankMap() {
        if (PLAYERRANKMAP.isEmpty()) syncPlayerRankMap();
        return PLAYERRANKMAP;
    }

    public static List<String> getHouseBanList() {
        if (HOUSEBANLIST.isEmpty()) syncHouseBanList();
        return HOUSEBANLIST;
    }

    public static List<NaviPointEntry> getNaviPointList() {
        if (NAVIPOINTLIST.isEmpty()) syncNaviPointList();
        return NAVIPOINTLIST;
    }

    public static void syncPlayerFactionMap() {
        new Thread(() -> {
            for (Faction faction : Faction.values()) {
                List<String> nameList = ListUtils.getAllMatchesFromString(PatternHandler.NAME_PATTERN, faction.getWebsiteSource());
                nameList.forEach(name -> PLAYERFACTIONMAP.put(name.replace("<h4 class=\"h5 g-mb-5\"><strong>", ""), faction));
            }

            JsonObject response = APIRequest.sendPlayerRequest();
            if (response != null) {
                JsonArray jsonArray = response.getAsJsonArray("LEMILIEU");
                jsonArray.forEach(jsonElement -> PLAYERFACTIONMAP.put(jsonElement.getAsJsonObject().get("name").getAsString(), Faction.LeMilieu));
            }


            // TODO: 30.09.2022 remove souts überall
            JsonArray response = APIRequest.sendPlayerRequest();


            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Fraktionen aktualisiert.");
        }).start();
    }

    public static void syncPlayerRankMap() {
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
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Ränge aktualisiert.");
        }).start();
    }

    public static void syncHouseBanList() {
        new Thread(() -> {
            JsonArray response = APIRequest.sendHouseBanRequest(false);
            if (response == null) return;
            response.forEach(jsonElement -> HOUSEBANLIST.add(jsonElement.getAsJsonObject().get("name").getAsString()));
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Hausverbote aktualisiert.");
        }).start();
    }

    public static void syncNaviPointList() {
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
            LabyMod.getInstance().notifyMessageRaw(ColorCode.AQUA.getCode() + "Synchronisierung", "Navipunkte aktualisiert.");
        }).start();
    }
}