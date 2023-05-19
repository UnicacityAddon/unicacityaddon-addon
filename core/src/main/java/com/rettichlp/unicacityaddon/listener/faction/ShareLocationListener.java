package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.sloc.DefaultSlocSetting;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/ShareLocationCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ShareLocationListener {

    private final UnicacityAddon unicacityAddon;

    public ShareLocationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        AddonPlayer p = this.unicacityAddon.player();
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

        Component hoverMessage = Message.getBuilder().of(String.valueOf(posX)).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of(String.valueOf(posY)).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of(String.valueOf(posZ)).color(ColorCode.AQUA).advance()
                .createComponent();

        Map.Entry<Double, NaviPoint> doubleNaviPointEntry = this.unicacityAddon.services().navigationService().getNearestNaviPoint(posX, posY, posZ);

        String navipointString;
        if (doubleNaviPointEntry.getValue() == null) {
            navipointString = "unbekannter Ort";
            p.sendErrorMessage("Navipunkte wurden nicht geladen. Versuche /syncdata um diese neu zu laden!");
        } else {
            navipointString = doubleNaviPointEntry.getValue().getName().replace("-", " ");
        }

        p.sendMessage(this.unicacityAddon.configuration().slocSetting().sloc().getOrDefault(DefaultSlocSetting.SLOC)
                .replace("&", "§")
                .replace("%sender%", senderName)
                .replace("%navipoint%", navipointString)
                .replace("%distance%", String.valueOf((int) p.getPosition().distance(new FloatVector3(posX, posY, posZ)))));

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