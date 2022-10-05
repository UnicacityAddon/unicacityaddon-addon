package com.rettichlp.UnicacityAddon.events.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;

/**
 * @author Dimiikou
 */
@UCEvent
public class ExplosiveBeltTimerEventHandler {

    public static void stopBombTimer() {
        ExplosiveBeltTimerModule.explosiveBeltStarted = false;
        ExplosiveBeltTimerModule.currentCount = 0;
        ExplosiveBeltTimerModule.timer = "";
    }
}
