//package com.rettichlp.unicacityaddon.modules;
//
//import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
//import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
//import com.rettichlp.unicacityaddon.base.manager.FileManager;
//import com.rettichlp.unicacityaddon.base.registry.HudWidgetRegistry;
//import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
//import net.labymod.ingamegui.ModuleCategory;
//import net.labymod.ingamegui.moduletypes.SimpleModule;
//import net.labymod.settings.elements.ControlElement;
//import net.labymod.utils.Material;
//
///**
// * @author RettichLP
// */
//@UCModule
//public class PayDayHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static int currentTime;
//
//    @Override
//    public String getControlName() {
//        return "Zeit bis zum PayDay";
//    }
//
//    @Override
//    public String getSettingName() {
//        return null;
//    }
//
//    @Override
//    public String getDisplayName() {
//        return "PayDay";
//    }
//
//    @Override
//    public String getDisplayValue() {
//        return currentTime + "/60 Minuten";
//    }
//
//    @Override
//    public String getDefaultValue() {
//        return "0";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Zeigt die Minuten bis zum PayDay an.";
//    }
//
//    @Override
//    public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.WATCH);
//    }
//
//    @Override
//    public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override
//    public boolean isShown() {
//        return true;
//    }
//
//    @Override
//    public int getSortingId() {
//        return 0;
//    }
//
//    @Override
//    public void loadSettings() {
//    }
//
//    public static void addTime(int time) {
//        AddonPlayer p = UnicacityAddon.PLAYER;
//        currentTime = currentTime + time;
//        if (currentTime == 55) p.sendInfoMessage("Du hast in 5 Minuten deinen PayDay.");
//        if (currentTime == 57) p.sendInfoMessage("Du hast in 3 Minuten deinen PayDay.");
//        if (currentTime == 59) p.sendInfoMessage("Du hast in 1 Minute deinen PayDay.");
//        FileManager.saveData();
//    }
//
//    public static void setTime(int time) {
//        currentTime = time;
//        FileManager.saveData();
//    }
//}