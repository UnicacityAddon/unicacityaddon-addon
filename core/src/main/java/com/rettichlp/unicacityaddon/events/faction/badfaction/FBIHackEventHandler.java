package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class FBIHackEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        Matcher fbiHackStartedMatcher = PatternHandler.FBI_HACK_STARTED_PATTERN.matcher(e.chatMessage().getPlainText());

//        if (fbiHackStartedMatcher.find())
//            FBIHackModule.startCountdown(Integer.parseInt(fbiHackStartedMatcher.group(1)));
    }
}
