package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ContractEventHandler {

    public static final List<String> CONTRACT_LIST = new ArrayList<>();
    private static long hitlistShown;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHitlistShown(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        long currentTime = System.currentTimeMillis();

        if (unformattedMessage.equals("=~=~=~Contracts~=~=~=")) {
            CONTRACT_LIST.clear();
            hitlistShown = currentTime;
            return;
        }

        Matcher contractListMatcher = PatternHandler.CONTRACT_LIST_PATTERN.matcher(unformattedMessage);
        if (contractListMatcher.find() && currentTime - hitlistShown < 5000) {
            String name = contractListMatcher.group("name");
            CONTRACT_LIST.add(name);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractSet(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            AbstractionLayer.getPlayer().playSound(SoundRegistry.CONTRACT_SET_SOUND, 1, 1);
            String name = matcher.group(1);
            CONTRACT_LIST.add(name);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractRemoved(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(unformattedMessage);
        if (!matcher.find())
            return;

        String name = null;
        for (int i = 1; i < matcher.groupCount() + 1; i++) {
            String tempName = matcher.group(i);
            if (tempName == null)
                continue;

            name = tempName;
            break;
        }

        AbstractionLayer.getPlayer().playSound(SoundRegistry.CONTRACT_FULFILLED_SOUND, 1, 1);
        CONTRACT_LIST.remove(name);
    }
}