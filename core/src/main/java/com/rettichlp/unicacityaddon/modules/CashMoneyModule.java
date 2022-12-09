package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author RettichLP
 */
@UCModule
public class CashMoneyModule extends SimpleModule {

    public static int cashBalance;

    @Override
    public String getControlName() {
        return "Geld auf der Hand";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Bargeld";
    }

    @Override
    public String getDisplayValue() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        return numberFormat.format(cashBalance) + "$";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt an, wie viel Geld du auf der Hand hast.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.GOLD_INGOT);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return true;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void addBalance(int balance) {
        cashBalance = cashBalance + balance;
        FileManager.saveData();
    }

    public static void removeBalance(int balance) {
        cashBalance = cashBalance - balance;
        FileManager.saveData();
    }

    public static void setBalance(int balance) {
        cashBalance = balance;
        FileManager.saveData();
    }
}