package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.badfaction.BadFaction;
import com.rettichlp.unicacityaddon.base.config.equip.hitman.Hitman;
import com.rettichlp.unicacityaddon.base.config.equip.medic.Medic;
import com.rettichlp.unicacityaddon.base.config.equip.news.News;
import com.rettichlp.unicacityaddon.base.config.equip.state.State;
import com.rettichlp.unicacityaddon.base.config.equip.terrorist.Terrorist;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface EquipConfiguration {

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

    Terrorist terrorist();
}