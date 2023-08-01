package com.rettichlp.unicacityaddon.base.config.hotkey;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface HotkeyConfiguration {

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Key> acceptAd();

    ConfigProperty<Key> declineAd();

    ConfigProperty<Key> acceptReport();

    ConfigProperty<Key> cancelReport();

    ConfigProperty<Key> publicChannel();

    ConfigProperty<Key> aBuy();

    ConfigProperty<Key> aEquip();

    ConfigProperty<Key> screenshot();
}