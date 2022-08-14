package com.rettichlp.UnicacityAddon.base.registry;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

public class CommandRegistry {

    public static void register(ASMDataTable asmDataTable) {
        // ForgeCommands
        asmDataTable.getAll(UCCommand.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                ClientCommandHandler.instance.registerCommand((ICommand) clazz.newInstance());
                System.out.println("UCCommand: " + clazz.getSimpleName());
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