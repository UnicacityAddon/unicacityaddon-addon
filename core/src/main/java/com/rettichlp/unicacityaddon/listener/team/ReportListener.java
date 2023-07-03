package com.rettichlp.unicacityaddon.listener.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.config.message.MessageConfiguration;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * @author Dimiikou
 */
@UCEvent
public class ReportListener {

    private static final Timer t = new Timer();
    private final Pattern urlPattern = Pattern.compile(
            //        schema                       ipv4            OR      namespace              port     path       ends
            //   |-------------|        |-------------------------|  |--------------------|    |---------| |--|   |----------|
            "((?:[a-z0-9]{2,}://)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|([-\\w_]+\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))",
            Pattern.CASE_INSENSITIVE);
    private long lastExecution = -1;
    private boolean isReport = false;

    private final UnicacityAddon unicacityAddon;

    public ReportListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        ChatMessage chatMessage = e.chatMessage();
        String msg = chatMessage.getPlainText();
        MessageConfiguration messageConfiguration = this.unicacityAddon.configuration().message();

        if (PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(msg).find()) {
            isReport = true;

            if (messageConfiguration.greeting().getOrDefault("").isEmpty()) {
                return;
            }

            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastExecution > 1000L) {
                        p.sendServerMessage(messageConfiguration.greeting().get());
                        lastExecution = System.currentTimeMillis();
                    }
                }
            }, 1000);

            return;
        }

        if (PatternHandler.REPORT_END_PATTERN.matcher(msg).find()) {
            isReport = false;
            return;
        }

        if (chatMessage.getFormattedText().startsWith(ColorCode.DARK_PURPLE.getCode()) && isReport) {
            Message.Builder messageBuilder = Message.getBuilder()
                    .add(messageConfiguration.prefix().getOrDefault("").replaceAll("&", "§"));

            Arrays.stream(msg.split(" ")).forEach(s -> {
                if (urlPattern.matcher(s).find())
                    messageBuilder
                            .of(s).color(ColorCode.BLUE)
                                    .underline()
                                    .clickEvent(ClickEvent.Action.OPEN_URL, s.startsWith("http") ? s : "http://" + s)
                                    .advance()
                            .space();
                else
                    messageBuilder
                            .of(s).color(ColorCode.DARK_PURPLE).advance()
                            .space();
            });

            e.setMessage(messageBuilder.createComponent());
        }

        if (PatternHandler.REPORT_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.soundController().playReportSound();
        }
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        Key key = e.getKey();
        HotkeyConfiguration hotkeyConfiguration = e.hotkeyConfiguration();

        if (e.isRealIngame()) {
            if (key.equals(hotkeyConfiguration.acceptReport().get())) {
                p.sendServerMessage("/ar");
            } else if (key.equals(hotkeyConfiguration.cancelReport().get())) {
                String farewell = this.unicacityAddon.configuration().message().farewell().get();
                if (!farewell.isEmpty())
                    p.sendServerMessage(farewell);
                p.sendServerMessage("/cr");
            }
        }
    }
}