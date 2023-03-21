package com.rettichlp.unicacityaddon.base.config.equip;

import com.rettichlp.unicacityaddon.base.config.equip.setting.BadFactionSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.HitmanSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.MedicSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.NewsSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.StateSetting;
import com.rettichlp.unicacityaddon.base.config.equip.setting.TerrorSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface EquipSetting {

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

    StateSetting stateSetting();

    BadFactionSetting badFactionSetting();

    MedicSetting medicSetting();

    NewsSetting newsSetting();

    HitmanSetting hitmanSetting();

    TerrorSetting terrorSetting();
}