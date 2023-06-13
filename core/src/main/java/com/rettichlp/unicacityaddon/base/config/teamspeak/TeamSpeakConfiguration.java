package com.rettichlp.unicacityaddon.base.config.teamspeak;

import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface TeamSpeakConfiguration {

    ConfigProperty<Boolean> resolveAPIKey();

    ConfigProperty<String> tsApiKey();

    ConfigProperty<Boolean> tsNotificationPublic();

    ConfigProperty<Boolean> tsNotificationSupport();
}