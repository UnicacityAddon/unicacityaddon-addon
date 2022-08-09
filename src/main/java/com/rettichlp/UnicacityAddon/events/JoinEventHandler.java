package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
public class JoinEventHandler {

    private static boolean accountLocked = false;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (e.getMessage().getUnformattedText().equals("  Info: Gebe bitte dein Passwort ein. /passwort [Passwort]")) {
            accountLocked = true;
            if (ConfigElements.getPasswordAutomation() && (ConfigElements.getPassword() != null))
                AbstractionLayer.getPlayer().sendChatMessage("/passwort " + ConfigElements.getPassword());

            return false;
        }

        UPlayer p = AbstractionLayer.getPlayer();

        if (PatternHandler.ACCOUNT_UNLOCKED_PATTERN.matcher(e.getMessage().getUnformattedText()).find()) {
            accountLocked = false;
            if (!ConfigElements.getCommandAutomation()) return false;
            Timer t = new Timer();

            if (!ConfigElements.getFirstCommand().isEmpty()) p.sendChatMessage(ConfigElements.getFirstCommand());

            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!ConfigElements.getSecondCommand().isEmpty()) p.sendChatMessage(ConfigElements.getSecondCommand());
                }
            }, 1000L);

            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!ConfigElements.getThirdCommand().isEmpty()) p.sendChatMessage(ConfigElements.getThirdCommand());
                }
            }, 2000L);

        }

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(e.getMessage().getUnformattedText()).find()) {
            Timer t = new Timer();

            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    if (!accountLocked && ConfigElements.getCommandAutomation()) {
                        if (!ConfigElements.getFirstCommand().isEmpty())
                            p.sendChatMessage(ConfigElements.getFirstCommand());

                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!ConfigElements.getSecondCommand().isEmpty())
                                    p.sendChatMessage(ConfigElements.getSecondCommand());
                            }
                        }, 1000L);

                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!ConfigElements.getThirdCommand().isEmpty())
                                    p.sendChatMessage(ConfigElements.getThirdCommand());
                            }
                        }, 2000L);
                    }
                }
            };
            t.schedule(tt, 200);

        }

        return false;
    }
}