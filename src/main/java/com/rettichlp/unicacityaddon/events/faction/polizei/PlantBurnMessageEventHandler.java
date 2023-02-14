package com.rettichlp.unicacityaddon.events.faction.polizei;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Gelegenheitscode
 */

@UCEvent
public class PlantBurnMessageEventHandler {
    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.PLANT_BURN.matcher(msg);
        if (ConfigElements.getPlantBurnMessageActivated())  {
            if (m.find())   {
                e.setMessage(Message.getBuilder().of("Plant-Burn").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.GREEN).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(m.group(3)).color(ColorCode.GOLD).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .createComponent());
            }
        }
    }
}
