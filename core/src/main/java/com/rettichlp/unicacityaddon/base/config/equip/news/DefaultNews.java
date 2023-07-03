package com.rettichlp.unicacityaddon.base.config.equip.news;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultNews extends Config implements News {

    @TextFieldSetting
    private final ConfigProperty<String> notepad = new ConfigProperty<>("4500");

    @TextFieldSetting
    private final ConfigProperty<String> coffee = new ConfigProperty<>("2");

    @Override
    public ConfigProperty<String> notepad() {
        return this.notepad;
    }

    @Override
    public ConfigProperty<String> coffee() {
        return this.notepad;
    }
}