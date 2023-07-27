package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.statistic.GamePlay;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.config.join.CommandConfiguration;
import com.rettichlp.unicacityaddon.base.config.join.PasswordConfiguration;
import com.rettichlp.unicacityaddon.base.events.BankRobStartedEvent;
import com.rettichlp.unicacityaddon.base.events.BombPlantedEvent;
import com.rettichlp.unicacityaddon.base.events.HearthChangeEvent;
import com.rettichlp.unicacityaddon.base.events.MaskPutOnEvent;
import com.rettichlp.unicacityaddon.base.events.MaskRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.MaskInfoCommand;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.Arrays;
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
    private long lastDamageTime = 0;

    private final UnicacityAddon unicacityAddon;

    public AccountListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        ChatMessage chatMessage = e.chatMessage();
        String msg = chatMessage.getPlainText();
        String formattedMsg = this.unicacityAddon.utilService().text().legacy(chatMessage.originalComponent());

        if (!this.unicacityAddon.utilService().isUnicacity())
            return;

        if (PatternHandler.ACCOUNT_WELCOME_BACK_PATTERN.matcher(msg).find()) {
            MobileListener.activeCommunicationsCheck = true;
            isAfk = false;
            return;
        }

        if (PatternHandler.ACCOUNT_PASSWORD_UNPROTECTED_PATTERN.matcher(msg).find() || PatternHandler.ACCOUNT_PASSWORD_UNLOCKED_PATTERN.matcher(msg).find()) {
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
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/afk");
                    lastAfkTry = System.currentTimeMillis();
                }
            }, new Date(this.lastDamageTime + TimeUnit.SECONDS.toMillis(15)));
            return;
        }

        Matcher accountFriendJoinMatcher = PatternHandler.ACCOUNT_FRIEND_JOIN_PATTERN.matcher(msg);
        if (accountFriendJoinMatcher.find()) {
            String name = accountFriendJoinMatcher.group("name");
            e.setMessage(Message.getBuilder()
                    .add(formattedMsg)
                    .space()
                    .of("[☎]").color(ColorCode.DARK_GREEN)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " anrufen").color(ColorCode.DARK_GREEN).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acall " + name)
                            .advance()
                    .space()
                    .of("[✉]").color(ColorCode.GOLD)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("SMS an " + name + " senden").color(ColorCode.GOLD).advance().createComponent())
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/asms " + name + " ")
                            .advance()
                    .space()
                    .of("[✕]").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " aus der Freundesliste entfernen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/fl delete " + name)
                            .advance()
                    .createComponent());
            return;
        }

        Matcher accountFriendLeaveMatcher = PatternHandler.ACCOUNT_FRIEND_LEAVE_PATTERN.matcher(msg);
        if (accountFriendLeaveMatcher.find()) {
            String name = accountFriendLeaveMatcher.group("name");
            e.setMessage(Message.getBuilder()
                    .add(formattedMsg)
                    .space()
                    .of("[✕]").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " aus der Freundesliste entfernen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/fl delete " + name)
                            .advance()
                    .createComponent());
            return;
        }


        if (PatternHandler.ACCOUNT_TREUEBONUS_PATTERN.matcher(msg).find()) {
            new Thread(() -> {
                GamePlay gamePlay = this.unicacityAddon.api().sendStatisticRequest().getGameplay();

                p.sendMessage(Message.getBuilder()
                        .space().space()
                        .of("-").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Tode").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(gamePlay.getDeaths() + " Tode").color(ColorCode.RED).advance()
                        .createComponent());

                p.sendMessage(Message.getBuilder()
                        .space().space()
                        .of("-").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Kills").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(gamePlay.getKills() + " Kills").color(ColorCode.RED).advance()
                        .createComponent());

                p.sendMessage(Message.getBuilder()
                        .space().space()
                        .of("-").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Spielzeit").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(gamePlay.getPlayTime() + (gamePlay.getPlayTime() == 1 ? " Stunde" : " Stunden")).color(ColorCode.RED).advance()
                        .createComponent());
            }).start();
            return;
        }

        if (PatternHandler.ACCOUNT_MASK_ON_PATTERN.matcher(msg).find()) {
            Laby.labyAPI().eventBus().fire(new MaskPutOnEvent());
            MaskInfoCommand.startTime = System.currentTimeMillis();
            return;
        }

        if (PatternHandler.ACCOUNT_MASK_OFF_PATTERN.matcher(msg).find()) {
            Laby.labyAPI().eventBus().fire(new MaskRemovedEvent());
            MaskInfoCommand.startTime = 0;
            return;
        }

        Matcher accountPayDayMatcher = PatternHandler.ACCOUNT_PAYDAY_PATTERN.matcher(msg);
        if (accountPayDayMatcher.find())
            this.unicacityAddon.fileService().data().setPayDayTime(Integer.parseInt(accountPayDayMatcher.group(1)));
    }

    @Subscribe
    public void onOfflineDataChanged(OfflineDataChangedEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        switch (e.getData().getPayDayTime()) {
            case 0, 56, 58 -> isMessageLocked = false;
            case 55 -> {
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 5 Minuten deinen PayDay.");
                    isMessageLocked = true;
                }
            }
            case 57 -> {
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 3 Minuten deinen PayDay.");
                    isMessageLocked = true;
                }
            }
            case 59 -> {
                if (!isMessageLocked) {
                    p.sendInfoMessage("Du hast in 1 Minute deinen PayDay.");
                    isMessageLocked = true;
                }
            }
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.MINUTE) && !isAfk) {
            this.unicacityAddon.fileService().data().addPayDayTime(1);
        }
    }

    @Subscribe
    public void onHearthChange(HearthChangeEvent e) {
        if (e.getType().equals(EventRegistrationListener.Type.HURT)) {
            this.lastDamageTime = e.getTime();
        }
    }

    private void handleUnlockAccount() {
        PasswordConfiguration passwordConfigurationSetting = this.unicacityAddon.configuration().password();
        String password = passwordConfigurationSetting.password().getOrDefault("");
        if (passwordConfigurationSetting.enabled().get() && !password.isBlank())
            this.unicacityAddon.player().sendServerMessage("/passwort " + password);
    }

    private void handleJoin() {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            try {
                Thread.sleep(1000);

                // MOBILEEVENTHANDLER
                p.sendServerMessage("/togglephone");

                Thread.sleep(100);

                // AUTOMATE_COMMAND_SETTINGS
                CommandConfiguration commandConfiguration = unicacityAddon.configuration().command();
                String command;

                // AUTOMATE_COMMAND_FIRST_SETTINGS
                command = commandConfiguration.first().get();
                if (commandConfiguration.enabled().get() && !command.isEmpty()) {
                    p.sendServerMessage(command);
                    this.unicacityAddon.logger().info("Sent first automated command: {}", command);
                    Thread.sleep(100);
                }
                // AUTOMATE_COMMAND_SECOND_SETTINGS
                command = commandConfiguration.second().get();
                if (commandConfiguration.enabled().get() && !command.isEmpty()) {
                    p.sendServerMessage(commandConfiguration.second().get());
                    this.unicacityAddon.logger().info("Sent second automated command: {}", command);
                    Thread.sleep(100);
                }
                // AUTOMATE_COMMAND_THIRD_SETTINGS
                command = commandConfiguration.third().get();
                if (commandConfiguration.enabled().get() && !command.isEmpty()) {
                    p.sendServerMessage(command);
                    this.unicacityAddon.logger().info("Sent third automated command: {}", command);
                    Thread.sleep(100);
                }

                // FORCE ENABLE HIT SOUNDS
                if (this.unicacityAddon.configuration().hitSound().get()) {
                    ScreenRenderListener.settingPath = new ArrayList<>(Arrays.asList(1, 2));
                    p.sendServerMessage("/settings");
                    this.unicacityAddon.logger().info("Force activate hit sounds");
                    Thread.sleep(1000);
                }

                // LOAD MAJOR EVENT TIME
                AccountListener.this.unicacityAddon.utilService().debug("Loading major event time");
                long bankRobTime = AccountListener.this.unicacityAddon.api().sendEventRequest().getBankRob();
                Laby.labyAPI().eventBus().fire(new BankRobStartedEvent(bankRobTime));
                long bombTime = AccountListener.this.unicacityAddon.api().sendEventRequest().getBomb();
                Laby.labyAPI().eventBus().fire(new BombPlantedEvent(bombTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}