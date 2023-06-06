package com.rettichlp.unicacityaddon.listener.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class AdListener {

    private String adIssuer;
    private long adTime;

    private final UnicacityAddon unicacityAddon;

    public AdListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        Matcher matcher = PatternHandler.AD_CONTROL_PATTERN.matcher(e.chatMessage().getPlainText());
        if (matcher.find()) {
            this.adIssuer = matcher.group(1);
            this.adTime = System.currentTimeMillis();
        }
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        Key key = e.key();
        HotkeySetting hotkeySetting = e.hotkeySetting();
        if (key.equals(hotkeySetting.acceptAd().get())) {
            this.handleAd("freigeben");
        } else if (key.equals(hotkeySetting.declineAd().get())) {
            this.handleAd("blockieren");
        }
    }

    private void handleAd(String type) {
        if (this.adIssuer != null && System.currentTimeMillis() - this.adTime < TimeUnit.SECONDS.toMillis(20)) {
            this.unicacityAddon.player().sendServerMessage("/adcontrol " + this.adIssuer + " " + type);
            this.adIssuer = null;
        }
    }
}