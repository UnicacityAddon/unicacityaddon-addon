package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DeathsKillsEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher jailKillMatcher = PatternHandler.WANTED_KILL.matcher(msg);
        if (jailKillMatcher.find()) {
            if (jailKillMatcher.group(2).equals(p.getName()))
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
            return;
        }

        Matcher contractKillPattern = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(msg);
        if (contractKillPattern.find() && msg.contains("getötet")) {
            if (msg.contains(p.getName())) {
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage();
            }
        }
    }
}