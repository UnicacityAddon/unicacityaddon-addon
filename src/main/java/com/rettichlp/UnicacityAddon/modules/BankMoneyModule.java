package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
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
        return bankBalance + "$";
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
        bankBalance = bankBalance + balance;
        UnicacityAddon.saveOfflineData();
    }

    public static void removeBalance(int balance) {
        bankBalance = bankBalance - balance;
        UnicacityAddon.saveOfflineData();
    }

    public static void setBalance(int balance) {
        bankBalance = balance;
        UnicacityAddon.saveOfflineData();
    }
}