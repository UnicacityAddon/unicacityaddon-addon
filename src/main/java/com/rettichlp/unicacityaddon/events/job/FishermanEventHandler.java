package com.rettichlp.unicacityaddon.events.job;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author RettichLP
 */
@UCEvent
public class FishermanEventHandler {

    public static boolean dropFish = false;

    private boolean onTargetLocation = false;
    private boolean canCatchFish = false;
    private boolean fisherManJob = false;
    private int count = 0;
    private final List<BlockPos> FISHER_POSITION_LIST = Arrays.asList(
            new BlockPos(-570, 63, 160),
            new BlockPos(-555, 63, 106),
            new BlockPos(-521, 63, 78),
            new BlockPos(-569, 63, 50),
            new BlockPos(-522, 63, 10)
    );

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.FISHER_START.matcher(msg).find()) {
            fisherManJob = canCatchFish = true;
            p.setNaviRoute(getFisherPosition(1));
            catchFish();
            return;
        }

        if (!fisherManJob) return;

        if (PatternHandler.FISHER_CATCH_SUCCESS.matcher(msg).find()
                || PatternHandler.FISHER_CATCH_FAILURE.matcher(msg).find()) {
            canCatchFish = true;
            count++;
            catchFish();
            return;
        }

        if (PatternHandler.FISHER_CATCH_START.matcher(msg).find()) {
            canCatchFish = false;
            p.setNaviRoute(getFisherPosition(count + 2));
            return;
        }

        if (PatternHandler.FISHER_SPOT_FOUND.matcher(msg).find()) {
            onTargetLocation = true;
            catchFish();
            return;
        }

        if (PatternHandler.FISHER_SPOT_LOSE.matcher(msg).find()) {
            onTargetLocation = false;
            return;
        }

        if (PatternHandler.FISHER_END.matcher(msg).find()) {
            count = 0;
            fisherManJob = onTargetLocation = canCatchFish = false;
            if (p.getPosition().getDistance(-504, 63, 197) < 2) {
                p.sendChatMessage("/dropfish");
                return;
            }
            dropFish = true;
        }
    }

    private BlockPos getFisherPosition(int i) {
        return i > FISHER_POSITION_LIST.size() ? new BlockPos(-504, 63, 197) /* Steg */ : FISHER_POSITION_LIST.get(i - 1);
    }

    private void catchFish() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (canCatchFish && onTargetLocation && count < 5) {
            p.sendChatMessage("/catchfish");
            onTargetLocation = canCatchFish = false;
        }
    }
}