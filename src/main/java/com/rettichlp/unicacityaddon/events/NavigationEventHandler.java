package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import com.rettichlp.unicacityaddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.unicacityaddon.events.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.events.job.JobEventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class NavigationEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(msg);
        if (routeMatcher.find() && System.currentTimeMillis() - NavigationUtils.routeMessageClearExecuteTime < 500L) {
            e.setCanceled(true);
            return;
        }

        if (msg.equals("Du hast dein Ziel erreicht.")) {
            if (FishermanEventHandler.dropFish) {
                p.sendChatMessage("/dropfish");
                FishermanEventHandler.dropFish = false;
            }

            if (JobEventHandler.isTabakJob) {
                p.sendChatMessage("/droptabak");
            }

            if (EmergencyServiceEventHandler.distanceToService > 0) {
                BlockPos pos = p.getPosition();

                double distanceFromServiceAcceptPositionToHere = EmergencyServiceEventHandler.serviceAcceptPosition.getDistance(pos.getX(), pos.getY(), pos.getZ());
                if (Math.abs(EmergencyServiceEventHandler.distanceToService - distanceFromServiceAcceptPositionToHere) <= 5) {
                    p.sendChatMessage("/doneservice");
                } else {
                    p.sendInfoMessage("Das Addon hat deinen Service nicht als erledigt markiert, da die Entfernung zum Zielpunkt nicht übereingestimmt hat.");
                }
                EmergencyServiceEventHandler.distanceToService = 0;
            }
        }
    }
}
