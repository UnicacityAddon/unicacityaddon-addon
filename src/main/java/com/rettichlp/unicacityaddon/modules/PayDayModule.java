package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
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
public class PayDayModule extends SimpleModule {

    @Override
    public String getControlName() {
        return "Zeit bis zum PayDay";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "PayDay";
    }

    @Override
    public String getDisplayValue() {
        return FileManager.DATA.getPayDayTime() + "/60 Minuten";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Zeigt die Minuten bis zum PayDay an.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.WATCH);
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override
    public boolean isShown() {
        return true;
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public void loadSettings() {
    }
}