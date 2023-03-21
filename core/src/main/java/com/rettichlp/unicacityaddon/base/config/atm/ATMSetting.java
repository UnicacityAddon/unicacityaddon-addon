package com.rettichlp.unicacityaddon.base.config.atm;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface ATMSetting {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> atmInfo();

    ConfigProperty<Boolean> fBank();

    ConfigProperty<Boolean> grBank();
}