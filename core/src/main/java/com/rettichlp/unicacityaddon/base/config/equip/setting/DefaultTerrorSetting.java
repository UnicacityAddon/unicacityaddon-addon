package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultTerrorSetting extends Config implements TerrorSetting {

    @TextFieldSetting
    private final ConfigProperty<String> explosiveBelt = new ConfigProperty<>("4000");

    @TextFieldSetting
    private final ConfigProperty<String> rpg7 = new ConfigProperty<>("13000");

    @Override
    public ConfigProperty<String> explosiveBelt() {
        return this.explosiveBelt;
    }

    @Override
    public ConfigProperty<String> rpg7() {
        return this.rpg7;
    }
}