package com.rettichlp.unicacityaddon.events.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogSendChatEventHandler {

    private final UnicacityAddon unicacityAddon;

    public ChatLogSendChatEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();

        if (UnicacityAddon.isUnicacity() && !msg.equalsIgnoreCase("/chatlog"))
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[SELF] " + msg);
    }
}