package com.rettichlp.unicacityaddon.base.config.playerlist;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface PlayerListConfiguration {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> sorted();

    ConfigProperty<Boolean> afk();
}