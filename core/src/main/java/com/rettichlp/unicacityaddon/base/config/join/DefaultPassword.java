package com.rettichlp.unicacityaddon.base.config.join;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultPassword extends Config implements Password {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @TextFieldSetting
    private final ConfigProperty<String> password = new ConfigProperty<>("");

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<String> password() {
        return this.password;
    }
}