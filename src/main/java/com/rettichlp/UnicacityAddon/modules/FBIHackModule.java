package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.labymod.utils.ModUtils;

/**
 * @author Dimiikou
 */
public class FBIHackModule  extends SimpleModule {

    public static int currentCount = 0;
    public static int currentTick = 0;
    public static boolean fbiHackStarted = false;
    public static String timer = "0";

    @Override public String getControlName() {
        return "FBI Hack Countdown";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "WP-Clear";
    }

    @Override public String getDisplayValue() {
        return timer;
    }

    @Override public String getDefaultValue() {
        return "00:00";
    }

    @Override public String getDescription() {
        return "Zeigt einen Countdown an, welcher die Zeit bis zum WP Clear im FBI HQ beschreibt.";
    }

    @Override public ControlElement.IconData getIconData() { return new ControlElement.IconData(Material.BOOK); }

    @Override public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override public boolean isShown() {
        return fbiHackStarted;
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }

    public static void startCountdown(int seconds) {
        fbiHackStarted = true;
        currentCount = seconds;
        timer = ModUtils.parseTimer(currentCount);
    }

    public static void stopCountdown() {
        fbiHackStarted = false;
    }

}
