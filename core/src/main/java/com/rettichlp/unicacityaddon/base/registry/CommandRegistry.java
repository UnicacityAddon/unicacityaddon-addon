package com.rettichlp.unicacityaddon.base.registry;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandRegistry {

    public static void register() {
        UnicacityAddon.asmDataTable.getAll(UCCommand.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                ClientCommandHandler.instance.registerCommand((ICommand) clazz.newInstance());
                UnicacityAddon.LOGGER.info("Loaded command: " + clazz.getSimpleName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                UnicacityAddon.LOGGER.catching(e);
            }
        });
    }
}