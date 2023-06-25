package com.rettichlp.unicacityaddon.base.config.nametag;

import com.rettichlp.unicacityaddon.base.config.nametag.alliance.Alliance;
import com.rettichlp.unicacityaddon.base.config.nametag.faction.Faction;
import com.rettichlp.unicacityaddon.base.config.nametag.specific.Specific;
import com.rettichlp.unicacityaddon.base.config.nametag.streetwar.Streetwar;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface NameTagConfiguration {

    ConfigProperty<Boolean> info();

    Faction faction();

    Alliance alliance();

    Streetwar streetwar();

    ConfigProperty<Boolean> houseban();

    ConfigProperty<Boolean> duty();

    Specific specific();

    ConfigProperty<Boolean> corpse();

    ConfigProperty<Boolean> afk();

    ConfigProperty<Boolean> team();
}