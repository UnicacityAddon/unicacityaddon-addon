package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCModule;
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
public class BankMoneyModule extends SimpleModule {

    public static int bankBalance;

    @Override
    public String getControlName() {
        return "Geld auf der Bank";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Bank";
    }

    @Override
    public String getDisplayValue() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        return numberFormat.format(bankBalance) + "$";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt an, wie viel Geld du auf der Bank hast.";
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
        bankBalance = bankBalance + balance;
        FileManager.saveData();
    }

    public static void removeBalance(int balance) {
        bankBalance = bankBalance - balance;
        FileManager.saveData();
    }

    public static void setBalance(int balance) {
        bankBalance = balance;
        FileManager.saveData();
    }
}