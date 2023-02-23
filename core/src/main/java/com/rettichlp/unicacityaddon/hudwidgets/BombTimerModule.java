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
//public class BombTimerHudWidget extends TextHudWidget<TextHudWidgetConfig> implements ModuleUpdateListener {
//
//    public static int timer = 0;
//
//    @Override
//    public String getControlName() {
//        return "Bomben-Timer";
//    }
//
//    @Override
//    public String getSettingName() {
//        return null;
//    }
//
//    @Override
//    public String getDisplayName() {
//        return "Bombe";
//    }
//
//    @Override
//    public String getDisplayValue() {
//        return String.valueOf(timer);
//    }
//
//    @Override
//    public String getDefaultValue() {
//        return "00:00";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Zeigt die Zeit an, die seit dem Legen einer Bombe vergangen ist.";
//    }
//
//    @Override
//    public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.TNT);
//    }
//
//    @Override
//    public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override
//    public boolean isShown() {
//        return timer > 0;
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
//}