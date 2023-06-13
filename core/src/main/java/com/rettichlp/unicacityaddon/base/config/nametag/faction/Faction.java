package com.rettichlp.unicacityaddon.base.config.nametag.faction;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Faction {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<ColorCode> color();
}
