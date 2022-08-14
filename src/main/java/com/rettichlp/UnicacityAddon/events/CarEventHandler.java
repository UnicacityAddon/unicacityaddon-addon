package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import java.util.regex.Matcher;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class CarEventHandler {

    @SubscribeEvent public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
            CarOpenModule.info = ColorCode.GREEN.getCode() + "offen";
            return false;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
            CarOpenModule.info = ColorCode.RED.getCode() + "zu";
            return false;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && ConfigElements.getEventCarFind()) {
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
        }
        return false;
    }
}
