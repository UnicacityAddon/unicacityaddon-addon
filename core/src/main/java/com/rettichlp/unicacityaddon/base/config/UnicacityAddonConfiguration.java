package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMConfiguration;
import com.rettichlp.unicacityaddon.base.config.drug.DrugConfiguration;
import com.rettichlp.unicacityaddon.base.config.equip.EquipConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.CommandConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.PasswordConfiguration;
import com.rettichlp.unicacityaddon.base.config.message.MessageConfiguration;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagConfiguration;
import com.rettichlp.unicacityaddon.base.config.playerlist.PlayerListConfiguration;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementConfiguration;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocConfiguration;
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

    DrugConfiguration drug();

    // message

    MessageConfiguration message();

    // join

    PasswordConfiguration password();

    CommandConfiguration command();

    ConfigProperty<Boolean> texturePack();

    ConfigProperty<Boolean> hitSound();

    // automation

    ATMConfiguration atm();

    ConfigProperty<Boolean> bombScreenshot();

    ConfigProperty<Boolean> carRoute();

    // other

    TeamSpeakConfiguration teamspeak();

    PlayerListConfiguration playerlist();

    ConfigProperty<Boolean> despawnTime();

    // debug

    ConfigProperty<Boolean> debug();

    ConfigProperty<Boolean> local();
}