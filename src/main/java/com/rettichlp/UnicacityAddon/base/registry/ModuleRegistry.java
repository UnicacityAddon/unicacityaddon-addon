package com.rettichlp.UnicacityAddon.base.registry;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class ModuleRegistry {

    public static final ModuleCategory UNICACITY = new ModuleCategory("Unicacity", true, new ControlElement.IconData(Material.DIAMOND));

    /*
    public static void register(ASMDataTable asmDataTable) {
        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        asmDataTable.getAll(UCModule.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerModule((Module) clazz.newInstance());
                System.out.println("UCModule: " + clazz.getSimpleName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            }
        });
    }
    */
}