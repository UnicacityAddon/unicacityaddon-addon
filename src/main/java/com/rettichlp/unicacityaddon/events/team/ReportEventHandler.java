package com.rettichlp.unicacityaddon.events.team;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCEvent
public class ReportEventHandler {

    private static final Timer t = new Timer();
    private long lastExecution = -1;
    private boolean isReport = false;

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        String formattedText = e.getMessage().getFormattedText();

        if (PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(msg).find()) {
            isReport = true;

            if (!ConfigElements.getReportGreeting().isEmpty()) return;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastExecution > 1000L) {
                        AbstractionLayer.getPlayer().sendChatMessage(ConfigElements.getReportGreeting());
                        lastExecution = System.currentTimeMillis();
                    }
                }
            }, 1000);
            return;
        }

        if (PatternHandler.REPORT_END_PATTERN.matcher(msg).find()) {
            isReport = false;
            return;
        }

        if (formattedText.startsWith(ColorCode.DARK_PURPLE.getCode()) && isReport) {
            e.setMessage(Message.getBuilder()
                    .add(ConfigElements.getReportPrefix())
                    .add(formattedText)
                    .createComponent());
        }
    }
}