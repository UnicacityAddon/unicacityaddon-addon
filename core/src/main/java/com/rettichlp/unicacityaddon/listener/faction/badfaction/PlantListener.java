package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 */
@UCEvent
public class PlantListener {

    private final UnicacityAddon unicacityAddon;

    public PlantListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        ClientPlayerInteractEvent.InteractionType interactionType = e.type();

        if (interactionType.equals(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            FloatVector3 pos = this.unicacityAddon.worldInteractionController().getClickedBlockLocation();
            boolean isPlant = pos != null && this.unicacityAddon.worldInteractionController().isPlant(pos);

            if (isPlant) {
                this.unicacityAddon.player().sendServerMessage("/plant");
            }
        }
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.PLANT_HARVEST_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.services().fileService().data().setPlantFertilizeTime(0L);
            this.unicacityAddon.services().fileService().data().setPlantWaterTime(0L);
            return;
        }

        Matcher plantUseMatcher = PatternHandler.PLANT_USE_PATTERN.matcher(msg);
        if (plantUseMatcher.find() && msg.contains(this.unicacityAddon.player().getName())) {
            if (msg.contains("gewässert") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > this.unicacityAddon.services().fileService().data().getPlantWaterTime()) {
                this.unicacityAddon.services().fileService().data().setPlantWaterTime(System.currentTimeMillis());
            } else if (msg.contains("gedüngt") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > this.unicacityAddon.services().fileService().data().getPlantFertilizeTime()) {
                this.unicacityAddon.services().fileService().data().setPlantFertilizeTime(System.currentTimeMillis());
            }
        }
    }
}