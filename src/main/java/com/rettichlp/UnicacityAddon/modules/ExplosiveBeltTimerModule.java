package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author Dimiikou
 */
@UCModule
public class ExplosiveBeltTimerModule extends SimpleModule {

    public static int currentCount = 0;
    public static boolean explosiveBeltStarted = false;
    public static String timer = "0";

    @Override
    public String getControlName() {
        return "Sprenggürtel-Timer";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Sprenggürtel";
    }

    @Override
    public String getDisplayValue() {
        return timer;
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt einen Countdown an, welcher die Zeit zur Detonation des Sprenggürtels beschreibt.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.LEATHER_CHESTPLATE);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return explosiveBeltStarted;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void stopBombTimer() {
        explosiveBeltStarted = false;
        currentCount = 0;
        timer = "";
    }
}