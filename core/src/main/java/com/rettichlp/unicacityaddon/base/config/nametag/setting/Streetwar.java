package com.rettichlp.unicacityaddon.base.config.nametag.setting;

import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Streetwar {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<ColorCode> color();

    ConfigProperty<Faction> faction1();

    ConfigProperty<Faction> faction2();
}