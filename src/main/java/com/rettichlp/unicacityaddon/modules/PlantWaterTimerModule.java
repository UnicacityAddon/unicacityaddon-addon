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
public class PlantWaterTimerModule extends SimpleModule {

    @Override
    public String getControlName() {
        return "Plantage Wässern";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Wässern";
    }

    @Override
    public String getDisplayValue() {
        long timeSinceLastInteraction = System.currentTimeMillis() - FileManager.DATA.getPlantWaterTime();
        long timeLeft = TimeUnit.MINUTES.toMillis(50) - timeSinceLastInteraction;
        return timeLeft > 0 ? TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(timeLeft)) : ColorCode.RED.getCode() + "Jetzt";
    }

    @Override
    public String getDefaultValue() {
        return "00:00";
    }

    @Override
    public String getDescription() {
        return "Zeigt einen Timer an, welcher die Zeit zum nächsten Wässern beschreibt.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.WATER_BUCKET);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return FileManager.DATA.getPlantWaterTime() > -1;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }
}
