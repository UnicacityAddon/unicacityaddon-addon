package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import java.text.DecimalFormat;

/**
 * @author Dimiikou
 */
@UCModule
public class PlantFertilizeTimerModule extends SimpleModule {

    public static boolean plantRunning = false;

    public static int currentCount = 0;
    public static int currentTick = 0;
    public static final int timeNeeded = 4200;
    public static String timer = "";

    @Override
    public String getControlName() {
        return "Plantage Düngen";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Düngen";
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
        return "Zeigt einen Timer an, welcher die Zeit zum nächsten Düngen beschreibt.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.DIRT);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return plantRunning;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void stopPlant() {
        plantRunning = false;
        currentCount = 0;
        currentTick = 0;
        timer = "";
    }

    public static String calcTimer(int count) {
        int minutes = count / 60;
        int seconds = count - (minutes * 60);
        final DecimalFormat format = new DecimalFormat("00");

        return format.format(minutes) + ":" + format.format(seconds);
    }
}
