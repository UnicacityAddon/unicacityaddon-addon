package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.base.teamspeak.events.ClientMessageReceivedEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.events.ClientMovedEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.events.TSEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.exceptions.ClientQueryListenerDeclarationException;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class TSEventHandler {

    static final Map<String, Class<? extends TSEvent>> TEAMSPEAK_EVENTS = new HashMap<>();
    private static final List<TSListener> EVENT_LISTENERS = new ArrayList<>();

    static {
        registerEvent(ClientMovedEvent.class);
        registerEvent(ClientMessageReceivedEvent.class);
    }

    public static void registerListener(TSListener tsListener) {
        EVENT_LISTENERS.add(tsListener);
    }

    static void fireEvent(TSEvent event) {
        MinecraftForge.EVENT_BUS.post(event);

        try {
            for (TSListener listener : EVENT_LISTENERS) {
                Method[] declaredMethods = listener.getClass().getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(EventHandler.class)) {
                        Class<?> parameterType = declaredMethod.getParameterTypes()[0];
                        if (parameterType != event.getClass())
                            continue;

                        declaredMethod.invoke(listener, parameterType.cast(event));
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ClientQueryListenerDeclarationException(e);
        }
    }

    static TSEvent getEvent(String input) {
        int splitIndex = input.indexOf(" ");
        String eventName = input.substring(0, splitIndex);
        Class<? extends TSEvent> clazz = TEAMSPEAK_EVENTS.get(eventName);
        if (clazz == null)
            return null;

        String content = input.substring(splitIndex);
        try {
            return clazz.getConstructor(String.class).newInstance(content);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void registerEvent(Class<? extends TSEvent> clazz) {
        TSEvent.Name annotation = clazz.getAnnotation(TSEvent.Name.class);

        String eventName = annotation.value();
        TEAMSPEAK_EVENTS.put(eventName, clazz);
    }
}
