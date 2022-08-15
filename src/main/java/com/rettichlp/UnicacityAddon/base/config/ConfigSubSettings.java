package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.badfaction.DrugPurity;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.settings.Settings;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.DropDownElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
public class ConfigSubSettings {

    static Settings getNameTagFactionSubSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> nameTagFactionDropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        nameTagFactionDropDownMenu0.setSelected(ConfigElements.getNameTagFactionColor());
        nameTagFactionDropDownMenu0.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> nameTagFactionDropDownElement0 = new DropDownElement<>("", nameTagFactionDropDownMenu0);
        nameTagFactionDropDownElement0.setChangeListener(ConfigElements::setNameTagFactionColor);
        settings.add(nameTagFactionDropDownElement0);

        return settings;
    }

    static Settings getNameTagAllianceSubSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> nameTagAllianceDropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        nameTagAllianceDropDownMenu0.setSelected(ConfigElements.getNameTagAllianceColor());
        nameTagAllianceDropDownMenu0.setEntryDrawer((object, x, y, string) ->  LabyMod.getInstance().getDrawUtils().drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> nameTagAllianceDropDownElement0 = new DropDownElement<>("", nameTagAllianceDropDownMenu0);
        nameTagAllianceDropDownElement0.setChangeListener(ConfigElements::setNameTagAllianceColor);
        settings.add(nameTagAllianceDropDownElement0);

        DropDownMenu<Faction> nameTagAllianceDropDownMenu1 = new DropDownMenu<Faction>("Bündnisfraktion 1", 0, 0, 0, 0).fill(Faction.values());
        nameTagAllianceDropDownMenu1.setSelected(ConfigElements.getNameTagAlliance1());
        nameTagAllianceDropDownMenu1.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> nameTagAllianceDropDownElement1 = new DropDownElement<>("", nameTagAllianceDropDownMenu1);
        nameTagAllianceDropDownElement1.setChangeListener(ConfigElements::setNameTagAlliance1);
        settings.add(nameTagAllianceDropDownElement1);

        DropDownMenu<Faction> nameTagAllianceDropDownMenu2 = new DropDownMenu<Faction>("Bündnisfraktion 2 (optional)", 0, 0, 0, 0).fill(Faction.values());
        nameTagAllianceDropDownMenu2.setSelected(ConfigElements.getNameTagAlliance2());
        nameTagAllianceDropDownMenu2.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> nameTagAllianceDropDownElement2 = new DropDownElement<>("", nameTagAllianceDropDownMenu2);
        nameTagAllianceDropDownElement2.setChangeListener(ConfigElements::setNameTagAlliance2);
        settings.add(nameTagAllianceDropDownElement2);

        return settings;
    }

    static Settings getNameTagStreetwarSubSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> nameTagStreetwarDropDownMenu0 = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        nameTagStreetwarDropDownMenu0.setSelected(ConfigElements.getNameTagStreetwarColor());
        nameTagStreetwarDropDownMenu0.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> nameTagStreetwarDropDownElement0 = new DropDownElement<>("", nameTagStreetwarDropDownMenu0);
        nameTagStreetwarDropDownElement0.setChangeListener(ConfigElements::setNameTagStreetwarColor);
        settings.add(nameTagStreetwarDropDownElement0);

        DropDownMenu<Faction> nameTagStreetwarDropDownMenu1 = new DropDownMenu<Faction>("Streetwarfraktion 1", 0, 0, 0, 0).fill(Faction.values());
        nameTagStreetwarDropDownMenu1.setSelected(ConfigElements.getNameTagStreetwar1());
        nameTagStreetwarDropDownMenu1.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> nameTagStreetwarDropDownElement1 = new DropDownElement<>("", nameTagStreetwarDropDownMenu1);
        nameTagStreetwarDropDownElement1.setChangeListener(ConfigElements::setNameTagStreetwar1);
        settings.add(nameTagStreetwarDropDownElement1);

        DropDownMenu<Faction> nameTagStreetwarDropDownMenu2 = new DropDownMenu<Faction>("Streetwarfraktion 2 (optional)", 0, 0, 0, 0).fill(Faction.values());
        nameTagStreetwarDropDownMenu2.setSelected(ConfigElements.getNameTagStreetwar2());
        nameTagStreetwarDropDownMenu2.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(Faction.valueOf(object.toString().toUpperCase()).getDisplayName(), x, y));
        DropDownElement<Faction> nameTagStreetwarDropDownElement2 = new DropDownElement<>("", nameTagStreetwarDropDownMenu2);
        nameTagStreetwarDropDownElement2.setChangeListener(ConfigElements::setNameTagStreetwar2);
        settings.add(nameTagStreetwarDropDownElement2);

        return settings;
    }

    static Settings getEigenbedarfCocaineSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement cocainAmount = new StringElement("Kokain Menge", unicacityAddon, new ControlElement.IconData(Material.SUGAR), "EIGENBEDARF_COCAINE_AMOUNT_SETTINGS", ConfigElements.getCocaineAmount());
        settings.add(cocainAmount);

        DropDownMenu<DrugPurity> cocainPurity = new DropDownMenu<DrugPurity>("Kokain Reinheit", 0, 0, 0, 0).fill(DrugPurity.values());
        cocainPurity.setSelected(ConfigElements.getCocainDrugPurity());
        cocainPurity.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(DrugPurity.valueOf(object.toString().toUpperCase()).getPurityString(), x, y));
        DropDownElement<DrugPurity> cocainPurityElement = new DropDownElement<>("", cocainPurity);
        cocainPurityElement.setChangeListener(ConfigElements::setCocainDrugPurity);

        settings.add(cocainPurityElement);

        return settings;
    }

    static Settings getEigenbedarfWeedSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement weedAmount = new StringElement("Marihuana Menge", unicacityAddon, new ControlElement.IconData(Material.WHEAT), "EIGENBEDARF_MARIHUANA_AMOUNT_SETTINGS", ConfigElements.getMarihuanaAmount());
        settings.add(weedAmount);

        DropDownMenu<DrugPurity> weedPurity = new DropDownMenu<DrugPurity>("Marihuana Reinheit", 0, 0, 0, 0).fill(DrugPurity.values());
        weedPurity.setSelected(ConfigElements.getMarihuanaDrugPurity());
        weedPurity.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(DrugPurity.valueOf(object.toString().toUpperCase()).getPurityString(), x, y));
        DropDownElement<DrugPurity> weedPurityElement = new DropDownElement<>("", weedPurity);
        weedPurityElement.setChangeListener(ConfigElements::setMarihuanaDrugPurity);

        settings.add(weedPurityElement);

        return settings;
    }

    static Settings getEigenbedarfMethSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement methAmount = new StringElement("Methamphetamin Menge", unicacityAddon, new ControlElement.IconData(Material.QUARTZ), "EIGENBEDARF_METH_AMOUNT_SETTINGS", ConfigElements.getMethAmount());
        settings.add(methAmount);

        DropDownMenu<DrugPurity> methPurity = new DropDownMenu<DrugPurity>("Methamphetamin Reinheit", 0, 0, 0, 0).fill(DrugPurity.values());
        methPurity.setSelected(ConfigElements.getMethDrugPurity());
        methPurity.setEntryDrawer((object, x, y, string) -> LabyMod.getInstance().getDrawUtils().drawString(DrugPurity.valueOf(object.toString().toUpperCase()).getPurityString(), x, y));
        DropDownElement<DrugPurity> methPurityEmelemt = new DropDownElement<>("", methPurity);
        methPurityEmelemt.setChangeListener(ConfigElements::setMethDrugPurity);

        settings.add(methPurityEmelemt);

        return settings;
    }
}