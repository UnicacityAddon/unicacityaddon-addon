package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.location.ServiceCallBox;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    private static final List<ServiceCallBox> activeEmergencyCallBoxList = new ArrayList<>();

    private final UnicacityAddon unicacityAddon;

    public EmergencyServiceEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        ChatMessage chatMessage = e.chatMessage();
        String msg = chatMessage.getPlainText();

        if (PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg).find()) {
//            // TODO: 10.12.2022 p.playSound(SoundRegistry.SERVICE_SOUND, 1, 1);
            FileManager.DATA.addServiceCount(1);
            return;
        }

        if (PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg).find()) {
            FileManager.DATA.addServiceCount(1);
            return;
        }

        if (PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg).find()) {
            FileManager.DATA.removeServiceCount(1);
            return;
        }

        if (PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg).find()) {
            FileManager.DATA.removeServiceCount(1);
            return;
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setServiceCount(0);
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setServiceCount(FileManager.DATA.getServiceCount() + 1);
            APIRequest.sendStatisticAddRequest(StatisticType.SERVICE);
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(msg);
        if (serviceOverviewMatcher.find()) {
            String openServices = serviceOverviewMatcher.group(1);
            FileManager.DATA.setServiceCount(Integer.parseInt(openServices));
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(msg);
        if (serviceCallBoxMatcher.find()) {
            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(serviceCallBoxMatcher.group(2));
            if (serviceCallBox != null) {
                activeEmergencyCallBoxList.add(serviceCallBox);
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

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        Optional<ServiceCallBox> serviceCallBoxOptional = activeEmergencyCallBoxList.stream()
                .filter(serviceCallBox -> serviceCallBox.getNaviCommand().equals(e.getMessage()))
                .findAny();

        if (serviceCallBoxOptional.isPresent()) {
            ServiceCallBox serviceCallBox = serviceCallBoxOptional.get();
            UnicacityAddon.PLAYER.sendServerMessage("/f ➡ Unterwegs zur Notrufsäule (" + serviceCallBox.getLocationName() + ")");
            activeEmergencyCallBoxList.remove(serviceCallBox);
        }
    }
}