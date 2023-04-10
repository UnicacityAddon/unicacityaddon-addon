package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
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
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();
        long currentTime = System.currentTimeMillis();

        Matcher contractSetMatcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(msg);
        if (contractSetMatcher.find()) {
            this.unicacityAddon.soundController().playContractSetSound();
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

            this.unicacityAddon.soundController().playContractFulfilledSound();
            CONTRACT_LIST.remove(name);

            if (msg.contains("getötet") && msg.contains(p.getName())) {
                this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage(this.unicacityAddon);
            }
            return;
        }

        Matcher contractListHeaderMatcher = PatternHandler.CONTRACT_LIST_HEADER_PATTERN.matcher(msg);
        if (contractListHeaderMatcher.find()) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        Matcher contractListMatcher = PatternHandler.CONTRACT_LIST_PATTERN.matcher(msg);
        if (contractListMatcher.find() && currentTime - hitlistShown < 5000L) {
            String name = contractListMatcher.group("name");
            CONTRACT_LIST.add(name);
        }
    }
}