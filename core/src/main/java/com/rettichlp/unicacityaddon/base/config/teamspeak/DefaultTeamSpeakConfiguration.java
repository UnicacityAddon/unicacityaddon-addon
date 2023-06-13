package com.rettichlp.unicacityaddon.base.config.teamspeak;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public class DefaultTeamSpeakConfiguration extends Config implements TeamSpeakConfiguration {

    @SwitchSetting
    private final ConfigProperty<Boolean> resolve = new ConfigProperty<>(true);

    @TextFieldSetting
    private final ConfigProperty<String> key = new ConfigProperty<>("");

    @SwitchSetting
    private final ConfigProperty<Boolean> publicity = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> support = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> resolve() {
        return this.resolve;
    }

    @Override
    public ConfigProperty<String> key() {
        return this.key;
    }

    @Override
    public ConfigProperty<Boolean> publicity() {
        return this.publicity;
    }

    @Override
    public ConfigProperty<Boolean> support() {
        return this.support;
    }
}