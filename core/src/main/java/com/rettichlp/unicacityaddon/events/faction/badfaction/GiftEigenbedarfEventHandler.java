package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GiftEigenbedarfCommand;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class GiftEigenbedarfEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.chatMessage().getPlainText();

        Matcher drugDealEndedMatcher = PatternHandler.DRUGDEAL_ENDED.matcher(msg);
        if (drugDealEndedMatcher.find() && !GiftEigenbedarfCommand.scheduledTasks.isEmpty()) {
            p.sendChatMessage(GiftEigenbedarfCommand.scheduledTasks.remove(0));
        }
    }
}
