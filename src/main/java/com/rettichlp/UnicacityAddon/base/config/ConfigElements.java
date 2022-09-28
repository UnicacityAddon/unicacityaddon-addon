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
        return !UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTIONSUFFIX_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTIONSUFFIX_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // FACTION
    public static boolean getNameTagFaction() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTION_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTION_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagFactionColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTION_COLOR_SETTINGS") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTION_COLOR_SETTINGS").getAsString()) :
                ColorCode.BLUE; // default = BLUE
    }

    public static void setNameTagFactionColor(ColorCode factionColor) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_FACTION_COLOR_SETTINGS", factionColor.toString());
    }

    // ALLIANCE
    public static boolean getNameTagAlliance() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagAllianceColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_ALLIANCE_COLOR_SETTINGS") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_ALLIANCE_COLOR_SETTINGS").getAsString()) :
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
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE_COLOR_SETTINGS", allianceColor.toString());
    }

    public static void setNameTagAlliance1(Faction allianceFaction1) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE1", allianceFaction1.toString());
    }

    public static void setNameTagAlliance2(Faction allianceFaction2) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_ALLIANCE2", allianceFaction2.toString());
    }

    // STREETWAR
    public static boolean getNameTagStreetwar() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagStreetwarColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_STREETWAR_COLOR_SETTINGS") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_STREETWAR_COLOR_SETTINGS").getAsString()) :
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
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR_COLOR_SETTINGS", streetwarColor.toString());
    }

    public static void setNameTagStreetwar1(Faction streetwarFaction1) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR1", streetwarFaction1.toString());
    }

    public static void setNameTagStreetwar2(Faction streetwarFaction2) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_STREETWAR2", streetwarFaction2.toString());
    }

    // HOUSEBAN
    public static boolean getNameTagHouseBan() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_HOUSEBAN_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_HOUSEBAN_SETTINGS")
                .getAsBoolean(); // default = false
    }

    // DUTY
    public static boolean getNameTagDuty() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_DUTY_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_DUTY_SETTINGS")
                .getAsBoolean(); // default = false
    }

    // WPS/BLACKLIST/CONTRACT
    public static boolean getNameTagFactionSpecific() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTIONSPECIFIC_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTIONSPECIFIC_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static ColorCode getNameTagFactionSpecificColor() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_FACTIONSPECIFIC_COLOR_SETTINGS") ?
                ColorCode.valueOf(UnicacityAddon.ADDON.getConfig().get("NAMETAG_FACTIONSPECIFIC_COLOR_SETTINGS").getAsString()) :
                ColorCode.DARK_RED; // default = DARK_RED
    }

    public static void setNameTagFactionSpecificColor(ColorCode factionSpecificColor) {
        UnicacityAddon.ADDON.getConfig().addProperty("NAMETAG_FACTIONSPECIFIC_COLOR_SETTINGS", factionSpecificColor.toString());
    }

    // Delay
    public static String getRefreshDisplayNamesInterval() {
        return UnicacityAddon.ADDON.getConfig().has("NAMETAG_DELAY_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY_SETTINGS").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY_SETTINGS").getAsString().equals("0") && !UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY_SETTINGS").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("NAMETAG_DELAY_SETTINGS").getAsString()
                : "5";
    }

    // ATMINFO
    public static boolean getEventATM() {
        return !UnicacityAddon.ADDON.getConfig().has("ATM_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("ATM_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMFBank() {
        return UnicacityAddon.ADDON.getConfig().has("ATM_FBANK_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("ATM_FBANK_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMGRKasse() {
        return UnicacityAddon.ADDON.getConfig().has("ATM_GRBANK_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("ATM_GRBANK_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getEventATMInfo() {
        return !UnicacityAddon.ADDON.getConfig().has("ATM_INFO_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("ATM_INFO_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // TABLIST
    public static boolean getEventTabList() {
        return !UnicacityAddon.ADDON.getConfig().has("ORDERED_TABLIST_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("ORDERED_TABLIST_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // CARFIND
    public static boolean getEventCarFind() {
        return !UnicacityAddon.ADDON.getConfig().has("CAR_FIND_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("CAR_FIND_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // REINFORCEMENT
    public static String getPatternReinforcement() {
        return UnicacityAddon.ADDON.getConfig().has("REINFORCEMENT_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_SETTINGS").getAsString()
                : REINFORCEMENT_FALLBACK;
    }

    // REINFORCEMENT REPLY
    public static String getPatternReinforcementReply() {
        return UnicacityAddon.ADDON.getConfig().has("REINFORCEMENT_REPLY_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_REPLY_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_REPLY_SETTINGS").getAsString()
                : REINFORCEMENT_REPLY_FALLBACK;
    }

    // SLOC
    public static String getPatternSloc() {
        return UnicacityAddon.ADDON.getConfig().has("SLOC_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("SLOC_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("SLOC_SETTINGS").getAsString()
                : SLOC_FALLBACK;
    }

    // REPORT GREETING
    public static String getReportGreeting() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_GREETING_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REPORT_GREETING_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_GREETING_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    public static String getReportFarewell() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_FAREWELL_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    // PASSWORD SETTINGS
    public static boolean getPasswordAutomation() {
        return UnicacityAddon.ADDON.getConfig().has("PASSWORD_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("PASSWORD_SETTINGS")
                .getAsBoolean();
    }

    public static String getPassword() {
        return UnicacityAddon.ADDON.getConfig().has("PASSWORD_STRING_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("PASSWORD_STRING_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("PASSWORD_STRING_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    // COMMANDS ON JOIN
    public static boolean getCommandAutomation() {
        return UnicacityAddon.ADDON.getConfig().has("AUTOMATE_COMMANDS_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMANDS_SETTINGS")
                .getAsBoolean();
    }

    public static String getFirstCommand() {
        return UnicacityAddon.ADDON.getConfig().has("AUTOMATE_COMMAND_ONE_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_ONE_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_ONE_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    public static String getSecondCommand() {
        return UnicacityAddon.ADDON.getConfig().has("AUTOMATE_COMMAND_SECOND_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_SECOND_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_SECOND_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    public static String getThirdCommand() {
        return UnicacityAddon.ADDON.getConfig().has("AUTOMATE_COMMAND_THIRD_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_THIRD_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("AUTOMATE_COMMAND_THIRD_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    // HQ Nachrichten
    public static boolean getHQMessagesActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("HQ_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("HQ_MESSAGE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // Minus Karma
    public static boolean getEstimatedDespawnTime() {
        return !UnicacityAddon.ADDON.getConfig().has("ESTIMATED_DESPAWN_TIME_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("ESTIMATED_DESPAWN_TIME_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // EIGENBEDARF SETTINGS
    public static boolean getCocainActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_COCAINE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_COCAINE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getCocainDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_COCAINE_PURITY_SETTINGS") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_COCAINE_PURITY_SETTINGS").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setCocainDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("EIGENBEDARF_COCAINE_PURITY_SETTINGS", cocainPurity.toString());
    }

    public static String getCocaineAmount() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_COCAINE_AMOUNT_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_COCAINE_AMOUNT_SETTINGS").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_COCAINE_AMOUNT_SETTINGS").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_COCAINE_AMOUNT_SETTINGS").getAsString()
                : "15";
    }

    public static boolean getMarihuanaActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_MARIHUANA_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_MARIHUANA_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getMarihuanaDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_MARIHUANA_PURITY_SETTINGS") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_MARIHUANA_PURITY_SETTINGS").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setMarihuanaDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("EIGENBEDARF_MARIHUANA_PURITY_SETTINGS", cocainPurity.toString());
    }

    public static String getMarihuanaAmount() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_MARIHUANA_AMOUNT_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_MARIHUANA_AMOUNT_SETTINGS").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_MARIHUANA_AMOUNT_SETTINGS").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_MARIHUANA_AMOUNT_SETTINGS").getAsString()
                : "15";
    }

    public static boolean getMethActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_METH_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_METH_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static DrugPurity getMethDrugPurity() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_METH_PURITY_SETTINGS") ?
                DrugPurity.valueOf(UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_METH_PURITY_SETTINGS").getAsString()) :
                DrugPurity.BEST; // default = NULL
    }

    public static void setMethDrugPurity(DrugPurity cocainPurity) {
        UnicacityAddon.ADDON.getConfig().addProperty("EIGENBEDARF_METH_PURITY_SETTINGS", cocainPurity.toString());
    }

    public static String getMethAmount() {
        return UnicacityAddon.ADDON.getConfig().has("EIGENBEDARF_METH_AMOUNT_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_METH_AMOUNT_SETTINGS").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_METH_AMOUNT_SETTINGS").getAsString().equals("0")
                ? UnicacityAddon.ADDON.getConfig().get("EIGENBEDARF_METH_AMOUNT_SETTINGS").getAsString()
                : "15";
    }

    public static String getTeamspeakAPIKey() {
        return UnicacityAddon.ADDON.getConfig().has("TEAMSPEAK_API_KEY")
                ? UnicacityAddon.ADDON.getConfig().get("TEAMSPEAK_API_KEY").getAsString()
                : Strings.EMPTY;
    }

    public static void setTeamspeakAPIKey(String tsApiKey) {
        UnicacityAddon.ADDON.getConfig().addProperty("TEAMSPEAK_API_KEY", tsApiKey);
    }

    public static boolean getTeamspeakNotifyWaitingSupport() {
        return UnicacityAddon.ADDON.getConfig().has("TEAMSPEAK_NOTIFY_WAITING_SUPPORT") && UnicacityAddon.ADDON.getConfig().get("TEAMSPEAK_NOTIFY_WAITING_SUPPORT")
                .getAsBoolean(); // default = false
    }

    public static boolean getTeamspeakNotifyWaitingPublic() {
        return UnicacityAddon.ADDON.getConfig().has("TEAMSPEAK_NOTIFY_WAITING_PUBLIC") && UnicacityAddon.ADDON.getConfig().get("TEAMSPEAK_NOTIFY_WAITING_PUBLIC")
                .getAsBoolean(); // default = false
    }

    public static boolean automaticReinfscreen() {
        return !UnicacityAddon.ADDON.getConfig().has("REINFORCEMENT_SCREENSHOT_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_SCREENSHOT_SETTINGS")
                .getAsBoolean(); // default = true
    }
}