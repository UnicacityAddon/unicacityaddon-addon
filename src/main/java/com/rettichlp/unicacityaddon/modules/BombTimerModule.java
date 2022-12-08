package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
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
    public static boolean isBomb = false;
    public static String timer = "";

    @Override
    public String getControlName() {
        return "Bomben-Timer";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Bombe";
    }

    @Override
    public String getDisplayValue() {
        return timer;
    }

    @Override
    public String getDefaultValue() {
        return "00:00";
    }

    @Override
    public String getDescription() {
        return "Zeigt die Zeit an, die seit dem Legen einer Bombe vergangen ist.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.TNT);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return !timer.isEmpty();
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void stopBombTimer() {
        isBomb = false;
        currentCount = 0;
        timer = "";
    }
}