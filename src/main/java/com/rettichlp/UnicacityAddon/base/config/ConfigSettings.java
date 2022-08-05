package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.settings.Settings;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.DropDownElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

import java.util.List;

/**
 * @author RettichLP
 */
public class ConfigSettings {

    public static void createConfig(UnicacityAddon unicacityAddon, List<SettingsElement> list) {
        list.add(new HeaderElement(Message.getBuilder()
                .of("U").color(ColorCode.RED).bold().advance()
                .of("nica").color(ColorCode.BLUE).bold().advance()
                .of("C").color(ColorCode.RED).bold().advance()
                .of("ity").color(ColorCode.BLUE).bold().advance()
                .of("A").color(ColorCode.RED).bold().advance()
                .of("ddon").color(ColorCode.BLUE).bold().advance()
                .space()
                .of("v" + UnicacityAddon.VERSION).color(ColorCode.BLUE).bold().advance()
                .space()
                .of("-").color(ColorCode.GRAY).bold().advance()
                .space()
                .of("by RettichLP and Dimiikou").color(ColorCode.GOLD).advance()
                .create()));

        list.add(new HeaderElement(Message.getBuilder()
                .of("Nametag").color(ColorCode.WHITE).advance()
                .create()));

        BooleanElement nameTagFactionSuffix = new BooleanElement("Fraktionsinfo", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_FACTIONSUFFIX",
                ConfigElements.getNameTagFactionSuffix());
        list.add(nameTagFactionSuffix);

        BooleanElement nameTagFaction = new BooleanElement("Fraktion", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_FACTION",
                ConfigElements.getNameTagFaction());
        nameTagFaction.setSubSettings(nameTagFactionSettings());
        list.add(nameTagFaction);

        BooleanElement nameTagAlliance = new BooleanElement("Bündnis", unicacityAddon, new ControlElement.IconData(Material.NAME_TAG), "NAMETAG_ALLIANCE",
                ConfigElements.getNameTagAlliance());
        nameTagAlliance.setSubSettings(nameTagAllianceSettings());
        list.add(nameTagAlliance);

        BooleanElement nameTagStreetwar = new BooleanElement("Streetwar", unicacityAddon, new ControlElement.IconData(Material.IRON_SWORD), "NAMETAG_STREETWAR",
                ConfigElements.getNameTagStreetwar());
        nameTagStreetwar.setSubSettings(nameTagStreetwarSettings());
        list.add(nameTagStreetwar);

        BooleanElement nameTagHouseBan = new BooleanElement("Hausverbot", unicacityAddon, new ControlElement.IconData(Material.MOB_SPAWNER), "NAMETAG_HOUSEBAN",
                ConfigElements.getNameTagHouseban());
        list.add(nameTagHouseBan);

        BooleanElement nameTagDuty = new BooleanElement("Duty", unicacityAddon, new ControlElement.IconData(Material.REDSTONE_TORCH_ON), "NAMETAG_DUTY",
                ConfigElements.getNameTagDuty());
        list.add(nameTagDuty);

        BooleanElement nameTagWPS = new BooleanElement("WPS", unicacityAddon, new ControlElement.IconData(Material.IRON_FENCE), "NAMETAG_WPS",
                ConfigElements.getNameTagWPS());
        list.add(nameTagWPS);

        BooleanElement nameTagBlacklist = new BooleanElement("Blacklist", unicacityAddon, new ControlElement.IconData(Material.IRON_SWORD), "NAMETAG_BLACKLIST",
                ConfigElements.getNameTagBlacklist());
        list.add(nameTagBlacklist);

        BooleanElement nameTagContract = new BooleanElement("Contract", unicacityAddon, new ControlElement.IconData(Material.IRON_SWORD), "NAMETAG_CONTRACT",
                ConfigElements.getNameTagContract());
        list.add(nameTagContract);

        list.add(new HeaderElement(Message.getBuilder()
                .of("Text").color(ColorCode.WHITE).advance()
                .create()));

        StringElement patternReinforcement = new StringElement("Reinforcement", unicacityAddon, new ControlElement.IconData(Material.PAPER), "PATTERN_REINFORCEMENT",
                ConfigElements.getPatternReinforcement());
        list.add(patternReinforcement);

        StringElement patternReinforcementReply = new StringElement("Reinforcement Antwort", unicacityAddon, new ControlElement.IconData(Material.PAPER), "PATTERN_REINFORCEMENT_REPLY",
                ConfigElements.getPatternReinforcementReply());
        list.add(patternReinforcementReply);

        StringElement patternSLoc = new StringElement("Position", unicacityAddon, new ControlElement.IconData(Material.PAPER), "PATTERN_SLOC",
                ConfigElements.getPatternSloc());
        list.add(patternSLoc);

        StringElement reportGreeting = new StringElement("Report Begrüßung", unicacityAddon, new ControlElement.IconData(Material.PAPER), "REPORT_GREETING",
                ConfigElements.getReportGreeting());
        list.add(reportGreeting);

        StringElement reportFarewell = new StringElement("Report Verabschiedung", unicacityAddon, new ControlElement.IconData(Material.PAPER), "REPORT_FAREWELL",
                ConfigElements.getReportFarewell());
        list.add(reportFarewell);

        list.add(new HeaderElement(Message.getBuilder()
                .of("Sonstiges").color(ColorCode.WHITE).advance()
                .create()));

        BooleanElement eventATM = new BooleanElement("ATM Info", unicacityAddon, new ControlElement.IconData(Material.PAPER), "EVENT_ATM",
                ConfigElements.getEventATM());
        eventATM.setSubSettings(eventATMSettings(unicacityAddon));
        list.add(eventATM);

        BooleanElement eventTabList = new BooleanElement("Sortierte Tablist", unicacityAddon, new ControlElement.IconData(Material.COMMAND), "EVENT_TABLIST",
                ConfigElements.getEventTabList());
        list.add(eventTabList);

        BooleanElement eventCarFind = new BooleanElement("Route bei /car find", unicacityAddon, new ControlElement.IconData(Material.MINECART), "EVENT_CARFIND",
                ConfigElements.getEventCarFind());
        list.add(eventCarFind);

        BooleanElement activatePassword = new BooleanElement("Automatisches /passwort", unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "AUTOMATED_PASSWORD",
                ConfigElements.getPasswordAutomation());
        activatePassword.setSubSettings(passwordSettings(unicacityAddon));
        list.add(activatePassword);

        BooleanElement commandsOnJoin = new BooleanElement("Automatisches Befehle", unicacityAddon, new ControlElement.IconData(Material.REDSTONE_COMPARATOR), "COMMAND_ON_JOIN",
                ConfigElements.getCommandAutomation());
        commandsOnJoin.setSubSettings(commandList(unicacityAddon));
        list.add(commandsOnJoin);

        // Certain LabyMod themes cover the lower settings. To counteract this, empty lines are appended.
        HeaderElement emptyLine = new HeaderElement("");
        list.add(emptyLine);
        list.add(emptyLine);
        list.add(emptyLine);
    }

    private static Settings nameTagAllianceSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> dropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        dropDownMenu0.setSelected(ConfigElements.getNameTagAllianceColor());
        dropDownMenu0.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> dropDownElement0 = new DropDownElement<>("", dropDownMenu0);
        dropDownElement0.setChangeListener(ConfigElements::setNameTagAllianceColor);

        DropDownMenu<Faction> dropDownMenu1 = new DropDownMenu<Faction>("Bündnisfraktion 1", 0, 0, 0, 0).fill(Faction.values());
        dropDownMenu1.setSelected(ConfigElements.getNameTagAlliance1());
        dropDownMenu1.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> dropDownElement1 = new DropDownElement<>("", dropDownMenu1);
        dropDownElement1.setChangeListener(ConfigElements::setNameTagAlliance1);

        DropDownMenu<Faction> dropDownMenu2 = new DropDownMenu<Faction>("Bündnisfraktion 2 (optional)", 0, 0, 0, 0).fill(Faction.values());
        dropDownMenu2.setSelected(ConfigElements.getNameTagAlliance2());
        dropDownMenu2.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> dropDownElement2 = new DropDownElement<>("", dropDownMenu2);
        dropDownElement2.setChangeListener(ConfigElements::setNameTagAlliance2);

        settings.add(dropDownElement0);
        settings.add(dropDownElement1);
        settings.add(dropDownElement2);
        return settings;
    }

    private static Settings nameTagFactionSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> dropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        dropDownMenu0.setSelected(ConfigElements.getNameTagFactionColor());
        dropDownMenu0.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> dropDownElement0 = new DropDownElement<>("", dropDownMenu0);
        dropDownElement0.setChangeListener(ConfigElements::setNameTagFactionColor);

        settings.add(dropDownElement0);
        return settings;
    }

    private static Settings nameTagStreetwarSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> dropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        dropDownMenu0.setSelected(ConfigElements.getNameTagStreetwarColor());
        dropDownMenu0.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> dropDownElement0 = new DropDownElement<>("", dropDownMenu0);
        dropDownElement0.setChangeListener(ConfigElements::setNameTagStreetwarColor);

        DropDownMenu<Faction> dropDownMenu1 = new DropDownMenu<Faction>("Streetwarfraktion 1", 0, 0, 0, 0).fill(Faction.values());
        dropDownMenu1.setSelected(ConfigElements.getNameTagStreetwar1());
        dropDownMenu1.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> dropDownElement1 = new DropDownElement<>("", dropDownMenu1);
        dropDownElement1.setChangeListener(ConfigElements::setNameTagStreetwar1);

        DropDownMenu<Faction> dropDownMenu2 = new DropDownMenu<Faction>("Streetwarfraktion 2 (optional)", 0, 0, 0, 0).fill(Faction.values());
        dropDownMenu2.setSelected(ConfigElements.getNameTagStreetwar2());
        dropDownMenu2.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils()
                .drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> dropDownElement2 = new DropDownElement<>("", dropDownMenu2);
        dropDownElement2.setChangeListener(ConfigElements::setNameTagStreetwar2);

        settings.add(dropDownElement0);
        settings.add(dropDownElement1);
        settings.add(dropDownElement2);
        return settings;
    }

    private static Settings eventATMSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        BooleanElement eventATMFBank = new BooleanElement("Kontoauszug FBank", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "EVENT_ATM_FBANK",
                ConfigElements.getEventATMFBank());

        BooleanElement eventATMGRKasse = new BooleanElement("Kontoauszug GRKasse", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "EVENT_ATM_GRKASSE",
                ConfigElements.getEventATMGRKasse());

        BooleanElement eventATMInfo = new BooleanElement("Geld im ATM", unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "EVENT_ATM_INFO",
                ConfigElements.getEventATMInfo());

        settings.add(eventATMFBank);
        settings.add(eventATMGRKasse);
        settings.add(eventATMInfo);
        return settings;
    }

    private static Settings passwordSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement password = new StringElement("Passwort", unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "PASSWORD",
                ConfigElements.getPassword());
        settings.add(password);

        return settings;
    }

    private static Settings commandList(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement commandOne = new StringElement("Erster Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "FIRST_COMMAND",
                ConfigElements.getFirstCommand());
        settings.add(commandOne);

        StringElement commandTwo = new StringElement("Zweiter Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "SECOND_COMMAND",
                ConfigElements.getSecondCommand());
        settings.add(commandTwo);

        StringElement commandThree = new StringElement("Dritter Command", unicacityAddon, new ControlElement.IconData(Material.PAPER), "THIRD_COMMAND",
                ConfigElements.getThirdCommand());
        settings.add(commandThree);

        return settings;
    }
}