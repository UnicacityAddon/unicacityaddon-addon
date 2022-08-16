package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    @SubscribeEvent public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg).find())
            EmergencyServiceModule.currentCount++;
        if (PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg).find())
            EmergencyServiceModule.currentCount++;
        if (PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg).find() && EmergencyServiceModule.currentCount > 0)
            EmergencyServiceModule.currentCount--;
        if (PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg).find() && EmergencyServiceModule.currentCount > 0)
            EmergencyServiceModule.currentCount--;
        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(msg).find())
            EmergencyServiceModule.currentCount = 0;

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(msg);
        if (serviceOverviewMatcher.find()) {
            String openServices = serviceOverviewMatcher.group(1);
            EmergencyServiceModule.currentCount = Integer.parseInt(openServices);
        }
        return false;
    }
}