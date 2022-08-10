package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.PaydayModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
public class PaydayEventHandler {

    public static boolean isAfk = false;
    private int currentTick;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.ACCOUNT_AFK_TRUE_PATTERN.matcher(msg).find()) {
            isAfk = true;
            return false;
        }

        if (PatternHandler.ACCOUNT_AFK_FALSE_PATTERN.matcher(msg).find()) {
            isAfk = false;
            return false;
        }

        Matcher statsPaydayMatcher = PatternHandler.STATS_PAYDAY_PATTERN.matcher(msg);
        if (statsPaydayMatcher.find()) {
            PaydayModule.setTime(Integer.parseInt(statsPaydayMatcher.group(1)));
            return false;
        }

        return false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (isAfk || event.phase != TickEvent.Phase.END) return;

        if (currentTick++ != 1200) return;
        PaydayModule.addTime(1);
        currentTick = 0;
    }
}