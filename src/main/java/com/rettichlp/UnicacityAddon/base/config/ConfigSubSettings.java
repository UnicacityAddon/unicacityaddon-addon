package com.rettichlp.UnicacityAddon.base.config;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Equip;
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

    static Settings getBadFrakEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement basiSetting = new StringElement(Equip.BASI.getName(), unicacityAddon, new ControlElement.IconData(Material.BONE), "EQUIP_BASEBALLBAT_SETTING", ConfigElements.getBaseballBatPrice());
        settings.add(basiSetting);

        StringElement donutSettings = new StringElement(Equip.DONUT.getName(), unicacityAddon, new ControlElement.IconData(Material.COOKIE), "EQUIP_DONUT_SETTING", ConfigElements.getDonutPrice());
        settings.add(donutSettings);

        StringElement lightKevlarSettings = new StringElement(Equip.KEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_LKEV_SETTING", ConfigElements.getLightKevlarPrice());
        settings.add(lightKevlarSettings);

        StringElement heavyKevlarSettings = new StringElement(Equip.HEAVYKEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_SKEV_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(heavyKevlarSettings);

        StringElement pistolSettings = new StringElement(Equip.PISTOL.getName(), unicacityAddon, new ControlElement.IconData(Material.IRON_HOE), "EQUIP_PISTOLE_SETTING", ConfigElements.getPistolPrice());
        settings.add(pistolSettings);

        StringElement mpSettings = new StringElement(Equip.MP5.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_HOE), "EQUIP_MP5_SETTING", ConfigElements.getMP5Price());
        settings.add(mpSettings);

        return settings;
    }

    static Settings getPoliceEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement lightKevlarSettings = new StringElement(Equip.KEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_LKEV_SETTING", ConfigElements.getLightKevlarPrice());
        settings.add(lightKevlarSettings);

        StringElement pepperSpraySettings = new StringElement(Equip.PEPPERSPRAY.getName(), unicacityAddon, new ControlElement.IconData(Material.LEVER), "EQUIP_PEPPERSPRAY_SETTING", ConfigElements.getPeppersprayPrice());
        settings.add(pepperSpraySettings);

        StringElement mpSettings = new StringElement(Equip.MP5.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_HOE), "EQUIP_MP5_SETTING", ConfigElements.getMP5Price());
        settings.add(mpSettings);

        StringElement pistolSettings = new StringElement(Equip.PISTOL.getName(), unicacityAddon, new ControlElement.IconData(Material.IRON_HOE), "EQUIP_PISTOLE_SETTING", ConfigElements.getPistolPrice());
        settings.add(pistolSettings);

        StringElement tazerSettings = new StringElement(Equip.TAZER.getName(), unicacityAddon, new ControlElement.IconData(Material.WOOD_HOE), "EQUIP_TAZER_SETTING", ConfigElements.getTazerPrice());
        settings.add(tazerSettings);

        StringElement cuffSettings = new StringElement(Equip.CUFFS.getName(), unicacityAddon, new ControlElement.IconData(Material.LEASH), "EQUIP_HANDCUFF_SETTING", ConfigElements.getHandCuffPrice());
        settings.add(cuffSettings);

        StringElement donutSettings = new StringElement(Equip.DONUT.getName(), unicacityAddon, new ControlElement.IconData(Material.COOKIE), "EQUIP_DONUT_SETTING", ConfigElements.getDonutPrice());
        settings.add(donutSettings);

        return settings;
    }

    static Settings getSWATEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement heavyKevlarSettings = new StringElement(Equip.HEAVYKEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_SKEV_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(heavyKevlarSettings);

        StringElement swatShieldSettings = new StringElement(Equip.SWATSHIELD.getName(), unicacityAddon, new ControlElement.IconData(Material.WOOD), "EQUIP_SWATSHIELD_SETTING", ConfigElements.getSwatShieldPrice());
        settings.add(swatShieldSettings);

        StringElement flashBangSettings = new StringElement(Equip.FLASHBANG.getName(), unicacityAddon, new ControlElement.IconData(Material.SLIME_BALL), "EQUIP_FLASHBANG_SETTING", ConfigElements.getFlashBangPrice());
        settings.add(flashBangSettings);

        StringElement smokeSettings = new StringElement(Equip.SMOKEGRENADE.getName(), unicacityAddon, new ControlElement.IconData(Material.SNOW_BALL), "EQUIP_SMOKEGRENADE_SETTING", ConfigElements.getSmokeGrenadePrice());
        settings.add(smokeSettings);

        StringElement maskSettings = new StringElement(Equip.MASK.getName(), unicacityAddon, new ControlElement.IconData(Material.SNOW_BALL), "EQUIP_MASK_SETTING", ConfigElements.getMaskPrice());
        settings.add(maskSettings);

        StringElement elytraSettings = new StringElement(Equip.WINGSUIT.getName(), unicacityAddon, new ControlElement.IconData(Material.WOOL), "EQUIP_WINGSUIT_SETTING", ConfigElements.getWingsuitPrice());
        settings.add(elytraSettings);

        StringElement sniperSettings = new StringElement(Equip.SNIPER.getName(), unicacityAddon, new ControlElement.IconData(Material.STONE_HOE), "EQUIP_SNIPER_SETTING", ConfigElements.getSniperPrice());
        settings.add(sniperSettings);

        return settings;
    }

    static Settings getFBIEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement lightKevlarSettings = new StringElement(Equip.KEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_LKEV_SETTING", ConfigElements.getLightKevlarPrice());
        settings.add(lightKevlarSettings);

        StringElement pepperSpraySettings = new StringElement(Equip.PEPPERSPRAY.getName(), unicacityAddon, new ControlElement.IconData(Material.LEVER), "EQUIP_PEPPERSPRAY_SETTING", ConfigElements.getPeppersprayPrice());
        settings.add(pepperSpraySettings);

        StringElement mpSettings = new StringElement(Equip.MP5.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_HOE), "EQUIP_MP5_SETTING", ConfigElements.getMP5Price());
        settings.add(mpSettings);

        StringElement pistolSettings = new StringElement(Equip.PISTOL.getName(), unicacityAddon, new ControlElement.IconData(Material.IRON_HOE), "EQUIP_PISTOLE_SETTING", ConfigElements.getPistolPrice());
        settings.add(pistolSettings);

        StringElement tazerSettings = new StringElement(Equip.TAZER.getName(), unicacityAddon, new ControlElement.IconData(Material.WOOD_HOE), "EQUIP_TAZER_SETTING", ConfigElements.getTazerPrice());
        settings.add(tazerSettings);

        StringElement cuffSettings = new StringElement(Equip.CUFFS.getName(), unicacityAddon, new ControlElement.IconData(Material.LEASH), "EQUIP_HANDCUFF_SETTING", ConfigElements.getHandCuffPrice());
        settings.add(cuffSettings);

        StringElement donutSettings = new StringElement(Equip.DONUT.getName(), unicacityAddon, new ControlElement.IconData(Material.COOKIE), "EQUIP_DONUT_SETTING", ConfigElements.getDonutPrice());
        settings.add(donutSettings);

        return settings;
    }

    static Settings getHRTEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement heavyKevlarSettings = new StringElement(Equip.HEAVYKEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_SKEV_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(heavyKevlarSettings);

        StringElement flashBangSettings = new StringElement(Equip.FLASHBANG.getName(), unicacityAddon, new ControlElement.IconData(Material.SLIME_BALL), "EQUIP_FLASHBANG_SETTING", ConfigElements.getFlashBangPrice());
        settings.add(flashBangSettings);

        StringElement maskSettings = new StringElement(Equip.MASK.getName(), unicacityAddon, new ControlElement.IconData(Material.SNOW_BALL), "EQUIP_MASK_SETTING", ConfigElements.getMaskPrice());
        settings.add(maskSettings);

        StringElement elytraSettings = new StringElement(Equip.WINGSUIT.getName(), unicacityAddon, new ControlElement.IconData(Material.WOOL), "EQUIP_WINGSUIT_SETTING", ConfigElements.getWingsuitPrice());
        settings.add(elytraSettings);

        StringElement sniperSettings = new StringElement(Equip.SNIPER.getName(), unicacityAddon, new ControlElement.IconData(Material.STONE_HOE), "EQUIP_SNIPER_SETTING", ConfigElements.getSniperPrice());
        settings.add(sniperSettings);

        StringElement defuseKitSettings = new StringElement(Equip.DEFUSEKIT.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_INGOT), "EQUIP_DEFUSEKIT_SETTING", ConfigElements.getDefuseKitPrice());
        settings.add(defuseKitSettings);

        return settings;
    }

    static Settings getMedicEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement bandageSettings = new StringElement(Equip.BANDAGES.getName(), unicacityAddon, new ControlElement.IconData(Material.PAPER), "EQUIP_BANDAGE_SETTING", ConfigElements.getBandagePrice());
        settings.add(bandageSettings);

        StringElement painKillerSettings = new StringElement(Equip.PAINKILLER.getName(), unicacityAddon, new ControlElement.IconData(Material.BLAZE_ROD), "EQUIP_PAINKILLER_SETTING", ConfigElements.getBandagePrice());
        settings.add(painKillerSettings);

        StringElement pepperSpraySettings = new StringElement(Equip.PEPPERSPRAY.getName(), unicacityAddon, new ControlElement.IconData(Material.LEVER), "EQUIP_PEPPERSPRAY_SETTING", ConfigElements.getPeppersprayPrice());
        settings.add(pepperSpraySettings);

        StringElement syringeSettings = new StringElement(Equip.SYRINGE.getName(), unicacityAddon, new ControlElement.IconData(Material.IRON_FENCE), "EQUIP_SYRINGE_SETTING", ConfigElements.getSyringePrice());
        settings.add(syringeSettings);

        StringElement breadSettings = new StringElement(Equip.BREAD.getName(), unicacityAddon, new ControlElement.IconData(Material.BREAD), "EQUIP_BREAD_SETTING", ConfigElements.getBreadPrice());
        settings.add(breadSettings);

        StringElement waterSettings = new StringElement(Equip.WATER.getName(), unicacityAddon, new ControlElement.IconData(Material.BREAD), "EQUIP_WATER_SETTING", ConfigElements.getWaterPrice());
        settings.add(waterSettings);

        return settings;
    }

    static Settings getFireFighterSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement axeSettings = new StringElement(Equip.AXE.getName(), unicacityAddon, new ControlElement.IconData(Material.LEVER), "EQUIP_AXE_SETTING", ConfigElements.getAxePrice());
        settings.add(axeSettings);

        StringElement fireExtinguisherSettings = new StringElement(Equip.FIREEXTINGUISHER.getName(), unicacityAddon, new ControlElement.IconData(Material.LEVER), "EQUIP_FIREEXTINGUISHER_SETTING", ConfigElements.getFireExtinguisherPrice());
        settings.add(fireExtinguisherSettings);

        StringElement helmetSettings = new StringElement(Equip.HELMET.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_HELMET), "EQUIP_HELMET_SETTING", ConfigElements.getFireExtinguisherPrice());
        settings.add(helmetSettings);

        return settings;
    }

    static Settings getChurchEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement breadSettings = new StringElement(Equip.BREAD.getName(), unicacityAddon, new ControlElement.IconData(Material.BREAD), "EQUIP_BREAD_SETTING", ConfigElements.getBreadPrice());
        settings.add(breadSettings);

        StringElement waterSettings = new StringElement(Equip.WATER.getName(), unicacityAddon, new ControlElement.IconData(Material.BREAD), "EQUIP_WATER_SETTING", ConfigElements.getWaterPrice());
        settings.add(waterSettings);

        return settings;
    }

    static Settings getNewsEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement breadSettings = new StringElement(Equip.BREAD.getName(), unicacityAddon, new ControlElement.IconData(Material.BREAD), "EQUIP_BREAD_SETTING", ConfigElements.getBreadPrice());
        settings.add(breadSettings);

        StringElement notepadSettings = new StringElement(Equip.NOTEPAD.getName(), unicacityAddon, new ControlElement.IconData(Material.BOOK_AND_QUILL), "EQUIP_NOTEPAD_SETTING", ConfigElements.getNotePadPrice());
        settings.add(notepadSettings);

        return settings;
    }

    static Settings getHitmanEquipSettings(UnicacityAddon unicacityAddon) {
        Settings settings = new Settings();

        StringElement mpSettings = new StringElement(Equip.MP5.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_HOE), "EQUIP_MP5_SETTING", ConfigElements.getMP5Price());
        settings.add(mpSettings);

        StringElement pistolSettings = new StringElement(Equip.PISTOL.getName(), unicacityAddon, new ControlElement.IconData(Material.IRON_HOE), "EQUIP_PISTOLE_SETTING", ConfigElements.getPistolPrice());
        settings.add(pistolSettings);

        StringElement sniperSettings = new StringElement(Equip.SNIPER.getName(), unicacityAddon, new ControlElement.IconData(Material.STONE_HOE), "EQUIP_SNIPER_SETTING", ConfigElements.getSniperPrice());
        settings.add(sniperSettings);

        StringElement maskSettings = new StringElement(Equip.MASK.getName(), unicacityAddon, new ControlElement.IconData(Material.SNOW_BALL), "EQUIP_MASK_SETTING", ConfigElements.getMaskPrice());
        settings.add(maskSettings);

        StringElement lightKevlarSettings = new StringElement(Equip.KEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_LKEV_SETTING", ConfigElements.getLightKevlarPrice());
        settings.add(lightKevlarSettings);

        StringElement heavyKevlarSettings = new StringElement(Equip.HEAVYKEVLAR.getName(), unicacityAddon, new ControlElement.IconData(Material.LEATHER_CHESTPLATE), "EQUIP_SKEV_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(heavyKevlarSettings);

        StringElement lockpickSettings = new StringElement(Equip.LOCKPICK.getName(), unicacityAddon, new ControlElement.IconData(Material.GOLD_SWORD), "EQUIP_LOCKPICK_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(lockpickSettings);

        StringElement glassCutterSettings = new StringElement(Equip.GLASSCUTTER.getName(), unicacityAddon, new ControlElement.IconData(Material.TRIPWIRE_HOOK), "EQUIP_GLASSCUTTER_SETTING", ConfigElements.getHeavyKevlarPrice());
        settings.add(glassCutterSettings);

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

    static Settings getNameTagFactionSpecificSubSettings() {
        Settings settings = new Settings();

        DropDownMenu<ColorCode> nameTagFactionSpecificDropDownMenu = new DropDownMenu<ColorCode>("Farbe", 0, 0, 0, 0).fill(ColorCode.values());
        nameTagFactionSpecificDropDownMenu.setSelected(ConfigElements.getNameTagAllianceColor());
        nameTagFactionSpecificDropDownMenu.setEntryDrawer((object, x, y, string) ->  LabyMod.getInstance().getDrawUtils().drawString(ColorCode.valueOf(object.toString().toUpperCase()).toString(), x, y));
        DropDownElement<ColorCode> nameTagFactionSpecificDropDownElement = new DropDownElement<>("", nameTagFactionSpecificDropDownMenu);
        nameTagFactionSpecificDropDownElement.setChangeListener(ConfigElements::setNameTagFactionSpecificColor);
        settings.add(nameTagFactionSpecificDropDownElement);

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