package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModule;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author RettichLP
 */
@UCModule
public class BombTimerModule extends SimpleModule {

    public static int currentCount = 0;
    public static int currentTick = 0;
    public static boolean isBomb = false;
    public static String timer = "";

    @Override public String getControlName() {
        return "Bomben-Timer";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Bombe";
    }

    @Override public String getDisplayValue() {
        return timer;
    }

    @Override public String getDefaultValue() {
        return "00:00";
    }

    @Override public String getDescription() {
        return "Zeigt die Zeit an, die seit dem Legen einer Bombe vergangen ist.";
    }

    @Override public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.TNT);
    }

    @Override public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override public boolean isShown() {
        return !timer.isEmpty();
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }
}