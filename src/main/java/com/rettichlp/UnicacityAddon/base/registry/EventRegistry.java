package com.rettichlp.UnicacityAddon.base.registry;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class EventRegistry {

    public static void register(ASMDataTable asmDataTable) {
        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        UnicacityAddon.ADDON.getApi().getEventManager().register(new TabListEventHandler());

        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        asmDataTable.getAll(UCEvent.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerForgeListener(clazz.newInstance());
                System.out.println("UCEvent: " + clazz.getSimpleName());
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