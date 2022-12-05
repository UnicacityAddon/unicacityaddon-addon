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
public class EmergencyServiceModule extends SimpleModule {

    public static int currentCount = 0;

    @Override public String getControlName() {
        return "Service-Count";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Notrufe";
    }

    @Override public String getDisplayValue() {
        return String.valueOf(currentCount);
    }

    @Override public String getDefaultValue() {
        return "0";
    }

    @Override public String getDescription() {
        return "Zeigt an, wie viele Notrufe offen sind.";
    }

    @Override public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.REDSTONE_LAMP_ON);
    }

    @Override public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override public boolean isShown() {
        return !String.valueOf(currentCount).isEmpty();
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }
}