package com.rettichlp.unicacityaddon.base.config.equip.setting;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface MedicSetting {

    ConfigProperty<String> axe();

    ConfigProperty<String> bandage();

    ConfigProperty<String> extinguisher();

    ConfigProperty<String> helmet();

    ConfigProperty<String> pills();

    ConfigProperty<String> syringe();
}