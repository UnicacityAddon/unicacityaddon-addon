package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
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
        return cashBalance + "$";
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
        return UCModuleHandler.UNICACITY;
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