package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.location.ServiceCallBox;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyCallBoxEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(msg);
        if (!serviceCallBoxMatcher.find()) return false;

        String playerName = serviceCallBoxMatcher.group(1);
        String naviPoint = serviceCallBoxMatcher.group(2);

        ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(naviPoint);
        if (serviceCallBox == null) return false;

        e.setMessage(Message.getBuilder()
                .space().space()
                .of("➡").color(ColorCode.DARK_GRAY).advance().space()
                .of(playerName).color(ColorCode.DARK_RED).advance().space()
                .of("»").color(ColorCode.DARK_GRAY).advance().space()
                .of(naviPoint).color(ColorCode.DARK_RED).advance().space()
                .of("➡").color(ColorCode.DARK_GRAY).advance().space()
                .of("Unterwegs (" + serviceCallBox.getDistance(p.getPosition()) + "m)")
                        .color(ColorCode.RED)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/f ➡ Unterwegs zur Notrufsäule (" + naviPoint + ")")
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Route anzeigen").color(ColorCode.RED).advance()
                                .createComponent())
                        .advance()
                .createComponent());

        return false;
    }
}