package com.rettichlp.unicacityaddon.events.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.events.MessageReceiveEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogReceiveChatEventHandler implements MessageReceiveEvent {

    @Override
    public boolean onReceive(String s, String s1) {
        if (UnicacityAddon.isUnicacity())
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[CHAT] " + s);
        return false;
    }
}