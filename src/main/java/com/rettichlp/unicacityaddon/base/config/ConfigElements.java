package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
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
            .of("%navipoint%").color(ColorCode.AQUA).advance().space()
            .of("(").color(ColorCode.GRAY).advance()
            .of("%distance%" + "m").color(ColorCode.AQUA).advance()
            .of(")").color(ColorCode.GRAY).advance()
            .create();

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
        return UnicacityAddon.ADDON.getConfig().has("REPORT_GREETING_SETTINGS")
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_GREETING_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    public static String getReportFarewell() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_FAREWELL_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_FAREWELL_SETTINGS").getAsString()
                : Strings.EMPTY;
    }

    public static String getReportPrefix() {
        return UnicacityAddon.ADDON.getConfig().has("REPORT_PREFIX_SETTINGS") && !UnicacityAddon.ADDON.getConfig().get("REPORT_PREFIX_SETTINGS").getAsString().isEmpty()
                ? UnicacityAddon.ADDON.getConfig().get("REPORT_PREFIX_SETTINGS").getAsString()
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

    // HQ Nachrichten
    public static boolean getServiceMessagesActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("SERVICE_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SERVICE_MESSAGE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // D-Bank Nachrichten
    public static boolean getDrugBankMessagesActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("DBANK_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("DBANK_MESSAGE_SETTINGS")
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
        return UnicacityAddon.ADDON.getConfig().has("TEAMSPEAK_NOTIFY_WAITING_SUPPORT_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("TEAMSPEAK_NOTIFY_WAITING_SUPPORT_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static boolean getTeamspeakNotifyWaitingPublic() {
        return UnicacityAddon.ADDON.getConfig().has("TEAMSPEAK_NOTIFY_WAITING_PUBLIC_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("TEAMSPEAK_NOTIFY_WAITING_PUBLIC_SETTINGS")
                .getAsBoolean(); // default = false
    }

    public static boolean getReinforcementScreenshot() {
        return !UnicacityAddon.ADDON.getConfig().has("REINFORCEMENT_SCREENSHOT_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("REINFORCEMENT_SCREENSHOT_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getAutomatedScreenshotUpload() {
        return !UnicacityAddon.ADDON.getConfig().has("AUTOMATED_SCREENSHOT_UPLOAD_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("AUTOMATED_SCREENSHOT_UPLOAD_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // DrugVaultMessages
    public static boolean getDrugVaultMessageActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("DRUG_VAULT_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("DRUG_VAULT_MESSAGE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // PlantBurnMessages
    public static boolean getPlantBurnMessageActivated() {
        return !UnicacityAddon.ADDON.getConfig().has("PLANT_BURN_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("PLANT_BURN_MESSAGE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // BombScreenshot
    public static boolean getAutomatedBombScreenshot() {
        return !UnicacityAddon.ADDON.getConfig().has("AUTOMATED_BOMB_SCREEN_SETTINGS") && UnicacityAddon.ADDON.getConfig().get("AUTOMATED_BOMB_SCREEN_SETTINGS")
                .getAsBoolean(); // default = false
    }

    // Revive Screenshot
    public static boolean getAutomatedReviveScreenshot() {
        return !UnicacityAddon.ADDON.getConfig().has("AUTOMATED_REVIVE_SCREEN_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("AUTOMATED_REVIVE_SCREEN_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // BetterWantedList
    public static boolean getBetterWantedList() {
        return !UnicacityAddon.ADDON.getConfig().has("BETTER_WANTED_LIST_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("BETTER_WANTED_LIST_SETTINGS")
                .getAsBoolean(); // default = true
    }

    // EquipSettings
    public static String getBaseballBatPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_BASEBALLBAT_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BASEBALLBAT_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BASEBALLBAT_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_BASEBALLBAT_SETTING").getAsString()
                : "80";
    }

    public static String getDonutPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_DONUT_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_DONUT_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_DONUT_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_DONUT_SETTING").getAsString()
                : "30";
    }

    public static String getLightKevlarPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_LKEV_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_LKEV_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_LKEV_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_LKEV_SETTING").getAsString()
                : "1450";
    }

    public static String getHeavyKevlarPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_SKEV_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SKEV_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SKEV_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_SKEV_SETTING").getAsString()
                : "2200";
    }

    public static String getPistolPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_PISTOLE_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PISTOLE_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PISTOLE_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_PISTOLE_SETTING").getAsString()
                : "350";
    }

    public static String getMP5Price() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_MP5_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_MP5_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_MP5_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_MP5_SETTING").getAsString()
                : "550";
    }

    public static String getBandagePrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_BANDAGE_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BANDAGE_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BANDAGE_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_BANDAGE_SETTING").getAsString()
                : "80";
    }

    public static String getPainKillerPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_PAINKILLER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PAINKILLER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PAINKILLER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_PAINKILLER_SETTING").getAsString()
                : "80";
    }

    public static String getWaterPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_WATER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_WATER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_WATER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_WATER_SETTING").getAsString()
                : "0";
    }

    public static String getSyringePrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_SYRINGE_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SYRINGE_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SYRINGE_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_SYRINGE_SETTING").getAsString()
                : "120";
    }

    public static String getBreadPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_BREAD_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BREAD_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_BREAD_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_BREAD_SETTING").getAsString()
                : "40";
    }

    public static String getPeppersprayPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_PEPPERSPRAY_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PEPPERSPRAY_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_PEPPERSPRAY_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_PEPPERSPRAY_SETTING").getAsString()
                : "400";
    }

    public static String getFireExtinguisherPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_FIREEXTINGUISHER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_FIREEXTINGUISHER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_FIREEXTINGUISHER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_FIREEXTINGUISHER_SETTING").getAsString()
                : "400";
    }

    public static String getAxePrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_AXE_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_AXE_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_AXE_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_AXE_SETTING").getAsString()
                : "50";
    }

    public static String getHelmetPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_HELMET_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_HELMET_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_HELMET_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_HELMET_SETTING").getAsString()
                : "250";
    }

    public static String getSwatShieldPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_SWATSHIELD_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SWATSHIELD_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SWATSHIELD_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_SWATSHIELD_SETTING").getAsString()
                : "0";
    }

    public static String getMaskPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_MASK_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_MASK_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_MASK_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_MASK_SETTING").getAsString()
                : "800";
    }

    public static String getFlashBangPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_FLASHBANG_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_FLASHBANG_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_FLASHBANG_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_FLASHBANG_SETTING").getAsString()
                : "250";
    }

    public static String getSmokeGrenadePrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_SMOKEGRENADE_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SMOKEGRENADE_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SMOKEGRENADE_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_SMOKEGRENADE_SETTING").getAsString()
                : "300";
    }

    public static String getTazerPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_TAZER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_TAZER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_TAZER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_TAZER_SETTING").getAsString()
                : "0";
    }

    public static String getHandCuffPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_HANDCUFF_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_HANDCUFF_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_HANDCUFF_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_HANDCUFF_SETTING").getAsString()
                : "0";
    }

    public static String getWingsuitPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_WINGSUIT_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_WINGSUIT_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_WINGSUIT_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_WINGSUIT_SETTING").getAsString()
                : "450";
    }

    public static String getSniperPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_SNIPER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SNIPER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_SNIPER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_SNIPER_SETTING").getAsString()
                : "2700";
    }

    public static String getDefuseKitPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_DEFUSEKIT_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_DEFUSEKIT_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_DEFUSEKIT_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_DEFUSEKIT_SETTING").getAsString()
                : "500";
    }

    public static String getTrackerPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_TRACKER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_TRACKER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_TRACKER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_TRACKER_SETTING").getAsString()
                : "600";
    }

    public static String getExplosiveBeltPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_EXPLOSIVEBELT_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_EXPLOSIVEBELT_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_EXPLOSIVEBELT_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_EXPLOSIVEBELT_SETTING").getAsString()
                : "4000";
    }

    public static String getRPG7Price() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_RPG7_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_RPG7_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_RPG7_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_RPG7_SETTING").getAsString()
                : "13000";
    }

    public static String getNotePadPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_NOTEPAD_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_NOTEPAD_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_NOTEPAD_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_NOTEPAD_SETTING").getAsString()
                : "4500";
    }

    public static String getGlassCutterPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_GLASSCUTTER_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_GLASSCUTTER_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_GLASSCUTTER_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_GLASSCUTTER_SETTING").getAsString()
                : "4500";
    }

    public static String getLockPickPrice() {
        return UnicacityAddon.ADDON.getConfig().has("EQUIP_LOCKPICK_SETTING") && !UnicacityAddon.ADDON.getConfig().get("EQUIP_LOCKPICK_SETTING").getAsString().isEmpty() && !UnicacityAddon.ADDON.getConfig().get("EQUIP_LOCKPICK_SETTING").getAsString().startsWith("-")
                ? UnicacityAddon.ADDON.getConfig().get("EQUIP_LOCKPICK_SETTING").getAsString()
                : "4500";
    }

    public static boolean getRemoveResourcePackMessage() {
        return !UnicacityAddon.ADDON.getConfig().has("REMOVE_RESOURCEPACK_MESSAGE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("REMOVE_RESOURCEPACK_MESSAGE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getAutomatedUpdate() {
        return !UnicacityAddon.ADDON.getConfig().has("AUTOMATED_UPDATE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("AUTOMATED_UPDATE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getBombSound() {
        return !UnicacityAddon.ADDON.getConfig().has("SOUND_BOMB_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SOUND_BOMB_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getContractSetSound() {
        return !UnicacityAddon.ADDON.getConfig().has("SOUND_CONTRACT_SET_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SOUND_CONTRACT_SET_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getContractFulfilledSound() {
        return !UnicacityAddon.ADDON.getConfig().has("SOUND_CONTRACT_FULFILLED_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SOUND_CONTRACT_FULFILLED_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getReportSound() {
        return !UnicacityAddon.ADDON.getConfig().has("SOUND_REPORT_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SOUND_REPORT_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getServiceSound() {
        return !UnicacityAddon.ADDON.getConfig().has("SOUND_SERVICE_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("SOUND_SERVICE_SETTINGS")
                .getAsBoolean(); // default = true
    }

    public static boolean getRenderAddonGroupTag() {
        return !UnicacityAddon.ADDON.getConfig().has("RENDER_ADDON_GROUP_TAG_SETTINGS") || UnicacityAddon.ADDON.getConfig().get("RENDER_ADDON_GROUP_TAG_SETTINGS")
                .getAsBoolean(); // default = true
    }
}