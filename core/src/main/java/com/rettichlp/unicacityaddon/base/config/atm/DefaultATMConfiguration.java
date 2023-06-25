package com.rettichlp.unicacityaddon.base.config.atm;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultATMConfiguration extends Config implements ATMConfiguration {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> info = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> faction = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> grouping = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<Boolean> info() {
        return this.info;
    }

    @Override
    public ConfigProperty<Boolean> faction() {
        return this.faction;
    }

    @Override
    public ConfigProperty<Boolean> grouping() {
        return this.grouping;
    }
}