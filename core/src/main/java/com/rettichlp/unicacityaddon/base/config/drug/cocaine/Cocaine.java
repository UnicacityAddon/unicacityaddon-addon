package com.rettichlp.unicacityaddon.base.config.drug.cocaine;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Cocaine {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Integer> amount();

    ConfigProperty<DrugPurity> purity();

    ConfigProperty<Integer> best();

    ConfigProperty<Integer> good();

    ConfigProperty<Integer> medium();

    ConfigProperty<Integer> bad();
}