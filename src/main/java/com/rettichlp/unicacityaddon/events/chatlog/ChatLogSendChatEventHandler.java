package com.rettichlp.unicacityaddon.events.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.events.MessageSendEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogSendChatEventHandler implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {
        if (UnicacityAddon.isUnicacity())
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[SELF] " + s);
        return false;
    }
}