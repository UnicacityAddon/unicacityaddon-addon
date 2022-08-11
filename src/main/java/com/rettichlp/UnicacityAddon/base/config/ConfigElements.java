package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.badfaction.DrugPurity;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import joptsimple.internal.Strings;

/**
 * @author RettichLP
 */
public class ConfigElements {

    private static final String REINFORCEMENT_FALLBACK = Message.getBuilder()
            .of("%type%").color(ColorCode.RED).bold().advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%navipoint%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%distance%" + "m").color(ColorCode.DARK_AQUA).advance().create();
    private static final String REINFORCEMENT_REPLY_FALLBACK = Message.getBuilder()
            .of("➥").color(ColorCode.GRAY).advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("➡").color(ColorCode.GRAY).advance().space()
            .of("%target%").color(ColorCode.DARK_AQUA).advance().space()
            .of("- (").color(ColorCode.GRAY).advance()
            .of("%distance%" + "m").color(ColorCode.DARK_AQUA).advance()
            .of(")").color(ColorCode.GRAY).advance().create();
    private static final String SLOC_FALLBACK = Message.getBuilder()
            .of("Position!").color(ColorCode.RED).bold().advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%sender%").color(ColorCode.AQUA).advance().space()
            .of("-").color(ColorCode.GRAY).advance().space()
            .of("%x%").color(ColorCode.AQUA).advance().space()
            .of("|").color(ColorCode.GRAY).advance().space()
            .of("%y%").color(ColorCode.AQUA).advance().space()
            .of("|").color(ColorCode.GRAY).advance().space()
            .of("%z%").color(ColorCode.AQUA).advance().create();

    // FACTIONSUFFIX
    public static boolean getNameTagFactionSuffix() {
        return !UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTIONSUFFIX") || UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTIONSUFFIX")
                .getAsBoolean(); // default = true
    }

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

    // HOUSEBAN
    public static boolean getNameTagHouseBan() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_HOUSEBAN") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_HOUSEBAN")
                .getAsBoolean(); // default = false
    }

    // DUTY
    public static boolean getNameTagDuty() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_DUTY") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_DUTY")
                .getAsBoolean(); // default = false
    }

    // WPS
    public static boolean getNameTagWPS() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_WPS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_WPS")
                .getAsBoolean(); // default = false
    }

    // Blacklist
    public static boolean getNameTagBlacklist() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_BLACKLIST") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_BLACKLIST")
                .getAsBoolean(); // default = false
    }

    // Contract
    public static boolean getNameTagContract() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_CONTRACT") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_CONTRACT")
                .getAsBoolean(); // default = false
    }

    // Delay
    public static String getRefreshDisplayNamesInterval() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_DELAY") && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY").getAsString().equals("0") && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY").getAsString()
                : "5";
    }

    // ATMINFO
    public static boolean getEventATM() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_ATM") || UnicacityAddon.ADDON.getConfig().get("EVENT_ATM")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMFBank() {
        return UnicacityAddon.ADDON.getConfig().has("EVENT_ATM_FBANK") && UnicacityAddon.ADDON.getConfig().get("EVENT_ATM_FBANK")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMGRKasse() {
        return UnicacityAddon.ADDON.getConfig().has("EVENT_ATM_GRKASSE") && UnicacityAddon.ADDON.getConfig().get("EVENT_ATM_GRKASSE")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMInfo() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_ATM_INFO") || UnicacityAddon.ADDON.getConfig().get("EVENT_ATM_INFO")
                .getAsBoolean(); // default = true
    }

    // TABLIST
    public static boolean getEventTabList() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_TABLIST") || UnicacityAddon.ADDON.getConfig().get("EVENT_TABLIST")
                .getAsBoolean(); // default = true
    }

    // CARFIND
    public static boolean getEventCarFind() {
        return !UnicacityAddon.ADDON.getConfig().has("EVENT_CARFIND") || UnicacityAddon.ADDON.getConfig().get("EVENT_CARFIND")
                .getAsBoolean(); // default = true
    }

    // REINFORCEMENT
    public static String getPatternReinforcement() {
        return UnicacityAddon.ADDON.getConfig().has("PATTERN_REINFORCEMENT") && !UnicacityAddon.ADDON.getConfig().get("PATTERN_REINFORCEMENT").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("PATTERN_REINFORCEMENT").getAsString()
                : REINFORCEMENT_FALLBACK;
    }

    // REINFORCEMENT REPLY
    public static String getPatternReinforcementReply() {
        return UnicacityAddon.ADDON.getConfig().has("PATTERN_REINFORCEMENT_REPLY") && !UnicacityAddon.ADDON.getConfig().get("PATTERN_REINFORCEMENT_REPLY").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("PATTERN_REINFORCEMENT_REPLY").getAsString()
                : REINFORCEMENT_REPLY_FALLBACK;
    }

    // SLOC
    public static String getPatternSloc() {
        return UnicacityAddon.ADDON.getConfig().has("PATTERN_SLOC") && !UnicacityAddon.ADDON.getConfig().get("PATTERN_SLOC").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("PATTERN_SLOC").getAsString()
                : SLOC_FALLBACK;
    }

    // REPORT GREETING
    public static String getReportGreeting() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_GREETING") && !UnicacityAddon.ADDON.getConfig().get("REPORT_GREETING").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_GREETING").getAsString()
                : Strings.EMPTY;
    }

    public static String getReportFarewell() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_FAREWELL") && !UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL").getAsString()
                : Strings.EMPTY;
    }

    // PASSWORD SETTINGS
    public static boolean getPasswordAutomation() {
        return UnicacityAddon.ADDON.getConfig().has("AUTOMATED_PASSWORD") && UnicacityAddon.ADDON.getConfig().get("AUTOMATED_PASSWORD")
                .getAsBoolean();
    }

    public static String getPassword() {
        return UnicacityAddon.ADDON.getConfig().has("PASSWORD") && !UnicacityAddon.ADDON.getConfig().get("PASSWORD").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("PASSWORD").getAsString()
                : Strings.EMPTY;
    }

    // COMMANDS ON JOIN
    public static boolean getCommandAutomation() {
        return UnicacityAddon.ADDON.getConfig().has("COMMAND_ON_JOIN") && UnicacityAddon.ADDON.getConfig().get("COMMAND_ON_JOIN")
                .getAsBoolean();
    }

    public static String getFirstCommand() {
        return UnicacityAddon.ADDON.getConfig().has("FIRST_COMMAND") && !UnicacityAddon.ADDON.getConfig().get("FIRST_COMMAND").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("FIRST_COMMAND").getAsString()
                : Strings.EMPTY;
    }

    public static String getSecondCommand() {
        return UnicacityAddon.ADDON.getConfig().has("SECOND_COMMAND") && !UnicacityAddon.ADDON.getConfig().get("SECOND_COMMAND").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("SECOND_COMMAND").getAsString()
                : Strings.EMPTY;
    }

    public static String getThirdCommand() {
        return UnicacityAddon.ADDON.getConfig().has("THIRD_COMMAND") && !UnicacityAddon.ADDON.getConfig().get("THIRD_COMMAND").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("THIRD_COMMAND").getAsString()
                : Strings.EMPTY;
    }

    // HQ Nachrichten
    public static boolean getHQMessagesActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("HQ_MESSAGES") || UnicacityAddon.ADDON.getConfig().get("HQ_MESSAGES")
                .getAsBoolean(); // default = true
    }

    // EIGENBEDARF SETTINGS
    public static boolean getCocainActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("COCAIN_ACTIVATED") || UnicacityAddon.ADDON.getConfig().get("COCAIN_ACTIVATED")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getCocainDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("COCAIN_PURITY") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("COCAIN_PURITY").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setCocainDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("COCAIN_PURITY", cocainPurity.toString());
    }

    public static String getCocaineAmount() {
        return UnicacityAddon.ADDON.getConfig().has("COCAINE_AMOUNT") && !UnicacityAddon.ADDON.getConfig().get("COCAINE_AMOUNT").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("COCAINE_AMOUNT").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("COCAINE_AMOUNT").getAsString()
                : "15";
    }

    public static boolean getMarihuanaActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("MARIHUANA_ACTIVATED") || UnicacityAddon.ADDON.getConfig().get("MARIHUANA_ACTIVATED")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getMarihuanaDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("MARIHUANA_PURITY") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("MARIHUANA_PURITY").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setMarihuanaDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("MARIHUANA_PURITY", cocainPurity.toString());
    }

    public static String getMarihuanaAmount() {
        return UnicacityAddon.ADDON.getConfig().has("MARIHUANA_AMOUNT") && !UnicacityAddon.ADDON.getConfig().get("MARIHUANA_AMOUNT").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("MARIHUANA_AMOUNT").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("MARIHUANA_AMOUNT").getAsString()
                : "15";
    }

    public static boolean getMethActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("METH_ACTIVATED") || UnicacityAddon.ADDON.getConfig().get("METH_ACTIVATED")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getMethDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("METH_PURITY") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("METH_PURITY").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setMethDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("MMETH_PURITY", cocainPurity.toString());
    }

    public static String getMethAmount() {
        return UnicacityAddon.ADDON.getConfig().has("METH_AMOUNT") && !UnicacityAddon.ADDON.getConfig().get("METH_AMOUNT").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("METH_AMOUNT").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("METH_AMOUNT").getAsString()
                : "15";
    }
}