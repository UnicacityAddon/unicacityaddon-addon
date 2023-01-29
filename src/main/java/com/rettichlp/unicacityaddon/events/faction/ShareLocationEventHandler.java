package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ShareLocationEventHandler {

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher shareLocationMatcher = PatternHandler.SHARE_LOCATION_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (!shareLocationMatcher.find())
            return;
        if (!shareLocationMatcher.group(2).contains(p.getName())) {
            e.setCanceled(true);
            return;
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

        Map.Entry<Double, NaviPoint> doubleNaviPointEntry = NavigationUtils.getNearestNaviPoint(posX, posY, posZ);

        String navipointString;
        if (doubleNaviPointEntry.getValue() == null) {
            navipointString = "unbekannter Ort";
            p.sendErrorMessage("Navipunkte wurden nicht geladen. Versuche /syncdata um diese neu zu laden!");
        } else {
            navipointString = doubleNaviPointEntry.getValue().getName().replace("-", " ");
        }

        p.sendMessageAsString(ConfigElements.getPatternSloc()
                .replace("&", "§")
                .replace("%sender%", senderName)
                .replace("%navipoint%", navipointString)
                .replace("%distance%", String.valueOf((int) p.getPosition().getDistance(posX, posY, posZ))));

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Route Anzeigen").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                        .advance()
                .createComponent());
        e.setCanceled(true);
    }
}