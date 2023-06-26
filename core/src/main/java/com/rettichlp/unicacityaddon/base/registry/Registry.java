package com.rettichlp.unicacityaddon.base.registry;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 */
public class Registry {

    @Accessors(fluent = true)
    @Getter
    private final Set<Command> commands = new HashSet<>();

    private final UnicacityAddon unicacityAddon;

    public Registry(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void registerTags() {
        TagRegistry registry = this.unicacityAddon.labyAPI().tagRegistry();

        AtomicInteger registeredNameTagCount = new AtomicInteger();
        Set<Class<?>> nameTagClassSet = this.unicacityAddon.services().util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.nametags");
        nameTagClassSet.stream()
                .filter(nameTagClass -> nameTagClass.isAnnotationPresent(UCNameTag.class))
                .sorted(Comparator.comparing(nameTagClass -> nameTagClass.getAnnotation(UCNameTag.class).priority()))
                .forEach(nameTagClass -> {
                    UCNameTag ucNameTag = nameTagClass.getAnnotation(UCNameTag.class);
                    try {
                        NameTag nameTag = (NameTag) nameTagClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                        Objects.requireNonNull(nameTag, "NameTag");
                        registry.register(ucNameTag.name(), ucNameTag.positionType(), nameTag);

                        registeredNameTagCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.unicacityAddon.logger().warn("Can't register NameTag: {}", nameTagClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} NameTags", registeredNameTagCount, nameTagClassSet.size());
    }

    public void registerHudWidgets() {
        HudWidgetRegistry registry = this.unicacityAddon.labyAPI().hudWidgetRegistry();

        AtomicInteger registeredHudWidgetCount = new AtomicInteger();
        Set<Class<?>> hudWidgetClassSet = this.unicacityAddon.services().util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.hudwidgets");
        hudWidgetClassSet.forEach(hudWidgetClass -> {
            try {
                TextHudWidget<TextHudWidgetConfig> textHudWidget = (TextHudWidget<TextHudWidgetConfig>) hudWidgetClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                Objects.requireNonNull(textHudWidget, "HudWidget");
                registry.register(textHudWidget);

                registeredHudWidgetCount.getAndIncrement();
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                     InstantiationException e) {
                this.unicacityAddon.logger().warn("Can't register HudWidget: {}", hudWidgetClass.getSimpleName());
                e.printStackTrace();
            }
        });
        this.unicacityAddon.logger().info("Registered {}/{} HudWidgets", registeredHudWidgetCount, hudWidgetClassSet.size());
    }

    public void registerListeners() {
        AtomicInteger registeredListenerCount = new AtomicInteger();
        Set<Class<?>> listenerClassSet = this.unicacityAddon.services().util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.listener");
        listenerClassSet.stream()
                .filter(listenerClass -> listenerClass.isAnnotationPresent(UCEvent.class))
                .forEach(listenerClass -> {
                    try {
                        Object listener = listenerClass.getConstructor(UnicacityAddon.class).newInstance(this.unicacityAddon);

                        Objects.requireNonNull(listener, "Listener");
                        this.unicacityAddon.labyAPI().eventBus().registerListener(listener);

                        registeredListenerCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.unicacityAddon.logger().warn("Can't register Listener: {}", listenerClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} Listeners", registeredListenerCount, listenerClassSet.size());
    }

    public void registerCommands() {
        AtomicInteger registeredCommandCount = new AtomicInteger();
        AtomicInteger deactivatedCommandCount = new AtomicInteger();
        Set<Class<?>> commandClassSet = this.unicacityAddon.services().util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.commands");
        commandClassSet.remove(UnicacityCommand.class);
        commandClassSet.stream()
                .filter(commandClass -> commandClass.isAnnotationPresent(UCCommand.class))
                .forEach(commandClass -> {
                    UCCommand ucCommand = commandClass.getAnnotation(UCCommand.class);
                    if (ucCommand.deactivated()) {
                        deactivatedCommandCount.getAndIncrement();
                    } else {
                        try {
                            Command command = (Command) commandClass.getConstructor(UnicacityAddon.class, UCCommand.class).newInstance(this.unicacityAddon, ucCommand);

                            Objects.requireNonNull(command, "Command");
                            this.commands.add(command);
                            this.unicacityAddon.labyAPI().commandService().register(command);

                            registeredCommandCount.getAndIncrement();
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                                 InstantiationException e) {
                            this.unicacityAddon.logger().warn("Can't register Command: {}", commandClass.getSimpleName());
                            e.printStackTrace();
                        }
                    }
                });
        this.unicacityAddon.logger().info("Registered {}/{} Commands, {} skipped (deactivated)", registeredCommandCount, commandClassSet.size() - deactivatedCommandCount.get(), deactivatedCommandCount.get());
    }
}
