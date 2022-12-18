//package com.rettichlp.unicacityaddon.modules;
//
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
//import java.text.NumberFormat;
//import java.util.Locale;
//
///**
// * @author Dimiikou
// */
//@UCModule
//public class JobHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    public static int jobBalance;
//    public static int jobExperience;
//
//    @Override
//    public String getControlName() {
//        return "Job Belohnungen";
//    }
//
//    @Override
//    public String getSettingName() {
//        return null;
//    }
//
//    @Override
//    public String getDisplayName() {
//        return "Job";
//    }
//
//    @Override
//    public String getDisplayValue() {
//        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
//        return numberFormat.format(jobBalance) + "$ | " + numberFormat.format(jobExperience) + " EXP";
//    }
//
//    @Override
//    public String getDefaultValue() {
//        return "0$ | 0 EXP";
//    }
//
//    @Override
//    public String getDescription() {
//        return "Zeigt dein Gehalt und durch Jobs erhaltene EXP bis zum nächsten PayDay an.";
//    }
//
//    @Override
//    public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.GOLD_INGOT);
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
//    public static void addBalance(int balance) {
//        jobBalance = jobBalance + balance;
//        FileManager.saveData();
//    }
//
//    public static void addExperience(int experience) {
//        jobExperience = jobExperience + experience;
//        FileManager.saveData();
//    }
//
//    public static void setBalance(int balance) {
//        jobBalance = balance;
//        FileManager.saveData();
//    }
//
//    public static void setExperience(int experience) {
//        jobExperience = experience;
//        FileManager.saveData();
//    }
//}