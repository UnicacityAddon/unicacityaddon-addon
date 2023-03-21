package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface StateSetting {

    ConfigProperty<String> cuff();

    ConfigProperty<String> defuseKit();

    ConfigProperty<String> elytra();

    ConfigProperty<String> grenade();

    ConfigProperty<String> shield();

    ConfigProperty<String> smoke();

    ConfigProperty<String> taser();

    ConfigProperty<String> tracker();
}