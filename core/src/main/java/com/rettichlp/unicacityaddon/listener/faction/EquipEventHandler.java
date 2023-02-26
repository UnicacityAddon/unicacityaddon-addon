package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class EquipEventHandler {

    private final UnicacityAddon unicacityAddon;

    public EquipEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (equipMatcher.find() || trackerMatcher.find()) {
            Equip equip = Arrays.stream(Equip.values())
                    .filter(equip1 -> msg.contains(equip1.getMessageName()))
                    .findFirst()
                    .orElse(null);

            if (equip != null) {
//                FileManager.DATA.addEquipToEquipMap(equip);
            } else {
                UnicacityAddon.PLAYER.sendErrorMessage("Equip wurde nicht gefunden.");
            }
        }
    }
}