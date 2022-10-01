package com.rettichlp.UnicacityAddon.base.registry;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import static com.rettichlp.UnicacityAddon.base.utils.DebugUtils.Debug;

public class CommandRegistry {

    public static void register(ASMDataTable asmDataTable) {
        asmDataTable.getAll(UCCommand.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                ClientCommandHandler.instance.registerCommand((ICommand) clazz.newInstance());
                Debug(CommandRegistry.class, "UCCommand: " + clazz.getSimpleName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            }
        });
    }
}