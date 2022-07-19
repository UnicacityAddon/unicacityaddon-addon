package com.rettichlp.UnicacityAddon.base.command;

import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;

public class UCCommandHandler {

    public static void registerCommands() {
        ReflectionUtils.getMethodsAnnotatedWith(UCCommand.class, "com.rettichlp.UnicacityAddon.commands").forEach(method -> {
            try {
                ClientCommandHandler.instance.registerCommand((ICommand) method.getDeclaringClass().newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}