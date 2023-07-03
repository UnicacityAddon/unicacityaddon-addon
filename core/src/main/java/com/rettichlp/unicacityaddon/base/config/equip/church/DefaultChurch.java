package com.rettichlp.unicacityaddon.base.config.equip.church;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultChurch extends Config implements Church {

    @TextFieldSetting
    private final ConfigProperty<String> soup = new ConfigProperty<>("2");

    @Override
    public ConfigProperty<String> soup() {
        return this.soup;
    }
}