package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultTerrorSetting extends Config implements TerrorSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> explosiveBelt = new ConfigProperty<>(4000);

    @TextFieldSetting
    private final ConfigProperty<Integer> rpg7 = new ConfigProperty<>(13000);

    @Override
    public ConfigProperty<Integer> explosiveBelt() {
        return this.explosiveBelt;
    }

    @Override
    public ConfigProperty<Integer> rpg7() {
        return this.rpg7;
    }
}