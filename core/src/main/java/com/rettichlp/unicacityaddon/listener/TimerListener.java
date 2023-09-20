package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.AutoFirstAidCommand;
import com.rettichlp.unicacityaddon.commands.ShutdownJailCommand;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class TimerListener {

    private boolean isJail = false;

    private final UnicacityAddon unicacityAddon;

    public TimerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        Matcher fbiHackStartedMatcher = PatternHandler.TIMER_FBI_HACK_START_PATTERN.matcher(msg);
        if (fbiHackStartedMatcher.find()) {
            this.unicacityAddon.fileService().data().setTimer(Integer.parseInt(fbiHackStartedMatcher.group(1)));
            return;
        }

        Matcher timerGraveyardStartMatcher = PatternHandler.TIMER_GRAVEYARD_START_PATTERN.matcher(msg);
        if (timerGraveyardStartMatcher.find()) {
            this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.DEATH);

            int duration = Integer.parseInt(timerGraveyardStartMatcher.group(1));

            if (!isJail) {
                int seconds = (int) TimeUnit.MINUTES.toSeconds(duration);
                this.unicacityAddon.fileService().data().setTimer(seconds);
            }

            if (duration == 20) {
                AutoFirstAidCommand.autoAcceptFirstAid = false;
                p.sendMessage(Message.getBuilder()
                        .info().space()
                        .of("Das UnicacityAddon nimmt Erste Hilfe").color(ColorCode.WHITE).advance().space()
                        .of("nicht").color(ColorCode.WHITE).bold().advance().space()
                        .of("automatisch an, da ein Hitman-Kill erkannt wurde. Klicke").color(ColorCode.WHITE).advance().space()
                        .of("hier").color(ColorCode.RED).underline()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Erste Hilfe automatisch annehmen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/autofirstaid on")
                                .advance().space()
                        .of("um die Funktion dennoch zu aktivieren.").color(ColorCode.WHITE).advance().space()
                        .createComponent());
            } else {
                AutoFirstAidCommand.autoAcceptFirstAid = true;
                p.sendMessage(Message.getBuilder()
                        .info().space()
                        .of("Das UnicacityAddon nimmt Erste Hilfe automatisch an. Klicke").color(ColorCode.WHITE).advance().space()
                        .of("hier").color(ColorCode.RED).underline()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Erste Hilfe nicht automatisch annehmen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/autofirstaid off")
                                .advance().space()
                        .of("um die Funktion zu deaktivieren.").color(ColorCode.WHITE).advance().space()
                        .createComponent());
            }

            return;
        }

        Matcher timerJailStartMatcher = PatternHandler.TIMER_JAIL_START_PATTERN.matcher(msg);
        if (timerJailStartMatcher.find()) {
            isJail = true;
            int seconds = (int) TimeUnit.MINUTES.toSeconds(Integer.parseInt(timerJailStartMatcher.group(1)));
            this.unicacityAddon.fileService().data().setTimer(seconds);
            return;
        }

        Matcher jailModifyMatcher = PatternHandler.TIMER_JAIL_MODIFY_PATTERN.matcher(msg);
        if (jailModifyMatcher.find()) {
            this.unicacityAddon.fileService().data().setTimer(this.unicacityAddon.fileService().data().getTimer() - Integer.parseInt(jailModifyMatcher.group(1)) * 60);
            return;
        }

        Matcher jailFinishMatcher = PatternHandler.TIMER_JAIL_FINISH_PATTERN.matcher(msg);
        if (jailFinishMatcher.find()) {
            isJail = false;
            this.unicacityAddon.fileService().data().setTimer(-1);

            if (ShutdownJailCommand.shutdownJail)
                this.unicacityAddon.utilService().shutdownPC();
        }
    }
}
