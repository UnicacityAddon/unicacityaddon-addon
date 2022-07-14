package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModule;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

@UCModule
public class CarOpenModule extends SimpleModule {

    public static String info = "";

    @Override public String getControlName() {
        return "Auto-Info";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Auto";
    }

    @Override public String getDisplayValue() {
        return info;
    }

    @Override public String getDefaultValue() {
        return "";
    }

    @Override public String getDescription() {
        return "Zeigt an, ob dein Auto offen oder abgeschlossen ist.";
    }

    @Override public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.MINECART);
    }

    @Override public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override public boolean isShown() {
        return !info.isEmpty();
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }
}