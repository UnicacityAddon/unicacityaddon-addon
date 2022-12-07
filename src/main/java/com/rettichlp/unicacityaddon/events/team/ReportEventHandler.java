package com.rettichlp.unicacityaddon.events.team;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
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
        UPlayer p = AbstractionLayer.getPlayer();
        ITextComponent msg = e.getMessage();
        String formattedMsg = msg.getFormattedText();
        String unformattedMsg = msg.getUnformattedText();

        if (PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(unformattedMsg).find()) {
            isReport = true;

            if (!ConfigElements.getReportGreeting().isEmpty()) return;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastExecution > 1000L) {
                        p.sendChatMessage(ConfigElements.getReportGreeting());
                        lastExecution = System.currentTimeMillis();
                    }
                }
            }, 1000);
            return;
        }

        if (PatternHandler.REPORT_END_PATTERN.matcher(unformattedMsg).find()) {
            isReport = false;
            return;
        }

        if (formattedMsg.startsWith(ColorCode.DARK_PURPLE.getCode()) && isReport) {
            e.setMessage(Message.getBuilder()
                    .add(ConfigElements.getReportPrefix().replaceAll("&", "§"))
                    .add(formattedMsg)
                    .createComponent());
        }

        if (PatternHandler.REPORT_PATTERN.matcher(unformattedMsg).find()) {
            p.playSound(SoundRegistry.REPORT_SOUND, 1, 1);
        }
    }
}