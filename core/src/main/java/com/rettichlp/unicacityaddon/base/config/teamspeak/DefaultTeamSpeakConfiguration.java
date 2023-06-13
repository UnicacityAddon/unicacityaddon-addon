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
    private final ConfigProperty<Boolean> resolveAPIKey = new ConfigProperty<>(true);

    @TextFieldSetting
    private final ConfigProperty<String> tsApiKey = new ConfigProperty<>("");

    @SwitchSetting
    private final ConfigProperty<Boolean> tsNotificationPublic = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> tsNotificationSupport = new ConfigProperty<>(false);

    @Override
    public ConfigProperty<Boolean> resolveAPIKey() {
        return this.resolveAPIKey;
    }

    @Override
    public ConfigProperty<String> tsApiKey() {
        return this.tsApiKey;
    }

    @Override
    public ConfigProperty<Boolean> tsNotificationPublic() {
        return this.tsNotificationPublic;
    }

    @Override
    public ConfigProperty<Boolean> tsNotificationSupport() {
        return this.tsNotificationSupport;
    }
}