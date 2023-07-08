package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;

/**
 * @author RettichLP
 */
@UCEvent
public class LabyConnectListener {

    private final UnicacityAddon unicacityAddon;

    public LabyConnectListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(LabyConnectStateUpdateEvent e) {
        if (e.state().equals(LabyConnectState.LOGIN)) {
            this.unicacityAddon.api().sync(this.unicacityAddon.player());
        }
    }
}