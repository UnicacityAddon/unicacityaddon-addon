package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import net.labymod.settings.Settings;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.ListContainerElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
public class ConfigSettings {

    static Settings getNameTagSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement nameTagDelaySettings = new StringElement("Update Intervall", unicacityAddon, new ControlElement.IconData(Material.WATCH), "NAMETAG_DELAY_SETTINGS", ConfigElements.getRefreshDisplayNamesInterval());
        settings.add(nameTagDelaySettings);

        BooleanElement nameTagFactionSuffixSettings = new BooleanElement("Fraktionsinfo", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_FACTIONSUFFIX_SETTINGS", ConfigElements.getNameTagFactionSuffix());
        settings.add(nameTagFactionSuffixSettings);

        BooleanElement nameTagFactionSettings = new BooleanElement("Fraktion", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_FACTION_SETTINGS", ConfigElements.getNameTagFaction());
        nameTagFactionSettings.setSubSettings(ConfigSubSettings.getNameTagFactionSubSettings());
        settings.add(nameTagFactionSettings);

        BooleanElement nameTagAllianceSettings = new BooleanElement("Bündnis", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_ALLIANCE_SETTINGS", ConfigElements.getNameTagAlliance());
        nameTagAllianceSettings.setSubSettings(ConfigSubSettings.getNameTagAllianceSubSettings());
        settings.add(nameTagAllianceSettings);

        BooleanElement nameTagStreetwarSettings = new BooleanElement("Streetwar", unicacityAddon, new ControlElement.IconData(Material.IRON_SWORD), "NAMETAG_STREETWAR_SETTINGS", ConfigElements.getNameTagStreetwar());
        nameTagStreetwarSettings.setSubSettings(ConfigSubSettings.getNameTagStreetwarSubSettings());
        settings.add(nameTagStreetwarSettings);

        BooleanElement nameTagHouseBanSettings = new BooleanElement("Hausverbot", unicacityAddon, new ControlElement.IconData(Material.MOB_SPAWNER), "NAMETAG_HOUSEBAN_SETTINGS", ConfigElements.getNameTagHouseBan());
        settings.add(nameTagHouseBanSettings);

        BooleanElement nameTagDutySettings = new BooleanElement("Duty", unicacityAddon, new ControlElement.IconData(Material.REDSTONE_TORCH_ON), "NAMETAG_DUTY_SETTINGS", ConfigElements.getNameTagDuty());
        settings.add(nameTagDutySettings);

        BooleanElement nameTagFactionSpecificSettings = new BooleanElement("WPS/Blacklist/Contract", unicacityAddon, new ControlElement.IconData(Material.IRON_FENCE), "NAMETAG_FACTIONSPECIFIC_SETTINGS", ConfigElements.getNameTagFactionSpecific());
        nameTagFactionSpecificSettings.setSubSettings(ConfigSubSettings.getNameTagFactionSpecificSubSettings());
        settings.add(nameTagFactionSpecificSettings);

        return settings;
    }

    static Settings getReinforcementSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement reinforcementSettings = new StringElement("Reinforcement", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "REINFORCEMENT_SETTINGS", ConfigElements.getPatternReinforcement());
        settings.add(reinforcementSettings);

        StringElement reinforcementReplySettings = new StringElement("Reinforcement Antwort", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "REINFORCEMENT_REPLY_SETTINGS", ConfigElements.getPatternReinforcementReply());
        settings.add(reinforcementReplySettings);

        BooleanElement automaticReinfscreen = new BooleanElement("Reinforcement Screenshot", unicacityAddon, new ControlElement.IconData(Material.LEATHER), "REINFORCEMENT_SCREENSHOT_SETTINGS", ConfigElements.getReinforcementScreenshot());
        settings.add(automaticReinfscreen);

        return settings;
    }

    static Settings getSlocSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement slocSettings = new StringElement("Position", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "SLOC_SETTINGS", ConfigElements.getPatternSloc());
        settings.add(slocSettings);

        return settings;
    }

    static Settings getTeamspeakSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement teamspeakAPIKey = new StringElement("API Key", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "TEAMSPEAK_API_KEY", ConfigElements.getTeamspeakAPIKey());
        settings.add(teamspeakAPIKey);

        BooleanElement teamspeakNotifyWaitingSupport = new BooleanElement("Support betreten Nachricht", unicacityAddon, new ControlElement.IconData(Material.NOTE_BLOCK), "TEAMSPEAK_NOTIFY_WAITING_SUPPORT_SETTINGS", ConfigElements.getTeamspeakNotifyWaitingSupport());
        settings.add(teamspeakNotifyWaitingSupport);

        BooleanElement teamspeakNotifyWaitingPublic = new BooleanElement("Öffentlich betreten Nachricht", unicacityAddon, new ControlElement.IconData(Material.NOTE_BLOCK), "TEAMSPEAK_NOTIFY_WAITING_PUBLIC_SETTINGS", ConfigElements.getTeamspeakNotifyWaitingPublic());
        settings.add(teamspeakNotifyWaitingPublic);

        return settings;
    }

    static Settings getEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        ListContainerElement badFrakEquip = new ListContainerElement("BadFrak-Equip", new ControlElement.IconData(Material.SUGAR));
        badFrakEquip.setSubSettings(ConfigSubSettings.getBadFrakEquipSettings(unicacityAddon));
        settings.add(badFrakEquip);

        ListContainerElement copFrakEquip = new ListContainerElement("Polizei-Equip", new ControlElement.IconData(Material.LEASH));
        copFrakEquip.setSubSettings(ConfigSubSettings.getPoliceEquipSettings(unicacityAddon));
        settings.add(copFrakEquip);

        ListContainerElement swatFrakEquip = new ListContainerElement("SWAT-Equip", new ControlElement.IconData(Material.LEATHER_CHESTPLATE));
        swatFrakEquip.setSubSettings(ConfigSubSettings.getSWATEquipSettings(unicacityAddon));
        settings.add(swatFrakEquip);

        ListContainerElement fbiFrakEquip = new ListContainerElement("FBI-Equip", new ControlElement.IconData(Material.DIAMOND));
        fbiFrakEquip.setSubSettings(ConfigSubSettings.getFBIEquipSettings(unicacityAddon));
        settings.add(fbiFrakEquip);

        ListContainerElement hrtFrakEquip = new ListContainerElement("HRT-Equip", new ControlElement.IconData(Material.DIAMOND_CHESTPLATE));
        hrtFrakEquip.setSubSettings(ConfigSubSettings.getHRTEquipSettings(unicacityAddon));
        settings.add(hrtFrakEquip);

        ListContainerElement medicFrakEquip = new ListContainerElement("Medic-Equip", new ControlElement.IconData(Material.PAPER));
        medicFrakEquip.setSubSettings(ConfigSubSettings.getMedicEquipSettings(unicacityAddon));
        settings.add(medicFrakEquip);

        ListContainerElement fireFightersFrakEquip = new ListContainerElement("Feuerwehr-Equip", new ControlElement.IconData(Material.LEVER));
        fireFightersFrakEquip.setSubSettings(ConfigSubSettings.getFireFighterSettings(unicacityAddon));
        settings.add(fireFightersFrakEquip);

        ListContainerElement churchEquip = new ListContainerElement("Kirchen-Equip", new ControlElement.IconData(Material.BOOK));
        churchEquip.setSubSettings(ConfigSubSettings.getChurchEquipSettings(unicacityAddon));
        settings.add(churchEquip);

        ListContainerElement newsEquip = new ListContainerElement("News-Equip", new ControlElement.IconData(Material.BOOK_AND_QUILL));
        newsEquip.setSubSettings(ConfigSubSettings.getNewsEquipSettings(unicacityAddon));
        settings.add(newsEquip);

        ListContainerElement hitmanEquip = new ListContainerElement("Hitman-Equip", new ControlElement.IconData(Material.GOLD_SWORD));
        hitmanEquip.setSubSettings(ConfigSubSettings.getHitmanEquipSettings(unicacityAddon));
        settings.add(hitmanEquip);

        return settings;
    }

    static Settings getReportSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement reportGreetingSettings = new StringElement("Report Begrüßung", unicacityAddon, new ControlElement.IconData(Material.PAPER), "REPORT_GREETING_SETTINGS", ConfigElements.getReportGreeting());
        settings.add(reportGreetingSettings);

        StringElement reportFarewellSettings = new StringElement("Report Verabschiedung", unicacityAddon, new ControlElement.IconData(Material.PAPER), "REPORT_FAREWELL_SETTINGS", ConfigElements.getReportFarewell());
        settings.add(reportFarewellSettings);

        return settings;
    }

    static Settings getATMSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        BooleanElement atmFBankSettings = new BooleanElement("Kontoauszug FBank", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "ATM_FBANK_SETTINGS", ConfigElements.getEventATMFBank());
        settings.add(atmFBankSettings);

        BooleanElement atmGRBankSettings = new BooleanElement("Kontoauszug GRKasse", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "ATM_GRBANK_SETTINGS", ConfigElements.getEventATMGRKasse());
        settings.add(atmGRBankSettings);

        BooleanElement atmInfoSettings = new BooleanElement("Geld im ATM", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "ATM_INFO_SETTINGS", ConfigElements.getEventATMInfo());
        settings.add(atmInfoSettings);

        return settings;
    }

    static Settings getPasswordSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement passwordSettings = new StringElement("Passwort", unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "PASSWORD_STRING_SETTINGS", ConfigElements.getPassword());
        settings.add(passwordSettings);

        return settings;
    }

    static Settings getAutomateCommandsSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement automateCommandOneSettings = new StringElement("Erster Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "AUTOMATE_COMMAND_ONE_SETTINGS", ConfigElements.getFirstCommand());
        settings.add(automateCommandOneSettings);

        StringElement automateCommandTwoSettings = new StringElement("Zweiter Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "AUTOMATE_COMMAND_SECOND_SETTINGS", ConfigElements.getSecondCommand());
        settings.add(automateCommandTwoSettings);

        StringElement automateCommandThreeSettings = new StringElement("Dritter Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "AUTOMATE_COMMAND_THIRD_SETTINGS", ConfigElements.getThirdCommand());
        settings.add(automateCommandThreeSettings);

        return settings;
    }

    static Settings getEigenbedarfSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        BooleanElement eigenbedarfCocaineSettings = new BooleanElement("Kokain", unicacityAddon, new ControlElement.IconData(Material.SUGAR), "EIGENBEDARF_COCAINE_SETTINGS", ConfigElements.getCocainActivated());
        eigenbedarfCocaineSettings.setSubSettings(ConfigSubSettings.getEigenbedarfCocaineSettings(unicacityAddon));
        settings.add(eigenbedarfCocaineSettings);

        BooleanElement eigenbedarfWeedSettings = new BooleanElement("Marihuana", unicacityAddon, new ControlElement.IconData(Material.WHEAT), "EIGENBEDARF_MARIHUANA_SETTINGS", ConfigElements.getMarihuanaActivated());
        eigenbedarfWeedSettings.setSubSettings(ConfigSubSettings.getEigenbedarfWeedSettings(unicacityAddon));
        settings.add(eigenbedarfWeedSettings);

        BooleanElement eigenbedarfMethSettings = new BooleanElement("Methamphetamin", unicacityAddon, new ControlElement.IconData(Material.QUARTZ), "EIGENBEDARF_METH_SETTINGS", ConfigElements.getMethActivated());
        eigenbedarfMethSettings.setSubSettings(ConfigSubSettings.getEigenbedarfMethSettings(unicacityAddon));
        settings.add(eigenbedarfMethSettings);

        return settings;
    }
}