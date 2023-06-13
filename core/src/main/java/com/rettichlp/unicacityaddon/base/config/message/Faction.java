package com.rettichlp.unicacityaddon.base.config.message;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Faction {

    ConfigProperty<Boolean> hq();

    ConfigProperty<Boolean> service();

    ConfigProperty<Boolean> dBank();

    ConfigProperty<Boolean> contract();
}