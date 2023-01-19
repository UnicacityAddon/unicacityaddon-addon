package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.modules.TimerModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class TimerEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher fbiHackStartedMatcher = PatternHandler.TIMER_FBI_HACK_START_PATTERN.matcher(msg);
        if (fbiHackStartedMatcher.find()) {
            TimerModule.startTimer(Integer.parseInt(fbiHackStartedMatcher.group(1)), true);
            return;
        }

        Matcher timerGraveyardStartMatcher = PatternHandler.TIMER_GRAVEYARD_START_PATTERN.matcher(msg);
        if (timerGraveyardStartMatcher.find()) {
            TimerModule.startTimer(Integer.parseInt(timerGraveyardStartMatcher.group(1)), true);
            return;
        }

        Matcher timerJailStartMatcher = PatternHandler.TIMER_JAIL_START_PATTERN.matcher(msg);
        if (timerJailStartMatcher.find()) {
            TimerModule.startTimer(Integer.parseInt(timerJailStartMatcher.group(1)), true);
        }
    }
}
