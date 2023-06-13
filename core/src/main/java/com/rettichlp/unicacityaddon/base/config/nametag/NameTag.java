package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.setting.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.Streetwar;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface NameTag {

    ConfigProperty<Boolean> factionInfo();

    Faction faction();

    Alliance alliance();

    Streetwar streetwar();

    ConfigProperty<Boolean> houseBan();

    ConfigProperty<Boolean> duty();

    Specific specific();

    ConfigProperty<Boolean> corpse();

    ConfigProperty<Boolean> noPushInfo();

    ConfigProperty<Boolean> addonTag();
}