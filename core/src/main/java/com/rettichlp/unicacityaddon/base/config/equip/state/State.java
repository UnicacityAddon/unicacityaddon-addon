package com.rettichlp.unicacityaddon.base.config.equip.state;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface State {

    ConfigProperty<String> cuff();

    ConfigProperty<String> defuseKit();

    ConfigProperty<String> elytra();

    ConfigProperty<String> grenade();

    ConfigProperty<String> shield();

    ConfigProperty<String> smoke();

    ConfigProperty<String> taser();

    ConfigProperty<String> tracker();
}