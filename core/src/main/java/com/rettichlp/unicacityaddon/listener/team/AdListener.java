package com.rettichlp.unicacityaddon.listener.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class AdListener {

    private static String adIssuer;
    private static long adTime;

    private final UnicacityAddon unicacityAddon;

    public AdListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e)  {
        Matcher matcher = PatternHandler.AD_CONTROL_PATTERN.matcher(e.chatMessage().getPlainText());
        if (matcher.find()) {
            adIssuer = matcher.group(1);
            adTime = System.currentTimeMillis();
        }
    }

    public static void handleAd(String type, UnicacityAddon unicacityAddon) {
        if (adIssuer != null && System.currentTimeMillis() - adTime < TimeUnit.SECONDS.toMillis(20)) {
            unicacityAddon.player().sendServerMessage("/adcontrol " + adIssuer + " " + type);
            adIssuer = null;
        }
    }
}