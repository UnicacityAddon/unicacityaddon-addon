package com.rettichlp.unicacityaddon.listener;

import com.google.inject.Inject;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import com.rettichlp.unicacityaddon.UnicacityAddon;

public class ExampleGameTickListener {

    private final UnicacityAddon addon;

    @Inject
    private ExampleGameTickListener(UnicacityAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onGameTick(ChatReceiveEvent event) {
        String msg = event.chatMessage().getPlainText();

        UnicacityAddon.LOGGER.info("ChatReceiveEvent: " + msg);
    }
}
