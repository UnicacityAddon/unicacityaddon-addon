package com.rettichlp.unicacityaddon.base.config.hotkey;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface HotkeySetting {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Key> acceptAd();

    ConfigProperty<Key> declineAd();

    ConfigProperty<Key> acceptReport();

    ConfigProperty<Key> cancelReport();

    ConfigProperty<Key> aDuty();

    ConfigProperty<Key> aDutySilent();

    ConfigProperty<Key> reinforcementFaction();

    ConfigProperty<Key> reinforcementAlliance();

    ConfigProperty<Key> publicChannel();
}