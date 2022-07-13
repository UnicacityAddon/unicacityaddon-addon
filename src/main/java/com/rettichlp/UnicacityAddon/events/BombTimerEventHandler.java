package com.rettichlp.UnicacityAddon.events;

import com.google.common.eventbus.Subscribe;
import com.rettichlp.UnicacityAddon.base.event.UCEventForge;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import net.labymod.api.events.MessageReceiveEvent;
import net.labymod.utils.ModUtils;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@UCEventLabymod(event = "MessageReceiveEvent")
@UCEventForge
public class BombTimerEventHandler implements MessageReceiveEvent {

    @Subscribe
    public void onTick(TickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!BombTimerModule.isBomb || ++BombTimerModule.currentTick != 20) return;
        BombTimerModule.currentTick = 0;

        if (BombTimerModule.currentCount++ >= 780) BombTimerModule.timer = ColorCode.RED.getCode() + ModUtils.parseTimer(BombTimerModule.currentCount);
        else BombTimerModule.timer = ModUtils.parseTimer(BombTimerModule.currentCount);

        if (BombTimerModule.currentCount > 1200) stopBombTimer();
    }

    @Override public boolean onReceive(String s, String s1) {
        String msg = s;

        if (PatternHandler.BOMB_PLACED_PATTERN.matcher(msg).find()) {
            BombTimerModule.isBomb = true;
            BombTimerModule.timer = "00:00";
            return false;
        }

        if (PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg).find()) stopBombTimer();
        return false;
    }

    private void stopBombTimer() {
        BombTimerModule.isBomb = false;
        BombTimerModule.currentCount = 0;
        BombTimerModule.timer = "";
    }
}