package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.updater.Updater;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCEvent
public class JoinEventHandler {

    private static boolean accountLocked = false;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (!UnicacityAddon.isUnicacity()) return false;

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(msg).find()) {
            MobileEventHandler.activeCommunicationsCheck = true;
            PayDayEventHandler.isAfk = false;
            handleJoin();
            return false;
        }

        if (PatternHandler.ACCOUNT_LOCKED_PATTERN.matcher(msg).find()) {
            accountLocked = true;
            handleUnlockAccount();
        }

        if (PatternHandler.ACCOUNT_UNLOCKED_PATTERN.matcher(msg).find()) {
            accountLocked = false;
            handleJoin();
        }

        if (ConfigElements.getRemoveResourcePackMessage() && PatternHandler.RESOURCEPACK_PATTERN.matcher(msg).find()) e.setCanceled(true);

        return false;
    }

    private void handleUnlockAccount() {
        if (!accountLocked) return;
        if (ConfigElements.getPasswordAutomation() && (ConfigElements.getPassword() != null))
            AbstractionLayer.getPlayer().sendChatMessage("/passwort " + ConfigElements.getPassword());
    }

    private void handleJoin() {
        UPlayer p = AbstractionLayer.getPlayer();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (accountLocked) return;

                // MOBILEEVENTHANDLER
                p.sendChatMessage("/mobile");

                // AUTOMATE_COMMAND_SETTINGS
                if (ConfigElements.getCommandAutomation()) {
                    // AUTOMATE_COMMAND_FIRST_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getFirstCommand().isEmpty()) p.sendChatMessage(ConfigElements.getFirstCommand());
                        }
                    }, 500);

                    // AUTOMATE_COMMAND_SECOND_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getSecondCommand().isEmpty()) p.sendChatMessage(ConfigElements.getSecondCommand());
                        }
                    }, 1000);

                    // AUTOMATE_COMMAND_THIRD_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!ConfigElements.getThirdCommand().isEmpty()) p.sendChatMessage(ConfigElements.getThirdCommand());
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