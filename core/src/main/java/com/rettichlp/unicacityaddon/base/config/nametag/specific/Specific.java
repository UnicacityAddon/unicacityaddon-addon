package com.rettichlp.unicacityaddon.base.config.nametag.specific;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Specific {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<ColorCode> color();
}