package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Report {

    ConfigProperty<String> greeting();

    ConfigProperty<String> farewell();

    ConfigProperty<String> prefix();
}