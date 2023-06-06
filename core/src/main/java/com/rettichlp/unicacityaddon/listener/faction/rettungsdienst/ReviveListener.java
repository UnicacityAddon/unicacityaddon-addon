package com.rettichlp.unicacityaddon.listener.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import com.rettichlp.unicacityaddon.listener.AccountListener;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class ReviveListener {

    public static boolean isDead = false;
    public static long medicReviveStartTime = 0; // revive time if you are the medic

    private static long playerReviveStartTime = 0; // revive time if you are dead
    private static final Timer timer = new Timer();

    private final UnicacityAddon unicacityAddon;

    public ReviveListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher reviveByMedicStartMatcher = PatternHandler.REVIVE_BY_MEDIC_START_PATTERN.matcher(msg);
        if (reviveByMedicStartMatcher.find()) {
            playerReviveStartTime = System.currentTimeMillis();

            FloatVector3 playerReviveLocation = this.unicacityAddon.player().getLocation();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    FloatVector3 location = ReviveListener.this.unicacityAddon.player().getLocation();
                    if (location != null && location.distance(playerReviveLocation) > 50) {
                        AddonPlayer p = ReviveListener.this.unicacityAddon.player();
                        ReviveListener.this.unicacityAddon.services().fileService().data().setTimer(0);
                        isDead = false;

                        if (System.currentTimeMillis() - playerReviveStartTime < TimeUnit.SECONDS.toMillis(10)) {
                            ReviveListener.this.unicacityAddon.services().fileService().data().removeBankBalance(50); // successfully revived by medic = 50$

                            // message to remember how long you are not allowed to shoot after revive
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    p.sendInfoMessage("Du darfst wieder schießen.");
                                }
                            }, TimeUnit.MINUTES.toMillis(2));
                        }

                        if (MobileListener.hasCommunications && !AccountListener.isAfk)
                            p.sendServerMessage("/togglephone");
                    }
                }
            }, TimeUnit.SECONDS.toMillis(9));

            return;
        }

        Matcher reviveFailureMatcher = PatternHandler.REVIVE_FAILURE_PATTERN.matcher(msg);
        if (reviveFailureMatcher.find()) {
            isDead = false;

            this.unicacityAddon.services().fileService().data().setTimer(0);
            this.unicacityAddon.services().fileService().data().setCashBalance(0);

            if (ShutdownGraveyardCommand.shutdownGraveyard)
                this.unicacityAddon.utils().shutdownPC();

            return;
        }

        Matcher firstAidUseMatcher = PatternHandler.FIRST_AID_USE_PATTERN.matcher(msg);
        if (firstAidUseMatcher.find()) {
            this.unicacityAddon.services().fileService().data().setTimer(this.unicacityAddon.services().fileService().data().getTimer() + 60);
            return;
        }

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && this.unicacityAddon.utils().isUnicacity())
            medicReviveStartTime = System.currentTimeMillis();
    }
}