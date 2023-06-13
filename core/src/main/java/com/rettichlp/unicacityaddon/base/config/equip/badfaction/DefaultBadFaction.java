package com.rettichlp.unicacityaddon.base.config.equip.badfaction;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultBadFaction extends Config implements BadFaction {

    @TextFieldSetting
    private final ConfigProperty<String> baseballBat = new ConfigProperty<>("80");

    @Override
    public ConfigProperty<String> baseballBat() {
        return this.baseballBat;
    }
}