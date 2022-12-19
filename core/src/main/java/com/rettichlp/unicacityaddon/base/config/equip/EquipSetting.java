package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.setting.BadFactionSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.HitmanSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.MedicSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.NewsSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.StateSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.TerrorSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface EquipSetting {

    ConfigProperty<Integer> water();

    ConfigProperty<Integer> bread();

    ConfigProperty<Integer> donut();

    ConfigProperty<Integer> mask();

    ConfigProperty<Integer> lkev();

    ConfigProperty<Integer> skev();

    ConfigProperty<Integer> pepperSpray();

    ConfigProperty<Integer> pistol();

    ConfigProperty<Integer> mp5();

    ConfigProperty<Integer> sniper();

    StateSetting stateSetting();

    BadFactionSetting badFactionSetting();

    MedicSetting medicSetting();

    NewsSetting newsSetting();

    HitmanSetting hitmanSetting();

    TerrorSetting terrorSetting();
}