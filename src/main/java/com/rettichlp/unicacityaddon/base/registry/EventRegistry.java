package com.rettichlp.unicacityaddon.base.registry;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventRegistry {

    public static void register() {
        UnicacityAddon.asmDataTable.getAll(UCEvent.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                UnicacityAddon.LOGGER.info("Loaded event: " + clazz.getSimpleName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                UnicacityAddon.LOGGER.catching(e);
            }
        });
    }
}