package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class KarmaMessageEventHandler {

    private boolean karmaCheck = false;
    private int karma;
    private int finalKarma;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (PaydayEventHandler.isAfk) return false;
        Matcher karmaChangedPattern = PatternHandler.KARMA_CHANGED_PATTERN.matcher(msg);
        if (karmaChangedPattern.find()) {
            p.sendChatMessage("/karma");
            karmaCheck = true;
            e.setCanceled(true);

            karma = Integer.parseInt(karmaChangedPattern.group(1));
            return false;
        }

        if (!karmaCheck) return false;
        Matcher karmaMatcher = PatternHandler.KARMA_PATTERN.matcher(msg);
        if (!karmaMatcher.find()) return false;

        e.setMessage(Message.getBuilder().of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Karma").color(ColorCode.BLUE).advance()
                    .of("] ").color(ColorCode.DARK_GRAY).advance()
                    .of("" + karma).color(ColorCode.AQUA).advance().space()
                    .of("Karma ").color(ColorCode.AQUA).advance()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(karmaMatcher.group(1)).color(ColorCode.AQUA).advance()
                    .of("/").color(ColorCode.DARK_GRAY).advance()
                    .of("100").color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
        karmaCheck = false;
        return false;
    }
}
