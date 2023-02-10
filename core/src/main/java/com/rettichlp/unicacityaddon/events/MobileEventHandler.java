package com.rettichlp.unicacityaddon.events;

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
public class MobileEventHandler {

    public static int lastCheckedNumber = 0;
    public static boolean isActive = false;
    public static boolean hasCommunications = false;
    public static boolean muted = false;
    public static boolean activeCommunicationsCheck;
    public static final List<String> blockedPlayerList = new ArrayList<>();
    private boolean blockNextMessage = false;
    private boolean whitelistSound = false;

    private final UnicacityAddon unicacityAddon;

    public MobileEventHandler(UnicacityAddon unicacityAddon) {
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
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        // blocks next SMS message (because SMS messages has two independent message parts)
        if (blockNextMessage && msg.matches("^(?:\\[UC])*\\w+: .*$")) {
            blockNextMessage = false;
            e.setCancelled(true);
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
        if (mobileTogglePattern.find() && activeCommunicationsCheck) {
            if (msg.contains("eingeschaltet")) {
                activeCommunicationsCheck = false;
                hasCommunications = true;
            } else {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendServerMessage("/togglephone");
                    }
                }, TimeUnit.SECONDS.toMillis(1));
            }
            e.setCancelled(true);
            return;
        }

        Matcher numberMatcher = PatternHandler.MOBILE_NUMBER_PATTERN.matcher(msg);
        if (numberMatcher.find()) {
            lastCheckedNumber = Integer.parseInt(numberMatcher.group(1));
            if (ACallCommand.isActive || ASMSCommand.isActive || isActive) {
                e.setCancelled(true);
                ACallCommand.isActive = ASMSCommand.isActive = isActive = false;
            }
        }

        Matcher mobileCallMatcher = PatternHandler.MOBILE_CALL_PATTERN.matcher(msg);
        if (mobileCallMatcher.find()) {
            if (blockedPlayerList.contains(mobileCallMatcher.group(1))) {
                e.setCancelled(true);
                return;
            }
            if (!muted) {
                whitelistSound = true;
                // TODO: 09.12.2022 p.playSound("record.cat");
            }
        }

        Matcher mobileSmsMatcher = PatternHandler.MOBILE_SMS_PATTERN.matcher(msg);
        if (mobileSmsMatcher.find()) {
            String playerName = mobileSmsMatcher.group(1);
            if (!AccountEventHandler.isAfk)
                p.sendServerMessage("/nummer " + playerName);
            isActive = true;
            if (blockedPlayerList.contains(playerName)) {
                blockNextMessage = true;
                e.setCancelled(true);
                return;
            }
            if (!muted) {
                whitelistSound = true;
                // TODO: 09.12.2022 p.playSound("entity.sheep.ambient");
            }
        }
    }

// TODO: 09.12.2022
//    /**
//     * To handle muted and blocked mobile settings, cancel all mobile sounds and send sound on client side if trigger message is received
//     */
//    @Subscribe
//    public void onPlaySound(PlaySoundEvent e) {
//        //The event.result starts off equal to event.sound, but could have been altered or set to null by another mod
//        if (e.getResult() != null) {
//            String name = e.getName();
//            if ((name.equals("record.cat") || name.equals("record.stal") || name.equals("entity.sheep.ambient")) && !whitelistSound) {
//                e.setResult(null);
//                e.setResultSound(null);
//                UnicacityAddon.LOGGER.info("Sound event cancelled: " + name);
//            } else whitelistSound = false;
//        }
//    }
}