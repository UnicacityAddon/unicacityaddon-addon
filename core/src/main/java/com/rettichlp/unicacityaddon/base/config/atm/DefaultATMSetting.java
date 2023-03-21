package com.rettichlp.unicacityaddon.base.config.atm;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultATMSetting extends Config implements ATMSetting {

    @SwitchSetting
    @ParentSwitch
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> atmInfo = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> fBank = new ConfigProperty<>(false);

    @SwitchSetting
    private final ConfigProperty<Boolean> grBank = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override
    public ConfigProperty<Boolean> atmInfo() {
        return this.atmInfo;
    }

    @Override
    public ConfigProperty<Boolean> fBank() {
        return this.fBank;
    }

    @Override
    public ConfigProperty<Boolean> grBank() {
        return this.grBank;
    }
}