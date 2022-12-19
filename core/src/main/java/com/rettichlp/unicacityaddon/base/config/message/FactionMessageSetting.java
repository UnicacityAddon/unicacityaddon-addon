package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface FactionMessageSetting {

    ConfigProperty<Boolean> hq();

    ConfigProperty<Boolean> service();

    ConfigProperty<Boolean> dBank();
}