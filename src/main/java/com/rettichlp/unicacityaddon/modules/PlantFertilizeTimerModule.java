package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCModule
public class PlantFertilizeTimerModule extends SimpleModule {

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
        long timeSinceLastInteraction = System.currentTimeMillis() - FileManager.DATA.getPlantFertilizeTime();
        long timeLeft = TimeUnit.MINUTES.toMillis(70) - timeSinceLastInteraction;
        return timeLeft >= 0 ? TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(timeLeft)) : ColorCode.RED.getCode() + "-" + TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(-timeLeft));
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
        return System.currentTimeMillis() - FileManager.DATA.getPlantFertilizeTime() < TimeUnit.MINUTES.toMillis(20);
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }
}
