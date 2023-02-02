package com.rettichlp.unicacityaddon.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import com.rettichlp.unicacityaddon.UnicacityAddon;

public class ExampleGameTickListener {

    private final UnicacityAddon addon;

    public ExampleGameTickListener(UnicacityAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onGameTick(ChatReceiveEvent event) {
        String msg = event.chatMessage().getPlainText();

        UnicacityAddon.LOGGER.info("ChatReceiveEvent: " + msg);
    }
}
