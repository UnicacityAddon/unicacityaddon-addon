package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.mobile.ACallCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ASMSCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class MobileListener {

    public static final List<String> blockedPlayerList = new ArrayList<>();
    public static int lastCheckedNumber = 0;
    public static boolean hasCommunications = false;
    public static boolean muted = false;
    public static boolean activeCommunicationsCheck;
    private boolean blockNextMessage = false;

    private final UnicacityAddon unicacityAddon;

    public MobileListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * If the user has set a password for their account, <code>/mobile</code> cannot be listed until the account is unlocked.
     * As a result, <code>hasCommunications</code> remains false. To avoid this, the check is carried out again when the message
     * came that the account was unlocked.<br><br>
     * <p>
     * Quote: "Du hast richtig gedacht aber es einfach falsch verstanden." - Dimiikou, 04.10.2022
     */
    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        // blocks next SMS message (because SMS messages has two independent message parts)
        if (this.blockNextMessage && msg.matches("^(?:\\[UC])*\\w+: .*$")) {
            this.blockNextMessage = false;
            e.setCancelled(true);
            return;
        }

        Matcher communicationsRemoveMatcher = PatternHandler.MOBILE_REMOVE_PATTERN.matcher(msg);
        if (communicationsRemoveMatcher.find()) {
            hasCommunications = false;
            return;
        }

        Matcher communicationsGetMatcher = PatternHandler.MOBILE_GET_PATTERN.matcher(msg);
        if (communicationsGetMatcher.find()) {
            hasCommunications = true;
            return;
        }

        Matcher mobileTogglePattern = PatternHandler.MOBILE_TOGGLE_PATTERN.matcher(msg);
        if (mobileTogglePattern.find()) {
            hasCommunications = true;

            if (activeCommunicationsCheck) {
                e.setCancelled(true);
                if (msg.contains("eingeschaltet") || msg.contains("Akku")) {
                    activeCommunicationsCheck = false;
                } else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            p.sendServerMessage("/togglephone");
                        }
                    }, TimeUnit.SECONDS.toMillis(1));
                }
            }
            return;
        }

        Matcher numberMatcher = PatternHandler.MOBILE_NUMBER_PATTERN.matcher(msg);
        if (numberMatcher.find()) {
            lastCheckedNumber = Integer.parseInt(numberMatcher.group(1));
            if (ACallCommand.isActive || ASMSCommand.isActive) {
                e.setCancelled(true);
                ACallCommand.isActive = ASMSCommand.isActive = false;
            }
            return;
        }

        Matcher mobileSmsMatcher = PatternHandler.MOBILE_SMS_PATTERN.matcher(msg);
        if (mobileSmsMatcher.find()) {
            lastCheckedNumber = Integer.parseInt(mobileSmsMatcher.group(2));
        }
    }
}