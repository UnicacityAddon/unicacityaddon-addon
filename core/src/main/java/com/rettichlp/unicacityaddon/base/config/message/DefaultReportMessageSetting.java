package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget.TextFieldSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class DefaultReportMessageSetting extends Config implements ReportMessageSetting {

    @TextFieldSetting
    private final ConfigProperty<String> greeting = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> farewell = new ConfigProperty<>("");

    @TextFieldSetting
    private final ConfigProperty<String> prefix = new ConfigProperty<>("");

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