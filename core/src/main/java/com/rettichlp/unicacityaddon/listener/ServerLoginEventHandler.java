package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.network.server.ServerAddress;
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
        ServerAddress actualAddress = e.serverData().actualAddress();

        if (actualAddress.matches("unicacity.de", 25565, true)) {
            FactionManager.getInstance().getFactionData();
        }
    }
}