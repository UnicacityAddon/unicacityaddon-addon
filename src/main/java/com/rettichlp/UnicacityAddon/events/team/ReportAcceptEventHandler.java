package com.rettichlp.UnicacityAddon.events.team;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.ModifyBlacklistCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCEvent
public class ReportAcceptEventHandler {

    private static final Timer t = new Timer();
    private long lastExecution = -1;

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        if (!PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(e.getMessage().getUnformattedText()).find()) return false;

        if (ConfigElements.getReportGreeting().isEmpty()) return false;

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - lastExecution < 1000L) {
                    AbstractionLayer.getPlayer().sendChatMessage(ConfigElements.getReportGreeting());
                    lastExecution = System.currentTimeMillis();
                }
            }
        }, 1000);

        return false;
    }
}
