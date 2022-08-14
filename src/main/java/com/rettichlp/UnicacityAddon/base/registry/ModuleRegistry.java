package com.rettichlp.UnicacityAddon.base.registry;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCModule;
import net.labymod.ingamegui.Module;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class ModuleRegistry {

    public static final ModuleCategory UNICACITY = new ModuleCategory("Unicacity", true, new ControlElement.IconData(Material.DIAMOND));

    public static void register(ASMDataTable asmDataTable) {
        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        asmDataTable.getAll(UCModule.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerModule((Module) clazz.newInstance());
                System.out.println("UCModule: " + clazz.getSimpleName());
            } catch (ClassNotFoundException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            }
        });
    }
}