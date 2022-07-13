package com.rettichlp.UnicacityAddon.base.command;

import com.google.common.eventbus.Subscribe;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
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
        System.out.println("Meine Message: " + s);
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

    @Subscribe
    public boolean onMessageSend(MessageSendEvent e) {
        String msg = e.toString();
        System.out.println("Meine Message: " + msg);
        if (!msg.startsWith("/")) return false;

        String label = msg.substring(1);
        List<String> args = new ArrayList<>();

        if (msg.contains(" ")) {
            label = msg.split(" ")[0].substring(1);
            args = Arrays.asList(msg.substring(label.length() + 2).split(" "));
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

        ReflectionUtils.getMethodsAnnotatedWith(UCCommand.class, "com.rettichlp.UnicacityAddon.commands").forEach(method -> {
            UCCommand ucCommand = method.getAnnotation(UCCommand.class);
            for (int i = 0; i < ucCommand.value().length; i++) {
                commandMap.put(ucCommand.value()[i], method);
            }
        });

        return commandMap;
    }
}