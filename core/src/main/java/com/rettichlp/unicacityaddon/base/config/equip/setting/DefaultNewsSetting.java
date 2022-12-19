package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultNewsSetting extends Config implements NewsSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> notepad = new ConfigProperty<>(4500);

    @Override
    public ConfigProperty<Integer> notepad() {
        return this.notepad;
    }
}