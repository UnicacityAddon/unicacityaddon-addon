package com.rettichlp.unicacityaddon.listener.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogSendChatListener {

    private final UnicacityAddon unicacityAddon;

    public ChatLogSendChatListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();

        if (this.unicacityAddon.services().utilService().isUnicacity() && !msg.equalsIgnoreCase("/chatlog"))
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[SELF] " + msg);
    }
}