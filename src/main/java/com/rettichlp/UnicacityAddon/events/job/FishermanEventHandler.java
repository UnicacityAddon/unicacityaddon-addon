package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FishermanEventHandler {

    private boolean onTargetLocation = false;
    private boolean canCatchFish = false;
    private int count = 0;

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.FISHER_START.matcher(msg).find()) {
            p.sendChatMessage("/findschwarm");
            canCatchFish = true;
            return catchFish();
        }

        if (PatternHandler.FISHER_CATCH_SUCCESS.matcher(msg).find()
                || PatternHandler.FISHER_CATCH_FAILURE.matcher(msg).find()) {
            canCatchFish = true;
            count++;
            return catchFish();
        }

        if (PatternHandler.FISHER_CATCH_START.matcher(msg).find()) {
            canCatchFish = false;
            return catchFish();
        }

        if (PatternHandler.FISHER_SPOT_FOUND.matcher(msg).find()) {
            onTargetLocation = true;
            return catchFish();
        }

        if (PatternHandler.FISHER_SPOT_LOSE.matcher(msg).find()
                || PatternHandler.FISHER_END.matcher(msg).find()) {
            onTargetLocation = false;
            return catchFish();
        }

        return false;
    }

    private boolean catchFish() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (canCatchFish && onTargetLocation && count < 5) {
            p.sendChatMessage("/catchfish");
            p.sendChatMessage("/findschwarm");
            return true;
        }
        return false;
    }
}
