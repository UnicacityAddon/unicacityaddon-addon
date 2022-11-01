package com.rettichlp.UnicacityAddon.events.faction.terroristen;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

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

        Matcher m = PatternHandler.BOMB_REMOVED_PATTERN.matcher(msg);
        if (m.find()) {
            String state = m.group(1);
            e.setMessage(Message.getBuilder()
                    .of("News: Die Bombe konnte").color(ColorCode.GOLD).advance().space()
                    .of(state).color(ColorCode.GOLD).advance().space()
                    .of("entschärft werden!").color(ColorCode.GOLD).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(BombTimerModule.timer).color(state.equals("nicht") ? ColorCode.RED : ColorCode.GREEN).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
            BombTimerModule.stopBombTimer();
        }
        return false;
    }
}