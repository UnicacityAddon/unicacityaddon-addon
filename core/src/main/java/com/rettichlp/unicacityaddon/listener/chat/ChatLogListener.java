package com.rettichlp.unicacityaddon.listener.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogListener {

    private final UnicacityAddon unicacityAddon;

    public ChatLogListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (this.unicacityAddon.utilService().isUnicacity() && !PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg).find() && !PatternHandler.CONTRACT_LIST_PATTERN.matcher(msg).find() && !PatternHandler.WANTED_LIST_PATTERN.matcher(msg).find())
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[CHAT] " + msg);
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();

        if (this.unicacityAddon.utilService().isUnicacity() && !msg.equalsIgnoreCase("/chatlog"))
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[SELF] " + msg);
    }
}