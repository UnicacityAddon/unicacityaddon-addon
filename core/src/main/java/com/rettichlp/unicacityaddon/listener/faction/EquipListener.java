package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class EquipListener {

    private final UnicacityAddon unicacityAddon;

    public EquipListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        if (equipMatcher.find()) {
            String equipString = equipMatcher.group(1);

            Optional<Equip> equipOptional = Arrays.stream(Equip.values())
                    .filter(eq -> eq.getMessageName().equalsIgnoreCase(equipString))
                    .findAny();

            if (equipOptional.isPresent()) {
                this.unicacityAddon.fileService().data().addEquipToEquipMap(equipOptional.get());
            } else {
                this.unicacityAddon.player().sendErrorMessage("Equip wurde nicht gefunden.");
            }
            return;
        }

        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (trackerMatcher.find()) {
            this.unicacityAddon.fileService().data().addEquipToEquipMap(Equip.TRACKER);
        }
    }
}