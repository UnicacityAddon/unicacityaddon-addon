package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.location.ServiceCallBox;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.hudwidgets.EmergencyServiceHudWidget;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceListener {

    public static int openServices = 0;
    public static int distanceToService = 0;
    public static FloatVector3 serviceAcceptPosition;

    private static final List<ServiceCallBox> activeEmergencyCallBoxList = new ArrayList<>();

    private final UnicacityAddon unicacityAddon;

    public EmergencyServiceListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        ChatMessage chatMessage = e.chatMessage();
        String msg = chatMessage.getPlainText();

        Matcher serviceArrivedMatcher = PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg);
        if (serviceArrivedMatcher.find()) {
            this.unicacityAddon.soundController().playServiceSound();

            openServices++;
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);

            if (this.unicacityAddon.configuration().factionMessageSetting().service().get()) {
                Component hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();
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

        Matcher serviceRequeuedMatcher = PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg);
        if (serviceRequeuedMatcher.find()) {
            this.unicacityAddon.soundController().playServiceSound();

            openServices++;
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);

            if (this.unicacityAddon.configuration().factionMessageSetting().service().get()) {
                Component hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();
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

        Matcher serviceAcceptedMatcher = PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg);
        if (serviceAcceptedMatcher.find()) {
            openServices = openServices > 0 ? openServices - 1 : 0;
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);

            if (serviceAcceptedMatcher.group(1).equals(p.getName())) {
                distanceToService = Integer.parseInt(serviceAcceptedMatcher.group(3));
                serviceAcceptPosition = p.getLocation();
            }

            if (this.unicacityAddon.configuration().factionMessageSetting().service().get())
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

        Matcher serviceDeletedMatcher = PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg);
        if (serviceDeletedMatcher.find()) {
            openServices = openServices > 0 ? openServices - 1 : 0;
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);

            e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.BLUE).bold().advance().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceDeletedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Löscher
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of(serviceDeletedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Service sender
            return;
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(msg);
        if (serviceOverviewMatcher.find()) {
            String openServicesString = serviceOverviewMatcher.group(1);
            openServices = Integer.parseInt(openServicesString);
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);
            return;
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(msg);
        if (serviceCallBoxMatcher.find()) {
            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(serviceCallBoxMatcher.group(2));
            if (serviceCallBox != null && p.getLocation() != null) {
                activeEmergencyCallBoxList.add(serviceCallBox);
                e.setMessage(Message.getBuilder()
                        .add(chatMessage.getFormattedText()).space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Unterwegs - " + serviceCallBox.getDistance(p.getLocation()) + "m").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs").color(ColorCode.RED).advance().createComponent())
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }
            return;
        }

        if (this.unicacityAddon.configuration().factionMessageSetting().service().get()) {
            Matcher serviceLoactionMatcher = PatternHandler.SERVICE_LOCATION_PATTERN.matcher(msg);
            if (serviceLoactionMatcher.find()) {
                e.setMessage(Message.getBuilder()
                        .of("➥").color(ColorCode.GRAY).advance().space()
                        .of(serviceLoactionMatcher.group(1)).color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            Matcher serviceLocationOneNearestMatcher = PatternHandler.SERVICE_LOCATION_ONE_NEAREST_PATTERN.matcher(msg);
            if (serviceLocationOneNearestMatcher.find()) { // one nearest person
                e.setMessage(Message.getBuilder()
                        .of("➥").color(ColorCode.GRAY).advance().space()
                        .of(serviceLocationOneNearestMatcher.group(1)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceLocationOneNearestMatcher.group(2) + " (" + serviceLocationOneNearestMatcher.group(3) + ")").color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            Matcher serviceLocationTwoNearestMatcher = PatternHandler.SERVICE_LOCATION_TWO_NEAREST_PATTERN.matcher(msg);
            if (serviceLocationTwoNearestMatcher.find()) {
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

            Matcher serviceBlockedMatcher = PatternHandler.SERVICE_BLOCKED_PATTERN.matcher(msg);
            if (serviceBlockedMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Blockiert").color(ColorCode.BLUE).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceBlockedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Blockierer
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceBlockedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
                return;
            }

            Matcher serviceUnblockedMatcher = PatternHandler.SERVICE_UNBLOCKED_PATTERN.matcher(msg);
            if (serviceUnblockedMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Entblockt").color(ColorCode.BLUE).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceUnblockedMatcher.group(2)).color(ColorCode.DARK_RED).advance().space() // Entblocker
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(serviceUnblockedMatcher.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
                return;
            }
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(msg).find()) {
            openServices = 0;
            EmergencyServiceHudWidget.textLine.updateAndFlush(openServices);
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.services().fileService().data().setServiceCount(this.unicacityAddon.services().fileService().data().getServiceCount() + 1);
            distanceToService = 0;
            this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.SERVICE);
        }
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        Optional<ServiceCallBox> serviceCallBoxOptional = activeEmergencyCallBoxList.stream()
                .filter(serviceCallBox -> serviceCallBox.getNaviCommand().equals(e.getMessage()))
                .findAny();

        if (serviceCallBoxOptional.isPresent()) {
            ServiceCallBox serviceCallBox = serviceCallBoxOptional.get();
            this.unicacityAddon.player().sendServerMessage("/f ➡ Unterwegs zur Notrufsäule (" + serviceCallBox.getLocationName() + ")");
            activeEmergencyCallBoxList.remove(serviceCallBox);
        }
    }
}