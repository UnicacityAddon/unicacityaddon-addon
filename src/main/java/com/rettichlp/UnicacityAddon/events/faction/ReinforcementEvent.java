package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 **/
public class ReinforcementEvent {

    private static ReinforcementCommand.ReinforcementType lastReinforcement;

    @SubscribeEvent public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher reinforcementMatcher = PatternHandler.REINFORCEMENT_PATTERN.matcher(e.getMessage().getUnformattedText());

        p.sendMessageAsString("1");
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

            Message.Builder builder = Message.getBuilder();
            p.sendMessageAsString("2");
            if (lastReinforcement != null && name.equals(lastReinforcement.getIssuer()) && System.currentTimeMillis() - lastReinforcement.getTime() < 1000) {
                builder.of(lastReinforcement.getType().getMessage()).color(ColorCode.RED).bold().advance().space();
            }

            // TODO: Nähesten Navispot rausfinden
            p.sendMessageAsString("3");
            Message hoverMessage = Message.getBuilder().of("" + posX).color(ColorCode.BLUE).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posY).color(ColorCode.BLUE).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("" + posZ).color(ColorCode.BLUE).advance()
                    .build();

            // &c&lDringend! &9Dimiikou &7- &9Stadthalle &7- &c300m
            builder.of(name).color(ColorCode.BLUE).advance()
                    .of(" - ").color(ColorCode.GRAY).advance()
                    //.of(nearestLocation.getName()).color(ColorCode.BLUE).hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage.toTextComponent).advance()
                    //.of(" - ").color(ColorCode.GRAY).advance()
                    .of(distance + "m").color(ColorCode.RED).advance()
                    .newline()
                    .of("» ").color(ColorCode.GRAY).advance().space()
                    .of("Route Anzeigen").color(ColorCode.RED)
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + posX + "/" + posY + "/" + posZ)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage.toTextComponent()).advance()
                    .of(" | ").color(ColorCode.GRAY).advance()
                    .of("Unterwegs").color(ColorCode.RED)
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/reinforcement ontheway " + name + " " + posX + " " + posY + " " + posZ + (dChat ? " -d" : ""))
                    .color(ColorCode.RED).advance()
                    .sendTo(p.getPlayer());
            p.sendMessageAsString("4");
            return true;
        }

        return false;
    }
}
