package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author Dimiikou
 */
@UCModule
public class FBIHackModule extends SimpleModule {

    public static int currentCount = 0;
    public static boolean fbiHackStarted = false;
    public static String timer = "0";

    @Override
    public String getControlName() {
        return "FBI Hack Countdown";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "WP-Clear";
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
        return "Zeigt einen Countdown an, welcher die Zeit bis zum WP Clear im FBI HQ beschreibt.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.BOOK);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return fbiHackStarted;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void startCountdown(int seconds) {
        fbiHackStarted = true;
        currentCount = seconds;
        timer = TextUtils.parseTimer(currentCount);
    }

    public static void stopCountdown() {
        fbiHackStarted = false;
    }
}