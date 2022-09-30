package com.rettichlp.UnicacityAddon.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class BroadcastEventHandler {

    private int currentTick = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (currentTick++ != 900) return;

        UPlayer p = AbstractionLayer.getPlayer();

        JsonArray response = APIRequest.sendBroadcastQueueRequest();
        if (response == null) return;

        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();
            String broadcast = o.get("broadcast").getAsString();
            String issuerName = o.get("issuerName").getAsString();

            p.sendEmptyMessage();
            p.sendEmptyMessage();

            p.sendMessage(Message.getBuilder()
                    .of("BROADCAST BY ").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(issuerName.toUpperCase()).color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            p.sendMessage(Message.getBuilder()
                    .of(broadcast).color(ColorCode.AQUA).advance()
                    .createComponent());

            p.sendEmptyMessage();
            p.sendEmptyMessage();
        });

        currentTick = 0;
    }
}