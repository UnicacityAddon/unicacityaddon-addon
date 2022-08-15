package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCEvent
public class ADropEventHandler {

    final Timer timer = new Timer();

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.DROP_TRANSPORT_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/droptransport");
                }
            }, TimeUnit.SECONDS.toMillis(10));
        }

        if (PatternHandler.DROP_DRINK_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/dropdrink");
                }
            }, TimeUnit.SECONDS.toMillis((long) 2.5));
        }

        return false;
    }
}
