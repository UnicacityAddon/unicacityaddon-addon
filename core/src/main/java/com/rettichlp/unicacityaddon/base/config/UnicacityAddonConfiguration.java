package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.equip.EquipConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.CommandConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.PasswordConfiguration;
import com.rettichlp.unicacityaddon.base.config.message.MessageConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUseConfiguration;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocConfiguration;
import com.rettichlp.unicacityaddon.base.config.tablist.TabListConfiguration;
import com.rettichlp.unicacityaddon.base.config.teamspeak.TeamSpeakConfiguration;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface UnicacityAddonConfiguration {

    ConfigProperty<Boolean> enabled();

    HotkeyConfiguration hotkey();

    // nametags

    NameTagConfiguration nametag();

    // faction

    ReinforcementConfiguration reinforcement();

    SlocConfiguration sloc();

    EquipConfiguration equip();

    OwnUseConfiguration ownUse();

    // message

    MessageConfiguration message();

    // join

    PasswordConfiguration password();

    CommandConfiguration command();

    ConfigProperty<Boolean> texturePack();

    // automation

    ATMConfiguration atm();

    ConfigProperty<Boolean> bombScreenshot();

    ConfigProperty<Boolean> carRoute();

    ConfigProperty<Integer> aBuyAmount();

    // other

    TeamSpeakConfiguration teamspeak();

    TabListConfiguration tablist();

    ConfigProperty<Boolean> despawnTime();

//    SoundSetting soundSetting();

    // debug

    ConfigProperty<Boolean> debug();
}