package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSlocSetting;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ShareLocationEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher shareLocationMatcher = PatternHandler.SHARE_LOCATION_PATTERN.matcher(e.chatMessage ().getPlainText());

        if (!shareLocationMatcher.find())
            return;
        if (!shareLocationMatcher.group(2).contains(p.getName())) {
            e.setCancelled(true);
            return;
        }
        String senderName = shareLocationMatcher.group(1);

        int posX = Integer.parseInt(shareLocationMatcher.group(3));
        int posY = Integer.parseInt(shareLocationMatcher.group(4));
        int posZ = Integer.parseInt(shareLocationMatcher.group(5));

        Component hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posY).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of("" + posZ).color(ColorCode.AQUA).advance()
                .createComponent();

        p.sendMessageAsString(UnicacityAddon.configuration.slocSetting().sloc().getOrDefault(DefaultSlocSetting.SLOC)
                .replace("&", "§")
                .replace("%sender%", senderName)
                .replace("%x%", String.valueOf(posX))
                .replace("%y%", String.valueOf(posY))
                .replace("%z%", String.valueOf(posZ)));

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Route Anzeigen").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                        .advance()
                .createComponent());
        e.setCancelled(true);
    }
}
