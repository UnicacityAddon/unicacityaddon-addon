package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.events.AccountEventHandler;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class ReviveEventHandler {

    private static long reviveByMedicStartTime = 0; // revive time if you are dead
    private static long reviveFromMedicStartTime = 0; // revive time if you are the medic

    @SubscribeEvent
    public void onReviveStart(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher reviveByMedicStartMatcher = PatternHandler.REVIVE_BY_MEDIC_START_PATTERN.matcher(msg);
        if (reviveByMedicStartMatcher.find()) {
            reviveByMedicStartTime = System.currentTimeMillis();
            return;
        }

//        TODO: 22.01.2023 Add permission for minecraft gui -> actionMessage -> read message -> add event
//        TODO: 23.01.2023 stopTimer
//        Matcher reviveByMedicFinishMatcher = PatternHandler.REVIVE_BY_MEDIC_FINISH_PATTERN.matcher(msg);
//        if (reviveByMedicFinishMatcher.find()) {
//            if (System.currentTimeMillis() - reviveByMedicStartTime > TimeUnit.SECONDS.toMillis(10)) {
//                FileManager.DATA.setCashBalance(0);
//            } else {
//                FileManager.DATA.removeBankBalance(50); // successfully revived by medic = 50$
//            }
//            if (MobileEventHandler.hasCommunications && !AccountEventHandler.isAfk)
//                AbstractionLayer.getPlayer().sendChatMessage("/togglephone");
//            return;
//        }

        Matcher reviveFailureMatcher = PatternHandler.REVIVE_FAILURE_PATTERN.matcher(msg);
        if (reviveFailureMatcher.find()) {
            FileManager.DATA.setTimer(0);
            FileManager.DATA.setCashBalance(0);

            if (MobileEventHandler.hasCommunications && !AccountEventHandler.isAfk)
                AbstractionLayer.getPlayer().sendChatMessage("/togglephone");
            return;
        }

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && UnicacityAddon.isUnicacity())
            reviveFromMedicStartTime = System.currentTimeMillis();
    }

    public static void handleRevive() {
        if (System.currentTimeMillis() - reviveFromMedicStartTime < TimeUnit.SECONDS.toMillis(10))
            APIRequest.sendStatisticAddRequest(StatisticType.REVIVE);
    }
}