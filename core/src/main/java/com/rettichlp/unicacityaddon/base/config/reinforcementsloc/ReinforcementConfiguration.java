package com.rettichlp.unicacityaddon.base.config.reinforcementsloc;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface ReinforcementConfiguration {

    ConfigProperty<String> reinforcement();

    ConfigProperty<String> answer();

    ConfigProperty<Boolean> screen();
}