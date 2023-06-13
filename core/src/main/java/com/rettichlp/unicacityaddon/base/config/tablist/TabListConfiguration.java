package com.rettichlp.unicacityaddon.base.config.tablist;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface TabListConfiguration {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> afk();
}