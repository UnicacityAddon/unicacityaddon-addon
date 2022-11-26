package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.enums.StatisticType;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.location.ServiceCallBox;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.ServiceCountCommand;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg).find()) {
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg).find()) {
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(msg).find()) {
            EmergencyServiceModule.currentCount = 0;
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(msg).find()) {
            ServiceCountCommand.addService();
            APIRequest.sendStatisticAddRequest(StatisticType.SERVICE);
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(msg);
        if (serviceOverviewMatcher.find()) {
            String openServices = serviceOverviewMatcher.group(1);
            EmergencyServiceModule.currentCount = Integer.parseInt(openServices);
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(msg);
        if (serviceCallBoxMatcher.find()) {
            String playerName = serviceCallBoxMatcher.group(1);
            String naviPoint = serviceCallBoxMatcher.group(2);

            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(naviPoint);
            if (serviceCallBox != null) e.setMessage(Message.getBuilder()
                    .space().space()
                    .of("➡").color(ColorCode.DARK_GRAY).advance().space()
                    .of(playerName).color(ColorCode.DARK_RED).advance().space()
                    .of("»").color(ColorCode.DARK_GRAY).advance().space()
                    .of(naviPoint).color(ColorCode.DARK_RED).advance().space()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Nachricht")
                    .color(ColorCode.RED)
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/f ➡ Unterwegs zur Notrufsäule (" + naviPoint + ")")
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("Unterwegs-Nachricht senden").color(ColorCode.RED).advance()
                            .createComponent())
                    .advance()
                    .of("|").color(ColorCode.DARK_GRAY).advance()
                    .of("Route (" + serviceCallBox.getDistance(p.getPosition()) + "m)")
                    .color(ColorCode.RED)
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("Route anzeigen").color(ColorCode.RED).advance()
                            .createComponent())
                    .advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
        }
    }
}