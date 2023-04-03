package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface NameTagSetting {

    ConfigProperty<Boolean> factionInfo();

    FactionNameTagSetting factionNameTagSetting();

    AllianceFactionNameTagSetting allianceFactionNameTagSetting();

    StreetwarNameTagSetting streetwarNameTagSetting();

    ConfigProperty<Boolean> houseBan();

    ConfigProperty<Boolean> duty();

    SpecificNameTagSetting specificNameTagSetting();

    ConfigProperty<Boolean> corpse();

    ConfigProperty<Boolean> noPushInfo();
}