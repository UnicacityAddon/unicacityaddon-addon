package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class FishermanEventHandler {

    private boolean onTargetLocation = false;
    private boolean canCatchFish = false;
    private boolean fisherManJob = false;
    private boolean dropFish = false;
    private int count = 0;

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.FISHER_START.matcher(msg).find()) {
            fisherManJob = true;
            p.sendChatMessage("/findschwarm");
            canCatchFish = true;
            return catchFish();
        }

        if (!fisherManJob) return false;

        if (PatternHandler.FISHER_CATCH_SUCCESS.matcher(msg).find()
                || PatternHandler.FISHER_CATCH_FAILURE.matcher(msg).find()) {
            canCatchFish = true;
            count++;
            return catchFish();
        }

        if (PatternHandler.FISHER_CATCH_START.matcher(msg).find()) {
            canCatchFish = false;
            p.sendChatMessage("/findschwarm");
            return false;
        }

        if (PatternHandler.FISHER_SPOT_FOUND.matcher(msg).find()) {
            onTargetLocation = true;
            return catchFish();
        }

        if (PatternHandler.FISHER_SPOT_LOSE.matcher(msg).find()) {
            onTargetLocation = false;
            return false;
        }

        if (PatternHandler.FISHER_END.matcher(msg).find()) {
            count = 0;
            fisherManJob = onTargetLocation = canCatchFish = false;
            dropFish = true;
            p.setNaviRoute(new BlockPos(-504, 63, 197)); // Steg
            return false;
        }

        if (dropFish && msg.equals("Du hast dein Ziel erreicht.")) {
            p.sendChatMessage("/dropfish");
            dropFish = false;
        }

        return false;
    }

    private boolean catchFish() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (canCatchFish && onTargetLocation && count < 5) {
            p.sendChatMessage("/catchfish");
            onTargetLocation = false;
            canCatchFish = false;
            return true;
        }
        return false;
    }
}