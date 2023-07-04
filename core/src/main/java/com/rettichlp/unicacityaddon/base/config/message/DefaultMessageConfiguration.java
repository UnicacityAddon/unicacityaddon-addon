package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/**
 * @author RettichLP
 */
public class DefaultMessageConfiguration extends Config implements MessageConfiguration {

    @SettingSection("faction")
    @SwitchSetting
    private final ConfigProperty<Boolean> hq = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> service = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> dBank = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> contract = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> filteredContractlist = new ConfigProperty<>(true);

    @SettingSection("report")
    @TextFieldSetting
    private final ConfigProperty<String> greeting = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> farewell = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> prefix = new ConfigProperty<>("");

    @Override
    public ConfigProperty<Boolean> hq() {
        return this.hq;
    }

    @Override
    public ConfigProperty<Boolean> service() {
        return this.service;
    }

    @Override
    public ConfigProperty<Boolean> dBank() {
        return this.dBank;
    }

    @Override
    public ConfigProperty<Boolean> contract() {
        return this.contract;
    }

    @Override
    public ConfigProperty<Boolean> filteredContractlist() {
        return this.filteredContractlist;
    }

    @Override
    public ConfigProperty<String> greeting() {
        return this.greeting;
    }

    @Override
    public ConfigProperty<String> farewell() {
        return this.farewell;
    }

    @Override
    public ConfigProperty<String> prefix() {
        return this.prefix;
    }
}