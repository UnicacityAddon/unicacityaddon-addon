package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GiftEigenbedarfCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class GiftEigenbedarfListener {

    private final UnicacityAddon unicacityAddon;

    public GiftEigenbedarfListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        Matcher drugDealEndedMatcher = PatternHandler.DRUG_DEAL_ENDED.matcher(msg);
        if (drugDealEndedMatcher.find() && !GiftEigenbedarfCommand.scheduledTasks.isEmpty()) {
            p.sendServerMessage(GiftEigenbedarfCommand.scheduledTasks.remove(0));
        }
    }
}
