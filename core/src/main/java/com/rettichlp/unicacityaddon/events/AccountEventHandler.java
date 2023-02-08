package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.join.CommandSetting;
import com.rettichlp.unicacityaddon.base.config.join.PasswordSetting;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class AccountEventHandler {

    public static boolean isAfk = false;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.chatMessage().getPlainText();

        if (!UnicacityAddon.isUnicacity())
            return;

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

        if (PatternHandler.RESOURCEPACK_PATTERN.matcher(msg).find() && UnicacityAddon.configuration.texturePack().get()) {
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

        if (PatternHandler.ACCOUNT_AFK_FAILURE_PATTERN.matcher(msg).find()) {
            p.sendInfoMessage("Das Addon versucht dich anschließend in den AFK Modus zu setzen.");
            long lastDamageTime = TickEventHandler.lastTickDamage.getKey();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/afk");
                }
            }, new Date(lastDamageTime + TimeUnit.SECONDS.toMillis(15)));
            return;
        }

        Matcher accountPayDayMatcher = PatternHandler.ACCOUNT_PAYDAY_PATTERN.matcher(msg);
        if (accountPayDayMatcher.find())
            FileManager.DATA.setPayDayTime(Integer.parseInt(accountPayDayMatcher.group(1)));
    }

    private void handleUnlockAccount() {
        PasswordSetting passwordSetting = UnicacityAddon.configuration.passwordSetting();
        if (passwordSetting.enabled().get())
            AbstractionLayer.getPlayer().sendChatMessage("/passwort " + passwordSetting.password().getOrDefault(""));
    }

    private void handleJoin() {
        UPlayer p = AbstractionLayer.getPlayer();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // MOBILEEVENTHANDLER
                p.sendChatMessage("/mobile");

                // AUTOMATE_COMMAND_SETTINGS
                CommandSetting commandSetting = UnicacityAddon.configuration.commandSetting();
                if (commandSetting.enabled().get()) {
                    // AUTOMATE_COMMAND_FIRST_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.first().getOrDefault("").isEmpty())
                                p.sendChatMessage(commandSetting.first().get());
                        }
                    }, 500);

                    // AUTOMATE_COMMAND_SECOND_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.second().getOrDefault("").isEmpty())
                                p.sendChatMessage(commandSetting.second().get());
                        }
                    }, 1000);

                    // AUTOMATE_COMMAND_THIRD_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.third().getOrDefault("").isEmpty())
                                p.sendChatMessage(commandSetting.third().get());
                        }
                    }, 1500);
                }

//                // UPDATECHECKER
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        UpdateUtils.updateChecker();
//                    }
//                }, 2000);
            }
        }, 1000);
    }
}