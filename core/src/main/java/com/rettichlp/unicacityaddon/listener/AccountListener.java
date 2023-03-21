package com.rettichlp.unicacityaddon.listener;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.join.CommandSetting;
import com.rettichlp.unicacityaddon.base.config.join.PasswordSetting;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.MaskInfoCommand;
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
public class AccountListener {

    public static boolean isAfk = false;

    private boolean isMessageLocked = false;
    private long lastAfkTry = 0;

    private final UnicacityAddon unicacityAddon;

    public AccountListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        if (!UnicacityAddon.isUnicacity())
            return;

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(msg).find()) {
            MobileListener.activeCommunicationsCheck = true;
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

        if (PatternHandler.RESOURCEPACK_PATTERN.matcher(msg).find() && this.unicacityAddon.configuration().texturePack().get()) {
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

        if (PatternHandler.ACCOUNT_AFK_FAILURE_PATTERN.matcher(msg).find() && System.currentTimeMillis() - lastAfkTry > TimeUnit.SECONDS.toMillis(30)) {
            p.sendInfoMessage("Das Addon versucht dich anschließend in den AFK Modus zu setzen.");
            long lastDamageTime = TickListener.lastTickDamage.getKey();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/afk");
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
                    float kd = gameplayJsonObject.get("kd").getAsFloat();
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
                            .of("K/D").color(ColorCode.GOLD).advance()
                            .of(":").color(ColorCode.DARK_GRAY).advance().space()
                            .of(MathUtils.DECIMAL_FORMAT.format(kd)).color(ColorCode.RED).advance()
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

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        switch (e.getData().getPayDayTime()) {
            case 0:
            case 56:
            case 58:
                isMessageLocked = false;
                break;
            case 55:
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 5 Minuten deinen PayDay.");
                    isMessageLocked = true;
                }
                break;
            case 57:
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 3 Minuten deinen PayDay.");
                    isMessageLocked = true;
                }
                break;
            case 59:
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 1 Minute deinen PayDay.");
                    isMessageLocked = true;
                }
                break;
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.MINUTE) && !isAfk) {
            FileManager.DATA.addPayDayTime(1);
        }
    }

    private void handleUnlockAccount() {
        PasswordSetting passwordSetting = this.unicacityAddon.configuration().passwordSetting();
        if (passwordSetting.enabled().get())
            UnicacityAddon.PLAYER.sendServerMessage("/passwort " + passwordSetting.password().getOrDefault(""));
    }

    private void handleJoin() {
        AddonPlayer p = UnicacityAddon.PLAYER;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // MOBILEEVENTHANDLER
                p.sendServerMessage("/togglephone");

                // AUTOMATE_COMMAND_SETTINGS
                CommandSetting commandSetting = unicacityAddon.configuration().commandSetting();
                if (commandSetting.enabled().get()) {
                    // AUTOMATE_COMMAND_FIRST_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.first().getOrDefault("").isEmpty())
                                p.sendServerMessage(commandSetting.first().get());
                        }
                    }, 500);

                    // AUTOMATE_COMMAND_SECOND_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.second().getOrDefault("").isEmpty())
                                p.sendServerMessage(commandSetting.second().get());
                        }
                    }, 1000);

                    // AUTOMATE_COMMAND_THIRD_SETTINGS
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!commandSetting.third().getOrDefault("").isEmpty())
                                p.sendServerMessage(commandSetting.third().get());
                        }
                    }, 1500);
                }
            }
        }, 1000);
    }
}