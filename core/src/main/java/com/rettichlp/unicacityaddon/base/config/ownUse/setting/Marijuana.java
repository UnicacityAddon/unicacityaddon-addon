package com.rettichlp.unicacityaddon.base.config.ownUse.setting;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Marijuana {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Integer> amount();

    ConfigProperty<DrugPurity> purity();
}