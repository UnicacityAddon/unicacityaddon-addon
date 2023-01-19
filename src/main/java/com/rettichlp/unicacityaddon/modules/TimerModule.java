package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.labymod.utils.ModUtils;

/**
 * Timer for:
 * <ul>
 *     <li>FBI burglary (time until wps are cleared)</li>
 *     <li>Explosive belt timer (time until explosive belt explodes)</li>
 *     <li>Start of shot (timer until you are allowed to shot after revive)</li>
 * </ul>
 *
 * @author Dimiikou
 * @author RettichLP
 */
@UCModule
public class TimerModule extends SimpleModule {

    public static int seconds = 0;
    public static boolean countdown = false;
    public static boolean active = false;

    private static String timerString = "00:00";

    @Override
    public String getControlName() {
        return "Timer";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Timer";
    }

    @Override
    public String getDisplayValue() {
        return timerString;
    }

    @Override
    public String getDefaultValue() {
        return "00:00";
    }

    @Override
    public String getDescription() {
        return "Zeigt einen Mehrzweck-Timer an.";
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
        return active;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }

    public static void startTimer(int secondsIn, boolean countdownIn) {
        seconds = secondsIn;
        countdown = countdownIn;
        active = true;
        timerString = ModUtils.parseTimer(secondsIn);
    }

    public static void stopTimer() {
        seconds = 0;
        countdown = false;
        active = false;
    }
}