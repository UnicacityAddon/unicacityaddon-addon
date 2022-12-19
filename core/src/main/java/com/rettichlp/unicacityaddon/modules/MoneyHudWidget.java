//package com.rettichlp.unicacityaddon.modules;
//
//import com.rettichlp.unicacityaddon.base.manager.FileManager;
//import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
//
//import java.text.NumberFormat;
//import java.util.Locale;
//
///**
// * @author RettichLP
// */
//@UCModule
//public class MoneyHudWidget extends TextHudWidget<TextHudWidgetConfig> implements ModuleUpdateListener {
//
//    public static int bankBalance;
//    public static int cashBalance;
//
//    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
//    private static TextLine bankLine;
//    private static TextLine cashLine;
//
//    public MoneyHudWidget(String id) {
//        super(id);
//    }
//
//    @Override
//    public void load(TextHudWidgetConfig config) {
//        super.load(config);
//
//        bankLine = super.createLine("Bank", numberFormat.format(bankBalance) + "$");
//        cashLine = super.createLine("Bargeld", numberFormat.format(cashBalance) + "$");
//    }
//
//    @Override
//    public void onBankAdd(int amount) {
//        bankBalance += amount;
//        bankLine.update(numberFormat.format(bankBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onBankRemove(int amount) {
//        bankBalance -= amount;
//        bankLine.update(numberFormat.format(bankBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onBankSet(int amount) {
//        bankBalance = amount;
//        bankLine.update(numberFormat.format(bankBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onCashAdd(int amount) {
//        cashBalance += amount;
//        cashLine.update(numberFormat.format(cashBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onCashRemove(int amount) {
//        cashBalance -= amount;
//        cashLine.update(numberFormat.format(cashBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onCashSet(int amount) {
//        cashBalance = amount;
//        cashLine.update(numberFormat.format(cashBalance) + "$");
//        FileManager.saveData();
//    }
//
//    @Override
//    public void onJobBalanceAdd(int parseInt) {
//
//    }
//
//    @Override
//    public void onJobBalanceSet(int amount) {
//
//    }
//
//    @Override
//    public void onJobExperienceAdd(int i) {
//
//    }
//
//    @Override
//    public void onJobExperienceSet(int amount) {
//
//    }
//
//    @Override
//    public void onPayDayTimeSet(int time) {
//
//    }
//}