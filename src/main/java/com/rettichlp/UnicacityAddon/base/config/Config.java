package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.ListContainerElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class Config {

    public static void createConfig(UnicacityAddon unicacityAddon, List<SettingsElement> list) {
        HeaderElement emptyLine = new HeaderElement("");

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

        list.add(new HeaderElement(Message.getBuilder().of("Nametags").color(ColorCode.WHITE).advance().create()));

        ListContainerElement nameTagSettings = new ListContainerElement("Nametags", new ControlElement.IconData(Material.NAME_TAG));
        nameTagSettings.setSubSettings(ConfigSettings.getNameTagSettings(unicacityAddon));
        list.add(nameTagSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Reinforcement & Sloc").color(ColorCode.WHITE).advance().create()));

        ListContainerElement reinforcementSettings = new ListContainerElement("Reinforcement", new ControlElement.IconData(Material.ARROW));
        reinforcementSettings.setSubSettings(ConfigSettings.getReinforcementSettings(unicacityAddon));
        list.add(reinforcementSettings);

        ListContainerElement slocSettings = new ListContainerElement("Sloc", new ControlElement.IconData(Material.COMPASS));
        slocSettings.setSubSettings(ConfigSettings.getSlocSettings(unicacityAddon));
        list.add(slocSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Nachrichten").color(ColorCode.WHITE).advance().create()));

        ListContainerElement messageSettings = new ListContainerElement("Nachrichten", new ControlElement.IconData(Material.BOOK_AND_QUILL));
        messageSettings.setSubSettings(ConfigSettings.getMessageSettings(unicacityAddon));
        list.add(messageSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Automatisierung").color(ColorCode.WHITE).advance().create()));

        ListContainerElement reportSettings = new ListContainerElement("Report Nachrichten", new ControlElement.IconData(Material.SIGN));
        reportSettings.setSubSettings(ConfigSettings.getReportSettings(unicacityAddon));
        list.add(reportSettings);

        BooleanElement atmSettings = new BooleanElement("ATM Info", unicacityAddon, new ControlElement.IconData(Material.PAPER), "ATM_SETTINGS", ConfigElements.getEventATM());
        atmSettings.setSubSettings(ConfigSettings.getATMSettings(unicacityAddon));
        list.add(atmSettings);

        BooleanElement carFindSettings = new BooleanElement("Route bei /car find", unicacityAddon, new ControlElement.IconData(Material.MINECART), "CAR_FIND_SETTINGS", ConfigElements.getEventCarFind());
        list.add(carFindSettings);

        BooleanElement passwordSettings = new BooleanElement("Automatisches Passwort", unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "PASSWORD_SETTINGS", ConfigElements.getPasswordAutomation());
        passwordSettings.setSubSettings(ConfigSettings.getPasswordSettings(unicacityAddon));
        list.add(passwordSettings);

        BooleanElement automateCommandsSettings = new BooleanElement("Join-Befehle", unicacityAddon, new ControlElement.IconData(Material.REDSTONE_COMPARATOR), "AUTOMATE_COMMANDS_SETTINGS", ConfigElements.getCommandAutomation());
        automateCommandsSettings.setSubSettings(ConfigSettings.getAutomateCommandsSettings(unicacityAddon));
        list.add(automateCommandsSettings);

        ListContainerElement eigenbedarfSettings = new ListContainerElement("Eigenbedarf", new ControlElement.IconData(Material.SUGAR));
        eigenbedarfSettings.setSubSettings(ConfigSettings.getEigenbedarfSettings(unicacityAddon));
        list.add(eigenbedarfSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Teamspeak").color(ColorCode.WHITE).advance().create()));

        ListContainerElement teamspeakSettings = new ListContainerElement("Teamspeak", new ControlElement.IconData(Material.NOTE_BLOCK));
        teamspeakSettings.setSubSettings(ConfigSettings.getTeamspeakSettings(unicacityAddon));
        list.add(teamspeakSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Equip").color(ColorCode.WHITE).advance().create()));

        ListContainerElement equipSettings = new ListContainerElement("Equip", new ControlElement.IconData(Material.ANVIL));
        equipSettings.setSubSettings(ConfigSettings.getEquipSettings(unicacityAddon));
        list.add(equipSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Sonstiges").color(ColorCode.WHITE).advance().create()));

        BooleanElement orderedTablistSettings = new BooleanElement("Sortierte Tablist", unicacityAddon, new ControlElement.IconData(Material.COMMAND), "ORDERED_TABLIST_SETTINGS", ConfigElements.getEventTabList());
        list.add(orderedTablistSettings);

        BooleanElement despawnTimeAfterKarma = new BooleanElement("Despawn Zeitpunkt", unicacityAddon, new ControlElement.IconData(Material.SKULL_ITEM), "ESTIMATED_DESPAWN_TIME_SETTINGS", ConfigElements.getEstimatedDespawnTime());
        list.add(despawnTimeAfterKarma);

        BooleanElement uploadAfterScreenCommand = new BooleanElement("Upload bei /screen", unicacityAddon, new ControlElement.IconData(Material.LEATHER), "AUTOMATED_SCREENSHOT_UPLOAD_SETTINGS", ConfigElements.getAutomatedScreenshotUpload());
        list.add(uploadAfterScreenCommand);

        BooleanElement removeResourcepackMessage = new BooleanElement("Remove Texturepack Nachricht", unicacityAddon, new ControlElement.IconData(Material.BARRIER), "REMOVE_RESOURCEPACK_MESSAGE", ConfigElements.getRemoveResourcePackMessage());
        list.add(removeResourcepackMessage);

        // Certain LabyMod themes cover the lower settings. To counteract this, empty lines are appended.
        list.add(emptyLine);
        list.add(emptyLine);
        list.add(emptyLine);
    }
}