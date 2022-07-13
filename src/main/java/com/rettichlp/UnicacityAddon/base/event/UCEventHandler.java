package com.rettichlp.UnicacityAddon.base.event;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import net.labymod.api.events.MessageModifyChatEvent;
import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.api.events.MouseInputEvent;
import net.labymod.api.events.PluginMessageEvent;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.api.events.RenderIngameOverlayEvent;
import net.labymod.api.events.TabListEvent;
import net.labymod.api.events.UserMenuActionEvent;

/**
 * @author RettichLP
 */
public class UCEventHandler {

    public static void registerEvents() {
        ReflectionUtils.getClassesAnnotatedWith(UCEventLabymod.class, "com.rettichlp.UnicacityAddon.events").forEach(clazz -> {
            try {
                switch (clazz.getAnnotation(UCEventLabymod.class).event()) {
                case "TabListEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((TabListEvent) clazz.newInstance());
                    break;
                case "MessageModifyChatEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((MessageModifyChatEvent) clazz.newInstance());
                    break;
                case "MessageReceiveEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((MessageReceiveEvent) clazz.newInstance());
                    break;
                case "MessageSendEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((MessageSendEvent) clazz.newInstance());
                    break;
                case "PluginMessageEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((PluginMessageEvent) clazz.newInstance());
                    break;
                case "RenderEntityEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((RenderEntityEvent) clazz.newInstance());
                    break;
                case "UserMenuActionEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((UserMenuActionEvent) clazz.newInstance());
                    break;
                case "MouseInputEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((MouseInputEvent) clazz.newInstance());
                    break;
                case "RenderIngameOverlayEvent":
                    UnicacityAddon.ADDON.getApi().getEventManager().register((RenderIngameOverlayEvent) clazz.newInstance());
                    break;

                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        ReflectionUtils.getClassesAnnotatedWith(UCEventForge.class, "com.rettichlp.UnicacityAddon.events").forEach(clazz -> {
            try {
                UnicacityAddon.ADDON.getApi().registerForgeListener(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}