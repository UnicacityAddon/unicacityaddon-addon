package com.rettichlp.unicacityaddon.events.job;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

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
    private final FloatVector3 FISHER_START_POSITION = new FloatVector3(-504, 63, 197);
    private final List<FloatVector3> FISHER_POSITION_LIST = Arrays.asList(
            new FloatVector3(-570, 63, 160),
            new FloatVector3(-555, 63, 106),
            new FloatVector3(-521, 63, 78),
            new FloatVector3(-569, 63, 50),
            new FloatVector3(-522, 63, 10)
    );

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        String msg = e.chatMessage().getPlainText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.FISHER_START.matcher(msg).find()) {
            fisherManJob = canCatchFish = true;
            p.setNaviRoute(getFisherPosition(1));
            catchFish();
            return;
        }

        if (!fisherManJob)
            return;

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
            if (p.getPosition().distance(FISHER_START_POSITION) < 2) {
                p.sendChatMessage("/dropfish");
                return;
            }
            dropFish = true;
        }
    }

    private FloatVector3 getFisherPosition(int i) {
        return i > FISHER_POSITION_LIST.size() ? FISHER_START_POSITION : FISHER_POSITION_LIST.get(i - 1);
    }

    private void catchFish() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (canCatchFish && onTargetLocation && count < 5) {
            p.sendChatMessage("/catchfish");
            onTargetLocation = canCatchFish = false;
        }
    }
}