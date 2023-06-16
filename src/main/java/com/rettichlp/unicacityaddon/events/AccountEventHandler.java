package com.rettichlp.unicacityaddon.events;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.UpdateUtils;
import com.rettichlp.unicacityaddon.commands.MaskInfoCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 */
@UCEvent
public class AccountEventHandler {

    public static boolean isAfk = false;

    private long lastAfkTry = 0;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();
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

        if (PatternHandler.ACCOUNT_AFK_FAILURE_PATTERN.matcher(msg).find() && System.currentTimeMillis() - lastAfkTry > TimeUnit.SECONDS.toMillis(30)) {
            p.sendInfoMessage("Das Addon versucht dich anschließend in den AFK Modus zu setzen.");
            long lastDamageTime = TickEventHandler.lastTickDamage.getKey();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/afk");
                    lastAfkTry = System.currentTimeMillis();
                }
            }, new Date(lastDamageTime + TimeUnit.SECONDS.toMillis(15)));
            return;
        }

        if (PatternHandler.ACCOUNT_TREUEBONUS_PATTERN.matcher(msg).find()) {
            new Thread(() -> {
                try {
                    JsonObject gameplayJsonObject = APIRequest.sendStatisticRequest().getAsJsonObject("gameplay");
                    int deaths = gameplayJsonObject.get("deaths").getAsInt();
                    int kills = gameplayJsonObject.get("kills").getAsInt();
                    int playTime = gameplayJsonObject.get("playTime").getAsInt();

                    p.sendMessage(Message.getBuilder()
                            .space().space()
                            .of("-").color(ColorCode.DARK_GRAY).advance().space()
                            .of("Tode").color(ColorCode.GOLD).advance()
                            .of(":").color(ColorCode.DARK_GRAY).advance().space()
                            .of(deaths + " Tode").color(ColorCode.RED).advance()
                            .createComponent());

                    p.sendMessage(Message.getBuilder()
                            .space().space()
                            .of("-").color(ColorCode.DARK_GRAY).advance().space()
                            .of("Kills").color(ColorCode.GOLD).advance()
                            .of(":").color(ColorCode.DARK_GRAY).advance().space()
                            .of(kills + " Kills").color(ColorCode.RED).advance()
                            .createComponent());

                    p.sendMessage(Message.getBuilder()
                            .space().space()
                            .of("-").color(ColorCode.DARK_GRAY).advance().space()
                            .of("Spielzeit").color(ColorCode.GOLD).advance()
                            .of(":").color(ColorCode.DARK_GRAY).advance().space()
                            .of(playTime + (playTime == 1 ? " Stunde" : " Stunden")).color(ColorCode.RED).advance()
                            .createComponent());
                } catch (APIResponseException ex) {
                    ex.sendInfo();
                }
            }).start();
            return;
        }

        if (PatternHandler.ACCOUNT_MASK_ON_PATTERN.matcher(msg).find()) {
            MaskInfoCommand.startTime = System.currentTimeMillis();
            return;
        }

        if (PatternHandler.ACCOUNT_MASK_OFF_PATTERN.matcher(msg).find()) {
            MaskInfoCommand.startTime = 0;
            return;
        }

        Matcher accountPayDayMatcher = PatternHandler.ACCOUNT_PAYDAY_PATTERN.matcher(msg);
        if (accountPayDayMatcher.find())
            FileManager.DATA.setPayDayTime(Integer.parseInt(accountPayDayMatcher.group(1)));
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
                        UpdateUtils.update(false);
                    }
                }, 2000);
            }
        }, 1000);
    }
}