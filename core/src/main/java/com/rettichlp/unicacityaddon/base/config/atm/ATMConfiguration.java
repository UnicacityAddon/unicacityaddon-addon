package com.rettichlp.unicacityaddon.base.config.atm;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface ATMConfiguration {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> info();

    ConfigProperty<Boolean> faction();

    ConfigProperty<Boolean> grouping();
}