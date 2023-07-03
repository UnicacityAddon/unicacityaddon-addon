package com.rettichlp.unicacityaddon.base.config.teamspeak;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface TeamSpeakConfiguration {

    ConfigProperty<Boolean> resolve();

    ConfigProperty<String> key();

    ConfigProperty<Boolean> publicity();

    ConfigProperty<Boolean> support();
}