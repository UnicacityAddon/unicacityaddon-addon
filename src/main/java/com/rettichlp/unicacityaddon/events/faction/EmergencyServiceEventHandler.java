package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.location.ServiceCallBox;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    public static int openServices = 0;
    public static int distanceToService = 0;
    public static BlockPos serviceAcceptPosition;

    private static final List<ServiceCallBox> activeEmergencyCallBoxList = new ArrayList<>();

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ITextComponent msg = e.getMessage();
        String unformattedMsg = msg.getUnformattedText();

        Matcher serviceArrivedMatcher = PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(unformattedMsg);
        if (serviceArrivedMatcher.find()) {
            p.playSound(SoundRegistry.SERVICE_SOUND, 1, 1);
            openServices++;

            if (ConfigElements.getServiceMessagesActivated()) {
                ITextComponent hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();
                e.setMessage(Message.getBuilder()
                        .of("Neuer Notruf").color(ColorCode.RED).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceArrivedMatcher.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceArrivedMatcher.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of(serviceArrivedMatcher.group(1)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceArrivedMatcher.group(1).replace("[UC]", ""))
                                .advance().space() // Notruf Sender
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceArrivedMatcher.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of("\"" + serviceArrivedMatcher.group(3) + "\"").color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceArrivedMatcher.group(1).replace("[UC]", ""))
                                .advance()
                        .createComponent());
            }

            return;
        }

        Matcher serviceRequeuedMatcher = PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(unformattedMsg);
        if (serviceRequeuedMatcher.find()) {
            openServices++;

            if (ConfigElements.getServiceMessagesActivated()) {
                ITextComponent hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();
                e.setMessage(Message.getBuilder()
                        .of("Neu geöffnet").color(ColorCode.GOLD).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceRequeuedMatcher.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceRequeuedMatcher.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of(serviceRequeuedMatcher.group(1)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceRequeuedMatcher.group(2).replace("[UC]", ""))
                                .advance().space() // Öffner
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceRequeuedMatcher.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of(serviceRequeuedMatcher.group(2)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + serviceRequeuedMatcher.group(2).replace("[UC]", ""))
                                .advance().space() // Notruf sender
                        .createComponent());
            }

            return;
        }

        Matcher serviceAcceptedMatcher = PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(unformattedMsg);
        if (serviceAcceptedMatcher.find()) {
            openServices = openServices > 0 ? openServices - 1 : 0;

            if (serviceAcceptedMatcher.group(1).equals(p.getName())) {
                distanceToService = Integer.parseInt(serviceAcceptedMatcher.group(3));
                serviceAcceptPosition = p.getPosition();
            }

            if (ConfigElements.getServiceMessagesActivated())
                e.setMessage(Message.getBuilder()
                        .of("Angenommen").color(ColorCode.GREEN).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceAcceptedMatcher.group(1)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceAcceptedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceAcceptedMatcher.group(3) + "m").color(ColorCode.DARK_RED).advance()
                        .createComponent());
            return;
        }

        Matcher serviceDeletedMatcher = PatternHandler.SERVICE_DELETED_PATTERN.matcher(unformattedMsg);
        if (serviceDeletedMatcher.find()) {
            openServices = openServices > 0 ? openServices - 1 : 0;

            e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.BLUE).bold().advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceDeletedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Löscher
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceDeletedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Service sender
            return;
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(unformattedMsg);
        if (serviceOverviewMatcher.find()) {
            String openServicesString = serviceOverviewMatcher.group(1);
            openServices = Integer.parseInt(openServicesString);
            return;
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(unformattedMsg);
        if (serviceCallBoxMatcher.find()) {
            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(serviceCallBoxMatcher.group(2));
            if (serviceCallBox != null) {
                activeEmergencyCallBoxList.add(serviceCallBox);
                e.setMessage(Message.getBuilder()
                        .add(msg.getFormattedText()).space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Unterwegs - " + serviceCallBox.getDistance(p.getPosition()) + "m").color(ColorCode.RED)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs").color(ColorCode.RED).advance().createComponent())
                        .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }
            return;
        }

        Matcher serviceLoactionMatcher = PatternHandler.SERVICE_LOCATION_PATTERN.matcher(unformattedMsg);
        if (serviceLoactionMatcher.find() && ConfigElements.getServiceMessagesActivated()) {
            e.setMessage(Message.getBuilder()
                    .of("➥").color(ColorCode.GRAY).advance().space()
                    .of(serviceLoactionMatcher.group(1)).color(ColorCode.DARK_RED).advance()
                    .createComponent());
            return;
        }

        Matcher serviceLocationOneNearestMatcher = PatternHandler.SERVICE_LOCATION_ONE_NEAREST_PATTERN.matcher(unformattedMsg);
        if (serviceLocationOneNearestMatcher.find() && ConfigElements.getServiceMessagesActivated()) { // one nearest person
            e.setMessage(Message.getBuilder()
                    .of("➥").color(ColorCode.GRAY).advance().space()
                    .of(serviceLocationOneNearestMatcher.group(1)).color(ColorCode.DARK_RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceLocationOneNearestMatcher.group(2) + " (" + serviceLocationOneNearestMatcher.group(3) + ")").color(ColorCode.DARK_RED).advance()
                    .createComponent());
            return;
        }

        Matcher serviceLocationTwoNearestMatcher = PatternHandler.SERVICE_LOCATION_TWO_NEAREST_PATTERN.matcher(unformattedMsg);
        if (serviceLocationTwoNearestMatcher.find() && ConfigElements.getServiceMessagesActivated()) {
            e.setMessage(Message.getBuilder()
                    .of("➥").color(ColorCode.GRAY).advance().space()
                    .of(serviceLocationTwoNearestMatcher.group(1)).color(ColorCode.DARK_RED).advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceLocationTwoNearestMatcher.group(2) + " (" + serviceLocationTwoNearestMatcher.group(3) + ")").color(ColorCode.DARK_RED).advance().space()
                    .of("|").color(ColorCode.GRAY).advance().space()
                    .of(serviceLocationTwoNearestMatcher.group(5) + " (" + serviceLocationTwoNearestMatcher.group(6) + ")").color(ColorCode.DARK_RED).advance()
                    .createComponent());
            return;
        }

        Matcher serviceBlockedMatcher = PatternHandler.SERVICE_BLOCKED_PATTERN.matcher(unformattedMsg);
        if (serviceBlockedMatcher.find() && ConfigElements.getServiceMessagesActivated()) {
            e.setMessage(Message.getBuilder().of("Blockiert").color(ColorCode.BLUE).bold().advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceBlockedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Blockierer
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceBlockedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
            return;
        }

        Matcher serviceUnblockedMatcher = PatternHandler.SERVICE_UNBLOCKED_PATTERN.matcher(unformattedMsg);
        if (serviceUnblockedMatcher.find() && ConfigElements.getServiceMessagesActivated()) {
            e.setMessage(Message.getBuilder().of("Entblockt").color(ColorCode.BLUE).bold().advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceUnblockedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Entblocker
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceUnblockedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
            return;
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(unformattedMsg).find()) {
            openServices = 0;
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(unformattedMsg).find()) {
            FileManager.DATA.setServiceCount(FileManager.DATA.getServiceCount() + 1);
            distanceToService = 0;
            APIRequest.sendStatisticAddRequest(StatisticType.SERVICE);
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
        Optional<ServiceCallBox> serviceCallBoxOptional = activeEmergencyCallBoxList.stream()
                .filter(serviceCallBox -> serviceCallBox.getNaviCommand().equals(e.getMessage()))
                .findAny();

        if (serviceCallBoxOptional.isPresent()) {
            ServiceCallBox serviceCallBox = serviceCallBoxOptional.get();
            AbstractionLayer.getPlayer().sendChatMessage("/f ➡ Unterwegs zur Notrufsäule (" + serviceCallBox.getLocationName() + ")");
            activeEmergencyCallBoxList.remove(serviceCallBox);
        }
    }
}