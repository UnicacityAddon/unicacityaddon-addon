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
public class PaydayModule extends SimpleModule {

    public static int currentTime;

    @Override
    public String getControlName() {
        return "Zeit bis zum Payday";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Payday";
    }

    @Override
    public String getDisplayValue() {
        return currentTime + "/60 Minuten";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt die Minuten bis zum Payday an.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.WATCH);
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

    public static void addTime(int time) {
        currentTime = currentTime + time;
        UnicacityAddon.saveOfflineData();
    }

    public static void removeTime(int time) {
        currentTime = currentTime - time;
        UnicacityAddon.saveOfflineData();
    }

    public static void setTime(int time) {
        currentTime = time;
        UnicacityAddon.saveOfflineData();
    }
}