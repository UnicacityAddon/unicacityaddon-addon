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
// * @author RettichLP
// */
//@UCModule
//public class EmergencyServiceHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static int currentCount = 0;
//
//    @Override public String getControlName() {
//        return "Service-Count";
//    }
//
//    @Override public String getSettingName() {
//        return null;
//    }
//
//    @Override public String getDisplayName() {
//        return "Notrufe";
//    }
//
//    @Override public String getDisplayValue() {
//        return String.valueOf(currentCount);
//    }
//
//    @Override public String getDefaultValue() {
//        return "0";
//    }
//
//    @Override public String getDescription() {
//        return "Zeigt an, wie viele Notrufe offen sind.";
//    }
//
//    @Override public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.REDSTONE_LAMP_ON);
//    }
//
//    @Override public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override public boolean isShown() {
//        return !String.valueOf(currentCount).isEmpty();
//    }
//
//    @Override public int getSortingId() {
//        return 0;
//    }
//
//    @Override public void loadSettings() {
//    }
//}