package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.GiftEigenbedarfCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class GiftEigenbedarfEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

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
