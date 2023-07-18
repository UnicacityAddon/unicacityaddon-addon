package com.rettichlp.unicacityaddon.listener.faction.kirche;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class PrayListener {

    private final UnicacityAddon unicacityAddon;

    public PrayListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        Matcher prayingStartMatcher = PatternHandler.PRAYING_START_PATTERN.matcher(e.chatMessage().getPlainText());
        if (prayingStartMatcher.find()) {
            String name = prayingStartMatcher.group("name");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/beten " + name);
                }
            }, TimeUnit.SECONDS.toMillis(15));
            return;
        }

        Matcher prayedForYouMatcher = PatternHandler.PRAYED_FOR_YOU_PATTERN.matcher(msg);
        if (prayedForYouMatcher.find()) {
            this.unicacityAddon.fileService().data().setTimer(this.unicacityAddon.fileService().data().getTimer() - 30);
        }
    }
}
