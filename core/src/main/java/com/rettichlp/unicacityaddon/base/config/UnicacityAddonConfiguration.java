package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATM;
import com.rettichlp.unicacityaddon.base.config.equip.Equip;
import com.rettichlp.unicacityaddon.base.config.hotkey.Hotkey;
import com.rettichlp.unicacityaddon.base.config.join.Command;
import com.rettichlp.unicacityaddon.base.config.join.Password;
import com.rettichlp.unicacityaddon.base.config.message.Faction;
import com.rettichlp.unicacityaddon.base.config.message.Report;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTag;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUse;
import com.rettichlp.unicacityaddon.base.config.reinforcement.Reinforcement;
import com.rettichlp.unicacityaddon.base.config.sloc.Sloc;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface UnicacityAddonConfiguration {

    ConfigProperty<Boolean> enabled();

    Hotkey hotkey();

    // nametags

    NameTag nameTag();

    // faction

    Reinforcement reinforcement();

    Sloc sloc();

    Faction faction();

    Equip equip();

    OwnUse ownUse();

    // message

    Report report();

    // join

    Password password();

    Command command();

    ConfigProperty<Boolean> texturePack();

    // automation

    ATM atm();

    ConfigProperty<Boolean> bombScreenshot();

    ConfigProperty<Boolean> carRoute();

    ConfigProperty<Integer> aBuyAmount();

    // teamspeak

    ConfigProperty<Boolean> resolveAPIKey();

    ConfigProperty<String> tsApiKey();

    ConfigProperty<Boolean> tsNotificationPublic();

    ConfigProperty<Boolean> tsNotificationSupport();

    // other

    ConfigProperty<Boolean> orderedTablist();

    ConfigProperty<Boolean> despawnTime();

//    SoundSetting soundSetting();

    // debug

    ConfigProperty<Boolean> debug();
}