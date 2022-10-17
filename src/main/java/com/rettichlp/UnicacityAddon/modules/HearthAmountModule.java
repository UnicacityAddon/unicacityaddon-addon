package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import static com.rettichlp.UnicacityAddon.base.utils.MathUtils.HEART_DECIMAL_FORMAT;

/**
 * @author Dimiikou
 */
public class HearthAmountModule extends SimpleModule {

    @Override public String getControlName() {
        return "Herzen";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Herzen";
    }

    @Override public String getDisplayValue() {
        return HEART_DECIMAL_FORMAT.format(AbstractionLayer.getPlayer().getPlayer().getHealth() / 2) + ColorCode.RED.getCode() + "❤";
    }

    @Override public String getDefaultValue() {
        return "0";
    }

    @Override public String getDescription() {
        return "Zeigt an wie viele Herzen du hast";
    }

    @Override public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.RED_ROSE);
    }

    @Override public ModuleCategory getCategory() {
        return ModuleRegistry.UNICACITY;
    }

    @Override public boolean isShown() {
        return UnicacityAddon.isUnicacity();
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }
}