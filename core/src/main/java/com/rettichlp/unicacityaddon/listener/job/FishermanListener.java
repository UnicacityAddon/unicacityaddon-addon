package com.rettichlp.unicacityaddon.listener.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
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
public class FishermanListener {

    public static boolean dropFish = false;
    private final FloatVector3 FISHER_START_LOCATION = new FloatVector3(-504, 63, 197);
    private final List<FloatVector3> FISHER_LOCATION_LIST = Arrays.asList(
            new FloatVector3(-570, 63, 160),
            new FloatVector3(-555, 63, 106),
            new FloatVector3(-521, 63, 78),
            new FloatVector3(-569, 63, 50),
            new FloatVector3(-522, 63, 10)
    );
    private boolean onTargetLocation = false;
    private boolean canCatchFish = false;
    private boolean fisherManJob = false;
    private int count = 0;

    private final UnicacityAddon unicacityAddon;

    public FishermanListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        AddonPlayer p = this.unicacityAddon.player();

        if (PatternHandler.FISHER_START.matcher(msg).find()) {
            fisherManJob = canCatchFish = true;
            p.setNaviRoute(getFisherLocation(1));
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
            p.setNaviRoute(getFisherLocation(count + 2));
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
            if (p.getLocation() != null && p.getLocation().distance(FISHER_START_LOCATION) < 2) {
                p.sendServerMessage("/dropfish");
                return;
            }
            dropFish = true;
        }
    }

    private FloatVector3 getFisherLocation(int i) {
        return i > FISHER_LOCATION_LIST.size() ? FISHER_START_LOCATION : FISHER_LOCATION_LIST.get(i - 1);
    }

    private void catchFish() {
        if (canCatchFish && onTargetLocation && count < 5) {
            this.unicacityAddon.player().sendServerMessage("/catchfish");
            onTargetLocation = canCatchFish = false;
        }
    }
}