package com.rettichlp.UnicacityAddon.base.command;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.commands.TriggerEventCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import net.labymod.api.events.MessageSendEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
@UCEventLabymod(event = "MessageSendEvent")
public class UCCommandHandler implements MessageSendEvent {

    @Override public boolean onSend(String s) {
        if (!s.startsWith("/")) return false;

        String label = s.substring(1);
        List<String> args = new ArrayList<>();

        if (s.contains(" ")) {
            label = s.split(" ")[0].substring(1);
            args = Arrays.asList(s.substring(label.length() + 2).split(" "));
        }

        Map<String, Method> commandMap = getCommandMap();
        if (!commandMap.containsKey(label)) return false;
        // e.setCancelled(true);

        Method method = commandMap.get(label);
        Class<?> clazz = method.getDeclaringClass();

        try {
            Object commandClassInstance = clazz.newInstance();
            boolean success = (boolean) method.invoke(commandClassInstance, AbstractionLayer.getPlayer(), args);

            if (!success) Message.getBuilder()
                    .error()
                    .space()
                    .of("Syntax: " + method.getAnnotation(UCCommand.class).usage().replace("%label%", label)).color(ColorCode.GRAY).advance()
                    .sendTo(AbstractionLayer.getPlayer().getPlayer());

        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
            throw new RuntimeException(ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    private Map<String, Method> getCommandMap() {
        Map<String, Method> commandMap = new HashMap<>();

        try {

            // HIER COMMANDS REGISTRIEREN
            // COMMANDNAME, onCommand class

            commandMap.put("triggerevent", TriggerEventCommand.class.getDeclaredMethod("onCommand", UPlayer.class, List.class));
            commandMap.put("reinforcement", ReinforcementCommand.class.getDeclaredMethod("onCommand", UPlayer.class, List.class));
            commandMap.put("callreinforcement", ReinforcementCommand.class.getDeclaredMethod("onCommand", UPlayer.class, List.class));
            commandMap.put("reinf", ReinforcementCommand.class.getDeclaredMethod("onCommand", UPlayer.class, List.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        /*ReflectionUtils.getMethodsAnnotatedWith(UCCommand.class, "com.rettichlp.UnicacityAddon.commands").forEach(method -> {
            UCCommand ucCommand = method.getAnnotation(UCCommand.class);
            for (int i = 0; i < ucCommand.value().length; i++) {
                commandMap.put(ucCommand.value()[i], method);
            }
        });*/

        return commandMap;
    }
}