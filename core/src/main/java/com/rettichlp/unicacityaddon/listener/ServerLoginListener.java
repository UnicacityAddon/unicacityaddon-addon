package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerLoginEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ServerLoginListener {

    private final UnicacityAddon unicacityAddon;

    public ServerLoginListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onServerLogin(ServerLoginEvent e) {
        ServerData serverData = e.serverData();
        if (serverData.address().matches("unicacity.de", 25565, true)) {
            MobileListener.activeCommunicationsCheck = true;
        }
    }
}