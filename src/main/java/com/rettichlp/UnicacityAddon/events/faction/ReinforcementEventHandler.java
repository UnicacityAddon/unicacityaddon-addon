package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.location.NaviPoint;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 **/
public class ReinforcementEventHandler {

    private static ReinforcementCommand.ReinforcementType lastReinforcement;

    @SubscribeEvent public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher reinforcementMatcher = PatternHandler.REINFORCEMENT_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (reinforcementMatcher.find()) {
            String fullName = reinforcementMatcher.group(1);
            String name = reinforcementMatcher.group(2);
            String[] splitFormattedMsg = e.getMessage().getFormattedText().split(":");

            int posX = Integer.parseInt(reinforcementMatcher.group(3));
            int posY = Integer.parseInt(reinforcementMatcher.group(4));
            int posZ = Integer.parseInt(reinforcementMatcher.group(5));

            int distance = (int) p.getPosition().getDistance(posX, posY, posZ);

            boolean dChat = splitFormattedMsg[0].contains(ColorCode.RED.getCode())
                    && splitFormattedMsg[1].contains(ColorCode.RED.getCode());

            String type = "Reinforcement!";
            if (lastReinforcement != null && name.equals(lastReinforcement.getIssuer()) && System.currentTimeMillis() - lastReinforcement.getTime() < 1000) {
                type = lastReinforcement.getType().getMessage();
            }

            ITextComponent hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.AQUA).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posY).color(ColorCode.AQUA).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posZ).color(ColorCode.AQUA).advance()
                    .createComponent();

            Map.Entry<Double, NaviPoint > nearestNaviPoint = NavigationUtils.getNearestNaviPoint(posX, posY, posZ);

            p.sendMessageAsString(ConfigElements.getPatternReinforcement()
                    .replace("&", "§")
                    .replace("%type%", type)
                    .replace("%sender%", fullName)
                    .replace("%x%", String.valueOf(posX))
                    .replace("%y%", String.valueOf(posY))
                    .replace("%z%", String.valueOf(posZ))
                    .replace("%navipoint%", nearestNaviPoint.getValue().getName())
                    .replace("%distance%", String.valueOf(distance)));

            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of("Route Anzeigen")
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage)
                    .color(ColorCode.RED).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("Unterwegs").hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs Nachricht absenden").color(ColorCode.RED).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/reinforcement ontheway " + name + " " + posX + " " + posY + " " + posZ + (dChat ? " -d" : ""))
                    .color(ColorCode.RED).advance()
                    .createComponent());

            e.setCanceled(true);
            return false;
        }

        Matcher onTheWayMatcher = PatternHandler.ON_THE_WAY_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (onTheWayMatcher.find()) {
            String senderFullName = onTheWayMatcher.group(1);
            String reinforcementSenderName = onTheWayMatcher.group(3);
            String distance = onTheWayMatcher.group(4);

            p.sendMessageAsString(ConfigElements.getPatternReinforcementReply()
                    .replace("&", "§")
                    .replace("%sender%", senderFullName)
                    .replace("%target%", reinforcementSenderName)
                    .replace("%distance%", String.valueOf(distance)));

            e.setCanceled(true);
            return false;
        }

        for (ReinforcementCommand.Type type : ReinforcementCommand.Type.values()) {
            Pattern pattern = type.getPattern();
            if (pattern == null) continue;

            Matcher matcher = pattern.matcher(e.getMessage().getUnformattedText());
            if (!matcher.find()) continue;

            String name = matcher.group(1);

            lastReinforcement = new ReinforcementCommand.ReinforcementType(name, type);
            e.setCanceled(true);
            return false;
        }

        return false;
    }
}
