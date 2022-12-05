package com.rettichlp.unicacityaddon.base.registry;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.Module;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class ModuleRegistry {

    // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
    public static final ModuleCategory UNICACITY = new ModuleCategory("Unicacity", true, new ControlElement.IconData(Material.DIAMOND));

    public static void register() {
        UnicacityAddon.asmDataTable.getAll(UCModule.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerModule((Module) clazz.newInstance());
                UnicacityAddon.LOGGER.info("Loaded module: " + clazz.getSimpleName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                UnicacityAddon.LOGGER.catching(e);
            }
        });
    }
}