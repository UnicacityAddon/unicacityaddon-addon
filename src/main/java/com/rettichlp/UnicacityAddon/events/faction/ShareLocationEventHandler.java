package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class ShareLocationEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher shareLocationMatcher = PatternHandler.SHARE_LOCATION_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (!shareLocationMatcher.find()) return false;
        if (!shareLocationMatcher.group(2).contains(p.getName())) {
            e.setCanceled(true);
            return false;
        }
        String senderName = shareLocationMatcher.group(1);

        int posX = Integer.parseInt(shareLocationMatcher.group(3));
        int posY = Integer.parseInt(shareLocationMatcher.group(4));
        int posZ = Integer.parseInt(shareLocationMatcher.group(5));
        Message.Builder builder = Message.getBuilder();

        ITextComponent hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posY).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posZ).color(ColorCode.AQUA).advance()
                .createComponent();

        p.sendMessage(builder.of("Position!").color(ColorCode.RED).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(senderName).color(ColorCode.AQUA).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(""+posX).color(ColorCode.AQUA).advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(""+posY).color(ColorCode.AQUA).advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(""+posZ).color(ColorCode.AQUA).advance().space().createComponent());

        builder = Message.getBuilder();

        p.sendMessage(builder.of("»").color(ColorCode.GRAY).advance().space()
                        .of("Route Anzeigen")
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                        .color(ColorCode.RED).advance().createComponent());
        e.setCanceled(true);
        return false;
    }
}
