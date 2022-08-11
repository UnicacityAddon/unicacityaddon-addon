package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
public class AmmunitionModule extends SimpleModule {

    public static boolean isShown;
    public static int currentLoadedAmmunition;
    public static int currentTakenAmmunition;

    @Override
    public String getControlName() {
        return "Munition in der Waffe";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Munition";
    }

    @Override
    public String getDisplayValue() {
        return (currentLoadedAmmunition-- < 1 ? ColorCode.RED.getCode() + "0" : ColorCode.GOLD.getCode() + currentLoadedAmmunition) + ColorCode.GRAY.getCode() + "/" + (currentTakenAmmunition < 1 ? ColorCode.RED.getCode() + "0" : ColorCode.GOLD.getCode() + currentTakenAmmunition);
    }

    @Override
    public String getDefaultValue() {
        return ColorCode.RED.getCode() + "0" + ColorCode.GRAY.getCode() + "/" + ColorCode.RED.getCode() + "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt an, wie viel Munition in der Waffe ist.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.IRON_AXE);
    }

    @Override
    public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return isShown;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }
}