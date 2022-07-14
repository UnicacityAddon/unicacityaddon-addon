package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.labymod.api.events.MessageReceiveEvent;

import java.util.regex.Matcher;

@UCEventLabymod(event = "MessageReceiveEvent")
public class ATMInfoEventHandler implements MessageReceiveEvent {

    @Override
    public boolean onReceive(String s, String s1) {
        if (!ConfigElements.getEventATMInfo()) return false;

        Matcher kontoauszugMatcher = PatternHandler.KONTOAUSZUG_PATTERN.matcher(s1);
        if (kontoauszugMatcher.find()) AbstractionLayer.getPlayer().sendChatMessage("/atminfo");
        return false;
    }
}