package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class SMSEventHandler {

    public static boolean isActive = false;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        Matcher smsMatcher = PatternHandler.SMS_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (!smsMatcher.find()) return false;
        if (!PayDayEventHandler.isAfk) AbstractionLayer.getPlayer().sendChatMessage("/nummer " + smsMatcher.group(1));
        isActive = true;

        return false;
    }
}
