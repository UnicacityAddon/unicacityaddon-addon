package com.rettichlp.UnicacityAddon.events.faction.terrorist;

import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author RettichLP
 */
public class ExplosiveBeltTimerEvent {

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
