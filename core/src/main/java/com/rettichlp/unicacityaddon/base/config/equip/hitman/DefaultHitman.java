package com.rettichlp.unicacityaddon.base.config.equip.hitman;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultHitman extends Config implements Hitman {

    @TextFieldSetting
    private final ConfigProperty<String> glassCutter = new ConfigProperty<>("2200");

    @TextFieldSetting
    private final ConfigProperty<String> lockPick = new ConfigProperty<>("2200");

    @Override
    public ConfigProperty<String> glassCutter() {
        return this.glassCutter;
    }

    @Override
    public ConfigProperty<String> lockPick() {
        return this.lockPick;
    }
}