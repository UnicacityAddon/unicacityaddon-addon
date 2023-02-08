package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
@NoArgsConstructor
public class ContractEventHandler {

    public static final List<String> CONTRACT_LIST = new ArrayList<>();
    private static long hitlistShown;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String unformattedMessage = e.chatMessage().getPlainText();
        long currentTime = System.currentTimeMillis();

        Matcher contractSetMatcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(unformattedMessage);
        if (contractSetMatcher.find()) {
            // TODO: 10.12.2022 AbstractionLayer.getPlayer().playSound(SoundRegistry.CONTRACT_SET_SOUND, 1, 1);
            String name = contractSetMatcher.group(1);
            CONTRACT_LIST.add(name);
            return;
        }

        Matcher contractRemovedMatcher = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(unformattedMessage);
        if (contractRemovedMatcher.find()) {
            String name = null;
            for (int i = 1; i < contractRemovedMatcher.groupCount() + 1; i++) {
                String tempName = contractRemovedMatcher.group(i);
                if (tempName == null) continue;

                name = tempName;
                break;
            }

            // TODO: 10.12.2022 AbstractionLayer.getPlayer().playSound(SoundRegistry.CONTRACT_FULFILLED_SOUND, 1, 1);
            CONTRACT_LIST.remove(name);
            return;
        }

        // TODO TRANSFORM TO REGEX
        if (unformattedMessage.equals("=~=~=~Contracts~=~=~=")) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        // TODO TRANSFORM TO REGEX
        if (currentTime - hitlistShown < 5000L && unformattedMessage.startsWith(" - ") && unformattedMessage.contains("$")) {
            String[] splittedMessage = unformattedMessage.split(" ");
            String name = ForgeUtils.stripPrefix(splittedMessage[1]);
            CONTRACT_LIST.add(name);
        }
    }
}