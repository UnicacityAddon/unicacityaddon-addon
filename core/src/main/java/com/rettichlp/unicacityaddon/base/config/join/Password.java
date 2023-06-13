package com.rettichlp.unicacityaddon.base.config.join;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Password {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<String> password();
}