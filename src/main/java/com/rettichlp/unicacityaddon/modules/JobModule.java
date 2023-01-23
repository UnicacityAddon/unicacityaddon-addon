package com.rettichlp.unicacityaddon.modules;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Dimiikou
 */
@UCModule
public class JobModule extends SimpleModule {

    @Override
    public String getControlName() {
        return "Job Belohnungen";
    }

    @Override
    public String getSettingName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Job";
    }

    @Override
    public String getDisplayValue() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        return numberFormat.format(FileManager.DATA.getJobBalance()) + "$ | " + numberFormat.format(FileManager.DATA.getJobExperience()) + " EXP";
    }

    @Override
    public String getDefaultValue() {
        return "0$ | 0 EXP";
    }

    @Override
    public String getDescription() {
        return "Zeigt dein Gehalt und durch Jobs erhaltene EXP bis zum nächsten PayDay an.";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.GOLD_INGOT);
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