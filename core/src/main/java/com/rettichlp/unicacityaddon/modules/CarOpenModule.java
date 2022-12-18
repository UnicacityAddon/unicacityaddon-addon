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
//public class CarOpenHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static String info = "";
//
//    @Override public String getControlName() {
//        return "Auto-Info";
//    }
//
//    @Override public String getSettingName() {
//        return null;
//    }
//
//    @Override public String getDisplayName() {
//        return "Auto";
//    }
//
//    @Override public String getDisplayValue() {
//        return info;
//    }
//
//    @Override public String getDefaultValue() {
//        return "";
//    }
//
//    @Override public String getDescription() {
//        return "Zeigt an, ob dein Auto offen oder abgeschlossen ist.";
//    }
//
//    @Override public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.MINECART);
//    }
//
//    @Override public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override public boolean isShown() {
//        return !info.isEmpty();
//    }
//
//    @Override public int getSortingId() {
//        return 0;
//    }
//
//    @Override public void loadSettings() {
//    }
//}