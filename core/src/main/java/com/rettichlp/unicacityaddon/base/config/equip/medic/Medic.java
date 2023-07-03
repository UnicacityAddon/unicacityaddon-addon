package com.rettichlp.unicacityaddon.base.config.equip.medic;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface Medic {

    ConfigProperty<String> axe();

    ConfigProperty<String> bandage();

    ConfigProperty<String> extinguisher();

    ConfigProperty<String> helmet();

    ConfigProperty<String> pills();

    ConfigProperty<String> syringe();
}