package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FishermanEventHandler {

    private boolean onTargetLocation = false;
    private boolean canCatchFish = false;

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.FISHER_START.matcher(msg).find()
                || PatternHandler.FISHER_CATCH_SUCCESS.matcher(msg).find()
                || PatternHandler.FISHER_CATCH_FAILURE.matcher(msg).find()) {
            canCatchFish = true;
            return catchFish();
        }

        if (PatternHandler.FISHER_CATCH_START.matcher(msg).find()) {
            AbstractionLayer.getPlayer().sendChatMessage("/findschwarm");
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
        if (canCatchFish && onTargetLocation) {
            AbstractionLayer.getPlayer().sendChatMessage("/catchfish");
            return true;
        }
        return false;
    }
}
