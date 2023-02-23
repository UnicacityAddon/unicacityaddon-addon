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
//public class ExplosiveBeltTimerHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static int timer = "0";
//
//    @Override
//    public String getControlName() {
//        return "Sprenggürtel-Timer";
//    }
//
//    @Override
//    public String getSettingName() {
//        return null;
//    }
//
//    @Override
//    public String getDisplayName() {
//        return "Sprenggürtel";
//    }
//
//    @Override
//    public String getDisplayValue() {
//        return String.valueOf(timer);
//    }
//
//    @Override
//    public String getDefaultValue() {
//        return "0";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Zeigt einen Countdown an, welcher die Zeit zur Detonation des Sprenggürtels beschreibt.";
//    }
//
//    @Override
//    public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.LEATHER_CHESTPLATE);
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