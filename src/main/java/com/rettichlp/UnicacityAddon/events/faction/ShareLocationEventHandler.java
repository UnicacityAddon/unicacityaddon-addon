package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
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
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
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

        ITextComponent hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posY).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posZ).color(ColorCode.AQUA).advance()
                .createComponent();

        p.sendMessageAsString(ConfigElements.getPatternSloc()
                .replace("&", "§")
                .replace("%sender%", senderName)
                .replace("%x%", String.valueOf(posX))
                .replace("%y%", String.valueOf(posY))
                .replace("%z%", String.valueOf(posZ)));

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Route Anzeigen")
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                    .color(ColorCode.RED).advance()
                .createComponent());
        e.setCanceled(true);
        return false;
    }
}
