package com.rettichlp.unicacityaddon.base.config.reinforcement;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface ReinforcementConfiguration {

    ConfigProperty<String> reinforcement();

    ConfigProperty<String> answer();

    ConfigProperty<Boolean> screen();
}