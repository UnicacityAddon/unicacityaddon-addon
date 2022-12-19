package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface ReportMessageSetting {

    ConfigProperty<String> greeting();

    ConfigProperty<String> farewell();

    ConfigProperty<String> prefix();
}