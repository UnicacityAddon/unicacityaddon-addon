package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.reinforcement.DefaultReinforcementSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.ReinforcementType;
import com.rettichlp.unicacityaddon.base.models.NaviPointEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import com.rettichlp.unicacityaddon.events.TickEventHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
@NoArgsConstructor
public class ReinforcementEventHandler {

    private static Reinforcement lastReinforcement;
    public static int activeReinforcement = -1;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        UPlayer p = AbstractionLayer.getPlayer();
        ChatMessage chatMessage = e.chatMessage();
        String unformattedMsg = chatMessage.getPlainText();

        Matcher reinforcementMatcher = PatternHandler.REINFORCEMENT_PATTERN.matcher(unformattedMsg);
        if (reinforcementMatcher.find()) {
            String fullName = reinforcementMatcher.group(1);
            String name = reinforcementMatcher.group(2);
            String[] splitFormattedMsg = chatMessage.getFormattedText().split(":");

            int posX = Integer.parseInt(reinforcementMatcher.group(3));
            int posY = Integer.parseInt(reinforcementMatcher.group(4));
            int posZ = Integer.parseInt(reinforcementMatcher.group(5));

            int distance = (int) p.getPosition().distance(new FloatVector3(posX, posY, posZ));

            boolean dChat = splitFormattedMsg[0].contains(ColorCode.RED.getCode())
                    && splitFormattedMsg[1].contains(ColorCode.RED.getCode());

            String type = "Reinforcement!";
            if (lastReinforcement != null && name.equals(lastReinforcement.getIssuerName()) && System.currentTimeMillis() - lastReinforcement.getTime() < 1000) {
                type = lastReinforcement.getType().getMessage();
            }

            Component hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.AQUA).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posY).color(ColorCode.AQUA).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posZ).color(ColorCode.AQUA).advance()
                    .createComponent();

            Map.Entry<Double, NaviPointEntry> nearestNaviPoint = NavigationUtils.getNearestNaviPoint(posX, posY, posZ);
            NaviPointEntry navipoint = nearestNaviPoint.getValue();

            String navipointString;
            if (navipoint == null) {
                navipointString = "unbekannter Ort";
                p.sendErrorMessage("Navipunkte wurden nicht geladen. Versuche /syncdata um diese neu zu laden!");
            } else {
                navipointString = navipoint.getName().replace("-", " ");
            }

            p.sendMessageAsString(UnicacityAddon.configuration.reinforcementSetting().reinforcement().getOrDefault(DefaultReinforcementSetting.REINFORCEMENT)
                    .replace("&", "§")
                    .replace("%type%", type)
                    .replace("%sender%", fullName)
                    .replace("%x%", String.valueOf(posX))
                    .replace("%y%", String.valueOf(posY))
                    .replace("%z%", String.valueOf(posZ))
                    .replace("%navipoint%", navipointString)
                    .replace("%distance%", String.valueOf(distance)));

            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of("Route Anzeigen").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                            .advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("Unterwegs").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs Nachricht absenden").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/reinforcement ontheway " + name + " " + posX + " " + posY + " " + posZ + (dChat ? " -d" : ""))
                            .advance()
                    .createComponent());

            e.setCancelled(true);
            return;
        }

        Matcher onTheWayMatcher = PatternHandler.ON_THE_WAY_PATTERN.matcher(unformattedMsg);
        if (onTheWayMatcher.find()) {
            String senderFullName = onTheWayMatcher.group(1);
            String reinforcementSenderName = onTheWayMatcher.group(3);
            String distance = onTheWayMatcher.group(4);

            p.sendMessageAsString(UnicacityAddon.configuration.reinforcementSetting().answer().getOrDefault(DefaultReinforcementSetting.ANSWER)
                    .replace("&", "§")
                    .replace("%sender%", senderFullName)
                    .replace("%target%", reinforcementSenderName)
                    .replace("%distance%", String.valueOf(distance)));

            e.setCancelled(true);
            return;
        }

        for (ReinforcementType type : ReinforcementType.values()) {
            Pattern pattern = type.getPattern();
            if (pattern == null)
                continue;

            Matcher matcher = pattern.matcher(unformattedMsg);
            if (!matcher.find())
                continue;

            String name = matcher.group(1);

            lastReinforcement = new Reinforcement(name, type);
            e.setCancelled(true);
            return;
        }
    }

    @Subscribe
    public void onClientMessageSend(ChatMessageSendEvent e) {
        if (UnicacityAddon.configuration.reinforcementSetting().screen().get() && e.getMessage().toLowerCase().startsWith("/reinforcement ontheway "))
            activeReinforcement = TickEventHandler.currentTick;
    }

    private class Reinforcement {

        private final String issuerName;
        private final ReinforcementType reinforcementType;
        private final long time;

        Reinforcement(String issuerName, ReinforcementType reinforcementType) {
            this.issuerName = issuerName;
            this.reinforcementType = reinforcementType;
            this.time = System.currentTimeMillis();
        }

        public String getIssuerName() {
            return issuerName;
        }

        public ReinforcementType getType() {
            return reinforcementType;
        }

        public long getTime() {
            return time;
        }
    }
}