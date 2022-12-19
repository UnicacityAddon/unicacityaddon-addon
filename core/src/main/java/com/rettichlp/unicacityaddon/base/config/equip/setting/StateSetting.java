package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface StateSetting {

    ConfigProperty<Integer> cuff();

    ConfigProperty<Integer> defuseKit();

    ConfigProperty<Integer> elytra();

    ConfigProperty<Integer> grenade();

    ConfigProperty<Integer> shield();

    ConfigProperty<Integer> smoke();

    ConfigProperty<Integer> taser();

    ConfigProperty<Integer> tracker();
}