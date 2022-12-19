package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface MedicSetting {

    ConfigProperty<Integer> axe();

    ConfigProperty<Integer> bandage();

    ConfigProperty<Integer> extinguisher();

    ConfigProperty<Integer> helmet();

    ConfigProperty<Integer> pills();

    ConfigProperty<Integer> syringe();
}