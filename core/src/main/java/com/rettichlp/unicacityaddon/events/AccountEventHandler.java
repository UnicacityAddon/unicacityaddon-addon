package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.UpdateUtils;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class AccountEventHandler {

    public static boolean isAfk = false;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        if (!UnicacityAddon.isUnicacity()) return;

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(msg).find()) {
            MobileEventHandler.activeCommunicationsCheck = true;
            isAfk = false;
            return;
        }

        if (PatternHandler.ACCOUNT_PASSWORD_UNPROTECTED_PATTERN.matcher(msg).find()) {
            handleJoin();
            return;
        }

        if (PatternHandler.ACCOUNT_PASSWORD_UNLOCKED_PATTERN.matcher(msg).find()) {
            handleJoin();
            return;
        }

        if (PatternHandler.ACCOUNT_PASSWORD_PROTECTED_PATTERN.matcher(msg).find()) {
            handleUnlockAccount();
            return;
        }

        if (PatternHandler.RESOURCEPACK_PATTERN.matcher(msg).find() && ConfigElements.getRemoveResourcePackMessage()) {
            e.setCancelled(true);
            return;
        }

        if (PatternHandler.ACCOUNT_AFK_TRUE_PATTERN.matcher(msg).find()) {
            isAfk = true;
            return;
        }

        if (PatternHandler.ACCOUNT_AFK_FALSE_PATTERN.matcher(msg).find()) {
            isAfk = false;
            return;
        }

        Matcher accountPayDayMatcher = PatternHandler.ACCOUNT_PAYDAY_PATTERN.matcher(msg);
//        if (accountPayDayMatcher.find())
//            PayDayModule.setTime(Integer.parseInt(accountPayDayMatcher.group(1)));
    }

    private void handleUnlockAccount() {
        if (ConfigElements.getPasswordAutomation())
            AbstractionLayer.getPlayer().sendChatMessage("/passwort " + ConfigElements.getPassword());
    }

    private void handleJoin() {
        UPlayer p = AbstractionLayer.getPlayer();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // MOBILEEVENTHANDLER
                p.sendChatMessage("/togglephone");

                // AUTOMATE_COMMAND_SETTINGS
                if (ConfigElements.getCommandAutomation()) {
                    // AUTOMATE_COMMAND_FIRST_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getFirstCommand().isEmpty())
                                p.sendChatMessage(ConfigElements.getFirstCommand());
                        }
                    }, 500);

                    // AUTOMATE_COMMAND_SECOND_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getSecondCommand().isEmpty())
                                p.sendChatMessage(ConfigElements.getSecondCommand());
                        }
                    }, 1000);

                    // AUTOMATE_COMMAND_THIRD_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getThirdCommand().isEmpty())
                                p.sendChatMessage(ConfigElements.getThirdCommand());
                        }
                    }, 1500);
                }

                // UPDATECHECKER
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        UpdateUtils.updateChecker();
                    }
                }, 2000);
            }
        }, 1000);
    }
}