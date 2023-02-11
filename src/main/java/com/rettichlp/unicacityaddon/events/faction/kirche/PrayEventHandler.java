package com.rettichlp.unicacityaddon.events.faction.kirche;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class PrayEventHandler {

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        Matcher prayingStartMatcher = PatternHandler.PRAYING_START_PATTERN.matcher(msg);
        if (prayingStartMatcher.find()) {
            String name = prayingStartMatcher.group("name");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/beten " + name);
                }
            }, TimeUnit.SECONDS.toMillis(15));
        }
    }
}