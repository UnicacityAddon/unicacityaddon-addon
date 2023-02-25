package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerLoginEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ServerLoginEventHandler {

    private final UnicacityAddon unicacityAddon;

    public ServerLoginEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onServerLogin(ServerLoginEvent e) {
        ServerData serverData = e.serverData();
        if (serverData.address().matches("unicacity.de", 25565, true)) {
            MobileEventHandler.activeCommunicationsCheck = true;
            FactionManager.getInstance().getFactionData();
        }
    }
}