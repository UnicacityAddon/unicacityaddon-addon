package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.updater.Updater;
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class AccountEventHandler {

    public static boolean isAfk = false;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (!UnicacityAddon.isUnicacity()) return;

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(msg).find()) {
            MobileEventHandler.activeCommunicationsCheck = true;
            AccountEventHandler.isAfk = false;
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
            e.setCanceled(true);
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
        if (accountPayDayMatcher.find())
            PayDayModule.setTime(Integer.parseInt(accountPayDayMatcher.group(1)));
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
                p.sendChatMessage("/mobile");

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
                        Updater.updateChecker();
                    }
                }, 2000);
            }
        }, 1000);
    }
}