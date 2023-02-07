package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.events.AccountEventHandler;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class ReviveEventHandler {

    private static long reviveByMedicStartTime = 0; // revive time if you are dead
    private static long reviveFromMedicStartTime = 0; // revive time if you are the medic

    @Subscribe
    public void onReviveStart(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher reviveByMedicStartMatcher = PatternHandler.REVIVE_BY_MEDIC_START_PATTERN.matcher(msg);
        if (reviveByMedicStartMatcher.find()) {
            reviveByMedicStartTime = System.currentTimeMillis();
            return;
        }

        Matcher reviveByMedicFinishMatcher = PatternHandler.REVIVE_BY_MEDIC_FINISH_PATTERN.matcher(msg);
        if (reviveByMedicFinishMatcher.find()) {
//            if (System.currentTimeMillis() - reviveByMedicStartTime > TimeUnit.SECONDS.toMillis(10)) {
//                CashMoneyModule.setBalance(0);
//            } else {
//                BankMoneyModule.removeBalance(50); // successfully revived by medic = 50$
//            }
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