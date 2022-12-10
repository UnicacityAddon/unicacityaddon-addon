package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.location.ServiceCallBox;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.ServiceCountCommand;
import com.rettichlp.unicacityaddon.modules.EmergencyServiceModule;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    public static boolean messageCreationActive = false;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ChatMessage chatMessage = e.chatMessage();
        String unformattedMsg = chatMessage.getPlainText();

        if (PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(unformattedMsg).find()) {
            // TODO: 10.12.2022 p.playSound(SoundRegistry.SERVICE_SOUND, 1, 1);
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(unformattedMsg).find()) {
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(unformattedMsg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_DELETED_PATTERN.matcher(unformattedMsg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(unformattedMsg).find()) {
            EmergencyServiceModule.currentCount = 0;
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(unformattedMsg).find()) {
            ServiceCountCommand.addService();
            APIRequest.sendStatisticAddRequest(StatisticType.SERVICE);
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(unformattedMsg);
        if (serviceOverviewMatcher.find()) {
            String openServices = serviceOverviewMatcher.group(1);
            EmergencyServiceModule.currentCount = Integer.parseInt(openServices);
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(unformattedMsg);
        if (serviceCallBoxMatcher.find()) {
            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(serviceCallBoxMatcher.group(2));
            if (serviceCallBox != null) {
                messageCreationActive = true;
                e.setMessage(Message.getBuilder()
                        .add(chatMessage.getFormattedText())
                        .space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Unterwegs - " + serviceCallBox.getDistance(p.getPosition()) + "m").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs").color(ColorCode.RED).advance().createComponent())
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }
        }
    }
}