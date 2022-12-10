package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.GiftEigenbedarfCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class GiftEigenbedarfEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.chatMessage().getPlainText();

        if (!GiftEigenbedarfCommand.checkWeed && !GiftEigenbedarfCommand.checkMeth) return;

        Matcher drugDealEndedMatcher = PatternHandler.DRUGDEAL_ENDED.matcher(msg);
        if (!drugDealEndedMatcher.find()) return;

        if (ConfigElements.getMarihuanaActivated() && GiftEigenbedarfCommand.checkWeed) {
            p.sendChatMessage("/selldrug " + drugDealEndedMatcher.group(1) + " Gras " + ConfigElements.getMarihuanaDrugPurity().getPurity() + " " + ConfigElements.getMarihuanaAmount() + " 0");
            GiftEigenbedarfCommand.checkWeed = false;

            if (ConfigElements.getMethActivated()) GiftEigenbedarfCommand.checkMeth = true;
            return;
        }

        if (ConfigElements.getMethActivated() && GiftEigenbedarfCommand.checkMeth)
            p.sendChatMessage("/selldrug " + drugDealEndedMatcher.group(1) + " Meth " + ConfigElements.getMethDrugPurity().getPurity() + " " + ConfigElements.getMethAmount() + " 0");

        GiftEigenbedarfCommand.checkMeth = false;
    }
}
