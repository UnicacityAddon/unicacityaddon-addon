package com.rettichlp.UnicacityAddon.events.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class ExplosiveBeltTimerEventHandler {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!ExplosiveBeltTimerModule.explosiveBeltStarted || ++ExplosiveBeltTimerModule.currentTick != 20) return;
        ExplosiveBeltTimerModule.currentTick = 0;
        ExplosiveBeltTimerModule.currentCount--;

        ExplosiveBeltTimerModule.timer = "" + ExplosiveBeltTimerModule.currentCount;

        if (ExplosiveBeltTimerModule.currentCount <= 0) stopBombTimer();
    }

    private void stopBombTimer() {
        ExplosiveBeltTimerModule.explosiveBeltStarted = false;
        ExplosiveBeltTimerModule.currentCount = 0;
        ExplosiveBeltTimerModule.timer = "";
    }
}
