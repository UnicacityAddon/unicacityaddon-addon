package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultHitmanSetting extends Config implements HitmanSetting {

    @TextFieldSetting
    private final ConfigProperty<Integer> glassCutter = new ConfigProperty<>(2200);

    @TextFieldSetting
    private final ConfigProperty<Integer> lockPick = new ConfigProperty<>(2200);

    @Override
    public ConfigProperty<Integer> glassCutter() {
        return this.glassCutter;
    }

    @Override
    public ConfigProperty<Integer> lockPick() {
        return this.lockPick;
    }
}