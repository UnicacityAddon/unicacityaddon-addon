package com.rettichlp.UnicacityAddon.events.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class BombTimerEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.BOMB_PLACED_PATTERN.matcher(msg).find()) {
            BombTimerModule.isBomb = true;
            BombTimerModule.timer = "00:00";
            return false;
        }

        if (PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg).find()) stopBombTimer();
        return false;
    }

    public static void stopBombTimer() {
        BombTimerModule.isBomb = false;
        BombTimerModule.currentCount = 0;
        BombTimerModule.timer = "";
    }
}