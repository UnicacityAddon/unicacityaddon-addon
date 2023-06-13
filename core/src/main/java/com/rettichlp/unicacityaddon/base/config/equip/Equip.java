package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.setting.BadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Hitman;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Medic;
import com.rettichlp.unicacityaddon.base.config.equip.setting.News;
import com.rettichlp.unicacityaddon.base.config.equip.setting.State;
import com.rettichlp.unicacityaddon.base.config.equip.setting.Terror;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Equip {

    ConfigProperty<String> water();

    ConfigProperty<String> bread();

    ConfigProperty<String> donut();

    ConfigProperty<String> mask();

    ConfigProperty<String> lkev();

    ConfigProperty<String> skev();

    ConfigProperty<String> pepperSpray();

    ConfigProperty<String> pistol();

    ConfigProperty<String> mp5();

    ConfigProperty<String> sniper();

    State state();

    BadFaction badFaction();

    Medic medic();

    News news();

    Hitman hitman();

    Terror terror();
}