package com.rettichlp.unicacityaddon.base.config.join;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface PasswordSetting {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<String> password();
}