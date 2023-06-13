package com.rettichlp.unicacityaddon.base.config.join;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultCommand extends Config implements Command {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @TextFieldSetting
    private final ConfigProperty<String> first = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> second = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> third = new ConfigProperty<>("");

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<String> first() {
        return this.first;
    }

    @Override
    public ConfigProperty<String> second() {
        return this.second;
    }

    @Override
    public ConfigProperty<String> third() {
        return this.third;
    }
}