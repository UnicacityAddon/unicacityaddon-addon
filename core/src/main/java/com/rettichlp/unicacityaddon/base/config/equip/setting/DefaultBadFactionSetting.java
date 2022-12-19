package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultBadFactionSetting extends Config implements BadFactionSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> baseballBat = new ConfigProperty<>(80);

    @Override
    public ConfigProperty<Integer> baseballBat() {
        return this.baseballBat;
    }
}