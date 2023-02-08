package com.rettichlp.unicacityaddon.events.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.message.ReportMessageSetting;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.client.component.event.ClickEvent;
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
@NoArgsConstructor
public class ReportEventHandler {

    private static final Timer t = new Timer();
    private long lastExecution = -1;
    private boolean isReport = false;
    private final Pattern urlPattern = Pattern.compile(
            //        schema                       ipv4            OR      namespace              port     path       ends
            //   |-------------|        |-------------------------|  |--------------------|    |---------| |--|   |----------|
            "((?:[a-z0-9]{2,}://)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|([-\\w_]+\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))",
            Pattern.CASE_INSENSITIVE);

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String unformattedMsg = e.chatMessage().getPlainText();
        ReportMessageSetting reportMessageSetting = UnicacityAddon.configuration.reportMessageSetting();

        if (PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(unformattedMsg).find()) {
            isReport = true;

            if (reportMessageSetting.greeting().getOrDefault("").isEmpty())
                return;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastExecution > 1000L) {
                        p.sendChatMessage(reportMessageSetting.greeting().get());
                        lastExecution = System.currentTimeMillis();
                    }
                }
            }, 1000);
            return;
        }

        if (PatternHandler.REPORT_END_PATTERN.matcher(unformattedMsg).find()) {
            isReport = false;
            return;
        }

        if (unformattedMsg.startsWith(ColorCode.DARK_PURPLE.getCode()) && isReport) {
            Message.Builder messageBuilder = Message.getBuilder()
                    .add(reportMessageSetting.prefix().getOrDefault("").replaceAll("&", "§"));

            Arrays.stream(unformattedMsg.split(" ")).forEach(s -> {
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

        if (PatternHandler.REPORT_PATTERN.matcher(unformattedMsg).find()) {
            // TODO: 09.12.2022 p.playSound(SoundRegistry.REPORT_SOUND, 1, 1);
        }
    }
}