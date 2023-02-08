package com.rettichlp.unicacityaddon.events.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class ChatLogReceiveChatEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (UnicacityAddon.isUnicacity() && !PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg).find() && !PatternHandler.CONTRACT_LIST_PATTERN.matcher(msg).find() && !PatternHandler.WANTED_LIST_PATTERN.matcher(msg).find())
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[CHAT] " + msg);
    }
}