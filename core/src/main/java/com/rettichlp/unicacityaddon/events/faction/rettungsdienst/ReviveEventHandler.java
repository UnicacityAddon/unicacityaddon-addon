package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class ReviveEventHandler {

    public static boolean isDead = false;

    private static long reviveByMedicStartTime = 0; // revive time if you are dead
    private static long reviveFromMedicStartTime = 0; // revive time if you are the medic
    private static final Timer timer = new Timer();

    @Subscribe
    public void onReviveStart(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher reviveByMedicStartMatcher = PatternHandler.REVIVE_BY_MEDIC_START_PATTERN.matcher(msg);
        if (reviveByMedicStartMatcher.find()) {
            reviveByMedicStartTime = System.currentTimeMillis();
            return;
        }

        Matcher reviveFailureMatcher = PatternHandler.REVIVE_FAILURE_PATTERN.matcher(msg);
        if (reviveFailureMatcher.find()) {
            isDead = false;

            FileManager.DATA.setTimer(0);
            FileManager.DATA.setCashBalance(0);

            if (ShutdownGraveyardCommand.shutdownGraveyard)
                ForgeUtils.shutdownPC();
            return;
        }

        Matcher firstAidUseMatcher = PatternHandler.FIRST_AID_USE_PATTERN.matcher(msg);
        if (firstAidUseMatcher.find()) {
            FileManager.DATA.setTimer(FileManager.DATA.getTimer() + 60);
            return;
        }

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && UnicacityAddon.isUnicacity())
            reviveFromMedicStartTime = System.currentTimeMillis();
    }

//    @SubscribeEvent
//    public void onSuccessfulRevive(PotionEvent.PotionAddedEvent e) {
//        UPlayer p = AbstractionLayer.getPlayer();
//
//        if (isDead && e.getPotionEffect().getPotion().equals(Potion.getPotionById(15))) {
//            isDead = false;
//            FileManager.DATA.setTimer(0);
//
//            if (System.currentTimeMillis() - reviveByMedicStartTime < TimeUnit.SECONDS.toMillis(10)) {
//                FileManager.DATA.removeBankBalance(50); // successfully revived by medic = 50$
//
//                // message to remember how long you are not allowed to shoot after revive
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        p.sendInfoMessage("Du darfst wieder schießen.");
//                    }
//                }, TimeUnit.MINUTES.toMillis(2));
//            }
//
//            if (MobileEventHandler.hasCommunications && !AccountEventHandler.isAfk)
//                p.sendChatMessage("/togglephone");
//        }
//    }

    public static void handleRevive() {
        if (System.currentTimeMillis() - reviveFromMedicStartTime < TimeUnit.SECONDS.toMillis(10))
            APIRequest.sendStatisticAddRequest(StatisticType.REVIVE);
    }
}