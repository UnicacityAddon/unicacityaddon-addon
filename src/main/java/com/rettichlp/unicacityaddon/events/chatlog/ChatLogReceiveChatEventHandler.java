package com.rettichlp.unicacityaddon.events.chatlog;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ChatLogCommand;
import net.labymod.api.events.MessageReceiveEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLogReceiveChatEventHandler implements MessageReceiveEvent {

    @Override
    public boolean onReceive(String s, String s1) {
        if (UnicacityAddon.isUnicacity() && !PatternHandler.BLACKLIST_LIST_PATTERN.matcher(s1).find() && !PatternHandler.CONTRACT_LIST_PATTERN.matcher(s1).find() && !PatternHandler.WANTED_LIST_PATTERN.matcher(s1).find())
            ChatLogCommand.chatLogMap.put(System.currentTimeMillis(), "[CHAT] " + s1);
        return false;
    }
}