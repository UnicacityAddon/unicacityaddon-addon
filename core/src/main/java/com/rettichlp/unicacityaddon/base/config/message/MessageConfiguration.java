package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface MessageConfiguration {

    ConfigProperty<Boolean> hq();

    ConfigProperty<Boolean> service();

    ConfigProperty<Boolean> dBank();

    ConfigProperty<Boolean> contract();

    ConfigProperty<Boolean> filteredContractlist();

    ConfigProperty<String> greeting();

    ConfigProperty<String> farewell();

    ConfigProperty<String> prefix();
}