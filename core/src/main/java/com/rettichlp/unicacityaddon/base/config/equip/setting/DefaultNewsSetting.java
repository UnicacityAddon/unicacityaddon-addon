package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultNewsSetting extends Config implements NewsSetting {

    @TextFieldSetting
    private final ConfigProperty<String> notepad = new ConfigProperty<>("4500");

    @Override
    public ConfigProperty<String> notepad() {
        return this.notepad;
    }
}