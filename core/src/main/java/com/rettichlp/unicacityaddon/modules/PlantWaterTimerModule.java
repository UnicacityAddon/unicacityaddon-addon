//package com.rettichlp.unicacityaddon.modules;
//
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
// * @author Dimiikou
// */
//@UCModule
//public class PlantWaterTimerHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static int currentCount = 0;
//    public static final int timeNeeded = 3000;
//    public static String timer = "";
//
//    @Override public String getControlName() {
//        return "Plantage Wässern";
//    }
//
//    @Override public String getSettingName() {
//        return null;
//    }
//
//    @Override public String getDisplayName() {
//        return "Wässern";
//    }
//
//    @Override public String getDisplayValue() {
//        return timer;
//    }
//
//    @Override public String getDefaultValue() {
//        return "00:00";
//    }
//
//    @Override public String getDescription() {
//        return "Zeigt einen Timer an, welcher die Zeit zum nächsten Wässern beschreibt.";
//    }
//
//    @Override public ControlElement.IconData getIconData() { return new ControlElement.IconData(Material.WATER_BUCKET); }
//
//    @Override public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override public boolean isShown() {
//        return PlantFertilizeTimerModule.plantRunning;
//    }
//
//    @Override public int getSortingId() {
//        return 0;
//    }
//
//    @Override public void loadSettings() {
//    }
//
//    public static void stopPlant() {
//        currentCount = 0;
//        timer = "";
//    }
//}
