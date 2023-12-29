package com.rettichlp.unicacityaddon.listener.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.ShutdownGraveyardCommand;
import com.rettichlp.unicacityaddon.listener.AccountListener;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class ReviveListener {

    public static long medicReviveStartTime = 0;
    private static final Timer timer = new Timer();
    private long lastReviveMessageDisplayTime;

    private final UnicacityAddon unicacityAddon;

    public ReviveListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher reviveFailureMatcher = PatternHandler.REVIVE_FAILURE_PATTERN.matcher(msg);
        if (reviveFailureMatcher.find()) {
            this.unicacityAddon.fileService().data().setTimer(-1);
            this.unicacityAddon.fileService().data().setCashBalance(0);

            if (ShutdownGraveyardCommand.shutdownGraveyard)
                this.unicacityAddon.utilService().shutdownPC();

            return;
        }

        Matcher firstAidUseMatcher = PatternHandler.FIRST_AID_USE_PATTERN.matcher(msg);
        if (firstAidUseMatcher.find()) {
            this.unicacityAddon.fileService().data().setTimer(this.unicacityAddon.fileService().data().getTimer() + 60);
            return;
        }

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && this.unicacityAddon.utilService().isUnicacity()) {
            medicReviveStartTime = System.currentTimeMillis();
        }
    }

    @Subscribe
    public void onActionBarReceive(ActionBarReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        if (System.currentTimeMillis() - this.lastReviveMessageDisplayTime > TimeUnit.SECONDS.toMillis(5) && this.unicacityAddon.utilService().text().plain(e.getMessage()).equals("Du lebst nun wieder.")) {
            this.lastReviveMessageDisplayTime = System.currentTimeMillis();

            this.unicacityAddon.fileService().data().removeBankBalance(50); // successfully revived by medic = 50$
            this.unicacityAddon.fileService().data().setTimer(-1);

            // message to remember how long you are not allowed to shoot after revive
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendInfoMessage("Du darfst wieder schießen.");
                }
            }, TimeUnit.MINUTES.toMillis(2));

            if (MobileListener.hasCommunications && !AccountListener.isAfk) {
                p.sendServerMessage("/togglephone");
            }
        }
    }
}