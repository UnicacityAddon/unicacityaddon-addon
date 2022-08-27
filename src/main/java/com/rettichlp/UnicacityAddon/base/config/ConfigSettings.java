package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import net.labymod.settings.Settings;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
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

        return settings;
    }

    static Settings getSlocSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement slocSettings = new StringElement("Position", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "SLOC_SETTINGS", ConfigElements.getPatternSloc());
        settings.add(slocSettings);

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