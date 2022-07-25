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
                .of("Einstellungen").color(ColorCode.WHITE).advance()
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

        BooleanElement nameTagForHouseBan = new BooleanElement("Hausverbot", unicacityAddon, new ControlElement.IconData(Material.MOB_SPAWNER), "NAMETAG_HOUSEBAN",
                ConfigElements.getNameTagHouseban());
        list.add(nameTagForHouseBan);

        list.add(new HeaderElement(Message.getBuilder()
                .of("").color(ColorCode.WHITE).advance()
                .create()));

        BooleanElement eventATMInfo = new BooleanElement("ATM Info", unicacityAddon, new ControlElement.IconData(Material.PAPER), "EVENT_ATMINFO",
                ConfigElements.getEventATMInfo());
        list.add(eventATMInfo);

        BooleanElement eventTabList = new BooleanElement("Sortierte Tablist", unicacityAddon, new ControlElement.IconData(Material.COMMAND), "EVENT_TABLIST",
                ConfigElements.getEventTabList());
        list.add(eventTabList);
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
}