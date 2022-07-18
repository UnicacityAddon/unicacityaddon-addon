package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BombTimerEventHandler {

    @SubscribeEvent public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!BombTimerModule.isBomb || ++BombTimerModule.currentTick != 20) return;
        BombTimerModule.currentTick = 0;

        if (BombTimerModule.currentCount++ >= 780) BombTimerModule.timer = ColorCode.RED.getCode() + ModUtils.parseTimer(BombTimerModule.currentCount);
        else BombTimerModule.timer = ModUtils.parseTimer(BombTimerModule.currentCount);

        if (BombTimerModule.currentCount > 1200) stopBombTimer();
    }

    @SubscribeEvent public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

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