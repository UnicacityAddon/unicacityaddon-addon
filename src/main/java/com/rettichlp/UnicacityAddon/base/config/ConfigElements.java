package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;

/**
 * @author RettichLP
 */
public class ConfigElements {

    // FACTION
    public static boolean getNameTagFaction() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTION") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTION")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagFactionColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTION_COLOR") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTION_COLOR").getAsString()) :
                ColorCode.BLUE; // default = BLUE
    }

    public static void setNameTagFactionColor(ColorCode factionColor) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_FACTION_COLOR", factionColor.toString());
    }

    // FACTIONSUFFIX
    public static boolean getNameTagFactionSuffix() {
        return !UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTIONSUFFIX") || UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTIONSUFFIX")
                .getAsBoolean(); // default = true
    }

    // HOUSEBAN
    public static boolean getNameTagHouseban() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_HOUSEBAN") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_HOUSEBAN")
                .getAsBoolean(); // default = false
    }

    // ALLIANCE
    public static boolean getNameTagAlliance() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagAllianceColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE_COLOR") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE_COLOR").getAsString()) :
                ColorCode.DARK_PURPLE; // default = DARK_PURPLE
    }

    public static Faction getNameTagAlliance1() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE1") ?
                Faction.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE1").getAsString()) :
                Faction.NULL; // default = NULL
    }

    public static Faction getNameTagAlliance2() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE2") ?
                Faction.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE2").getAsString()) :
                Faction.NULL; // default = NULL
    }

    public static void setNameTagAllianceColor(ColorCode allianceColor) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE_COLOR", allianceColor.toString());
    }

    public static void setNameTagAlliance1(Faction allianceFaction1) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE1", allianceFaction1.toString());
    }

    public static void setNameTagAlliance2(Faction allianceFaction2) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE2", allianceFaction2.toString());
    }

    // STREETWAR
    public static boolean getNameTagStreetwar() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagStreetwarColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR_COLOR") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR_COLOR").getAsString()) :
                ColorCode.DARK_RED; // default = DARK_RED
    }

    public static Faction getNameTagStreetwar1() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR1") ?
                Faction.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR1").getAsString()) :
                Faction.NULL; // default = NULL
    }

    public static Faction getNameTagStreetwar2() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR2") ?
                Faction.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR2").getAsString()) :
                Faction.NULL; // default = NULL
    }

    public static void setNameTagStreetwarColor(ColorCode streetwarColor) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR_COLOR", streetwarColor.toString());
    }

    public static void setNameTagStreetwar1(Faction streetwarFaction1) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR1", streetwarFaction1.toString());
    }

    public static void setNameTagStreetwar2(Faction streetwarFaction2) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR2", streetwarFaction2.toString());
    }

    // ATMINFO
    public static boolean getEventATMInfo() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_ATMINFO") || UnicacityAddon.ADDON.getConfig().get("EVENT_ATMINFO")
                .getAsBoolean(); // default = true
    }

    // TABLIST
    public static boolean getEventTabList() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_TABLIST") || UnicacityAddon.ADDON.getConfig().get("EVENT_TABLIST")
                .getAsBoolean(); // default = true
    }
}