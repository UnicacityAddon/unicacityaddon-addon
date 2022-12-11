package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.ListContainerElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

import java.util.List;

public class Config {

    public static void createConfig(UnicacityAddon unicacityAddon, List<SettingsElement> list) {
        HeaderElement emptyLine = new HeaderElement("");

        list.add(new HeaderElement(Message.getBuilder()
                .of("U").color(ColorCode.RED).bold().advance()
                .of("nica").color(ColorCode.BLUE).bold().advance()
                .of("c").color(ColorCode.RED).bold().advance()
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

        ListContainerElement messageSettings = new ListContainerElement("Fraktions-Nachrichten", new ControlElement.IconData(Material.BOOK_AND_QUILL));
        messageSettings.setSubSettings(ConfigSettings.getMessageSettings(unicacityAddon));
        list.add(messageSettings);

        ListContainerElement reportSettings = new ListContainerElement("Report", new ControlElement.IconData(Material.SIGN));
        reportSettings.setSubSettings(ConfigSettings.getReportSettings(unicacityAddon));
        list.add(reportSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Join").color(ColorCode.WHITE).advance().create()));

        BooleanElement passwordSettings = new BooleanElement("Passwort", unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "PASSWORD_SETTINGS", ConfigElements.getPasswordAutomation());
        passwordSettings.setSubSettings(ConfigSettings.getPasswordSettings(unicacityAddon));
        list.add(passwordSettings);

        BooleanElement automateCommandsSettings = new BooleanElement("Befehle", unicacityAddon, new ControlElement.IconData(Material.REDSTONE_COMPARATOR), "AUTOMATE_COMMANDS_SETTINGS", ConfigElements.getCommandAutomation());
        automateCommandsSettings.setSubSettings(ConfigSettings.getAutomateCommandsSettings(unicacityAddon));
        list.add(automateCommandsSettings);

        BooleanElement removeResourcepackMessage = new BooleanElement("Texturepack Nachricht ausblenden", unicacityAddon, new ControlElement.IconData(Material.BARRIER), "REMOVE_RESOURCEPACK_MESSAGE_SETTINGS", ConfigElements.getRemoveResourcePackMessage());
        list.add(removeResourcepackMessage);

        list.add(new HeaderElement(Message.getBuilder().of("Automatisierung").color(ColorCode.WHITE).advance().create()));

        BooleanElement atmSettings = new BooleanElement("ATM Info", unicacityAddon, new ControlElement.IconData(Material.PAPER), "ATM_SETTINGS", ConfigElements.getEventATM());
        atmSettings.setSubSettings(ConfigSettings.getATMSettings(unicacityAddon));
        list.add(atmSettings);

        BooleanElement carFindSettings = new BooleanElement("Route bei /car find", unicacityAddon, new ControlElement.IconData(Material.MINECART), "CAR_FIND_SETTINGS", ConfigElements.getEventCarFind());
        list.add(carFindSettings);

        BooleanElement automatedScreenshotUploadSettings = new BooleanElement("Upload bei /screen", unicacityAddon, new ControlElement.IconData(Material.LEATHER), "AUTOMATED_SCREENSHOT_UPLOAD_SETTINGS", ConfigElements.getAutomatedScreenshotUpload());
        list.add(automatedScreenshotUploadSettings);

        BooleanElement automatedUpdateSettings = new BooleanElement("Automatisches Update", unicacityAddon, new ControlElement.IconData(Material.FIREWORK), "AUTOMATED_UPDATE_SETTINGS", ConfigElements.getAutomatedUpdate());
        list.add(automatedUpdateSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Teamspeak").color(ColorCode.WHITE).advance().create()));

        StringElement teamspeakAPIKey = new StringElement("API Key", unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "TEAMSPEAK_API_KEY", ConfigElements.getTeamspeakAPIKey());
        list.add(teamspeakAPIKey);

        BooleanElement teamspeakNotifyWaitingSupport = new BooleanElement("Support betreten Nachricht", unicacityAddon, new ControlElement.IconData(Material.NOTE_BLOCK), "TEAMSPEAK_NOTIFY_WAITING_SUPPORT_SETTINGS", ConfigElements.getTeamspeakNotifyWaitingSupport());
        list.add(teamspeakNotifyWaitingSupport);

        BooleanElement teamspeakNotifyWaitingPublic = new BooleanElement("Öffentlich betreten Nachricht", unicacityAddon, new ControlElement.IconData(Material.NOTE_BLOCK), "TEAMSPEAK_NOTIFY_WAITING_PUBLIC_SETTINGS", ConfigElements.getTeamspeakNotifyWaitingPublic());
        list.add(teamspeakNotifyWaitingPublic);

        list.add(new HeaderElement(Message.getBuilder().of("Equip").color(ColorCode.WHITE).advance().create()));

        ListContainerElement equipSettings = new ListContainerElement("Equip", new ControlElement.IconData(Material.ANVIL));
        equipSettings.setSubSettings(ConfigSettings.getEquipSettings(unicacityAddon));
        list.add(equipSettings);

        ListContainerElement eigenbedarfSettings = new ListContainerElement("Eigenbedarf", new ControlElement.IconData(Material.SUGAR));
        eigenbedarfSettings.setSubSettings(ConfigSettings.getEigenbedarfSettings(unicacityAddon));
        list.add(eigenbedarfSettings);

        list.add(new HeaderElement(Message.getBuilder().of("Sonstiges").color(ColorCode.WHITE).advance().create()));

        BooleanElement orderedTablistSettings = new BooleanElement("Sortierte Tablist", unicacityAddon, new ControlElement.IconData(Material.COMMAND), "ORDERED_TABLIST_SETTINGS", ConfigElements.getEventTabList());
        list.add(orderedTablistSettings);

        BooleanElement despawnTimeAfterKarma = new BooleanElement("Despawn Zeitpunkt", unicacityAddon, new ControlElement.IconData(Material.SKULL_ITEM), "ESTIMATED_DESPAWN_TIME_SETTINGS", ConfigElements.getEstimatedDespawnTime());
        list.add(despawnTimeAfterKarma);

        ListContainerElement soundSettings = new ListContainerElement("Sounds", new ControlElement.IconData(Material.NOTE_BLOCK));
        soundSettings.setSubSettings(ConfigSettings.getSoundSettings(unicacityAddon));
        list.add(soundSettings);

        // Certain LabyMod themes cover the lower settings. To counteract this, empty lines are appended.
        list.add(emptyLine);
        list.add(emptyLine);
        list.add(emptyLine);
    }
}