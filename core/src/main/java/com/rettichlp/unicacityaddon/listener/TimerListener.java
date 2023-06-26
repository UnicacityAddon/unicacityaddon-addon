package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ShutdownJailCommand;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class TimerListener {

    public static boolean isJail = false;

    private final UnicacityAddon unicacityAddon;

    public TimerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher fbiHackStartedMatcher = PatternHandler.TIMER_FBI_HACK_START_PATTERN.matcher(msg);
        if (fbiHackStartedMatcher.find()) {
            this.unicacityAddon.services().file().data().setTimer(Integer.parseInt(fbiHackStartedMatcher.group(1)));
            return;
        }

        Matcher timerGraveyardStartMatcher = PatternHandler.TIMER_GRAVEYARD_START_PATTERN.matcher(msg);
        if (timerGraveyardStartMatcher.find()) {
            ReviveListener.isDead = true;
            this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.DEATH);

            if (!isJail) {
                int seconds = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(timerGraveyardStartMatcher.group(1)));
                this.unicacityAddon.services().file().data().setTimer(seconds);
            }

            return;
        }

        Matcher timerJailStartMatcher = PatternHandler.TIMER_JAIL_START_PATTERN.matcher(msg);
        if (timerJailStartMatcher.find()) {
            isJail = true;
            int seconds = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(timerJailStartMatcher.group(1)));
            this.unicacityAddon.services().file().data().setTimer(seconds);
            return;
        }

        Matcher jailModifyMatcher = PatternHandler.TIMER_JAIL_MODIFY_PATTERN.matcher(msg);
        if (jailModifyMatcher.find()) {
            this.unicacityAddon.services().file().data().setTimer(this.unicacityAddon.services().file().data().getTimer() - Integer.parseInt(jailModifyMatcher.group(1)) * 60);
            return;
        }

        Matcher jailFinishMatcher = PatternHandler.TIMER_JAIL_FINISH_PATTERN.matcher(msg);
        if (jailFinishMatcher.find()) {
            isJail = false;
            this.unicacityAddon.services().file().data().setTimer(0);

            if (ShutdownJailCommand.shutdownJail)
                this.unicacityAddon.services().util().shutdownPC();
        }
    }
}
