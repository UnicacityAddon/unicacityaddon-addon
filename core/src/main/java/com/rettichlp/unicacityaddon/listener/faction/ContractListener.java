package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
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
public class ContractListener {

    public static final List<String> CONTRACT_LIST = new ArrayList<>();
    private static long hitlistShown;

    private final UnicacityAddon unicacityAddon;

    public ContractListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();
        long currentTime = System.currentTimeMillis();

        Matcher contractSetMatcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(msg);
        if (contractSetMatcher.find()) {
            // TODO: 10.12.2022 p.playSound(SoundRegistry.CONTRACT_SET_SOUND, 1, 1);
            String name = contractSetMatcher.group(1);
            CONTRACT_LIST.add(name);
            return;
        }

        Matcher contractRemovedMatcher = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(msg);
        if (contractRemovedMatcher.find()) {
            String name = null;
            for (int i = 1; i < contractRemovedMatcher.groupCount() + 1; i++) {
                String tempName = contractRemovedMatcher.group(i);
                if (tempName == null) continue;

                name = tempName;
                break;
            }

            // TODO: 10.12.2022 p.playSound(SoundRegistry.CONTRACT_FULFILLED_SOUND, 1, 1);
            CONTRACT_LIST.remove(name);

            if (msg.contains("getötet") && msg.contains(p.getName())) {
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage();
            }
            return;
        }

        // TODO TRANSFORM TO REGEX
        if (msg.equals("=~=~=~Contracts~=~=~=")) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        // TODO TRANSFORM TO REGEX
        if (currentTime - hitlistShown < 5000L && msg.startsWith(" - ") && msg.contains("$")) {
            String[] splittedMessage = msg.split(" ");
            String name = ForgeUtils.stripPrefix(splittedMessage[1]);
            CONTRACT_LIST.add(name);
        }
    }
}