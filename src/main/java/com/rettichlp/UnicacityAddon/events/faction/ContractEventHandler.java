package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
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
            NameTagEventHandler.refreshAllDisplayNames();
            return;
        }

        // TODO TRANSFORM TO REGEX
        if (currentTime - hitlistShown > 5000L || !unformattedMessage.startsWith(" - ")) return;
        if (!unformattedMessage.contains("$")) return;

        String[] splittedMessage = StringUtils.split(unformattedMessage, " ");
        String name = ForgeUtils.stripPrefix(splittedMessage[1]);

        CONTRACT_LIST.add(name);
        NameTagEventHandler.refreshAllDisplayNames(); // TODO: not all display names needs to update
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractSet(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_SET_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = matcher.group(1);

            System.out.println("Name: " + name);

            CONTRACT_LIST.add(name);
            NameTagEventHandler.refreshAllDisplayNames(); // TODO: not all display names needs to update
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onContractRemoved(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(unformattedMessage);
        if (!matcher.find()) return;

        String name = null;
        for (int i = 1; i < matcher.groupCount() + 1; i++) {
            String tempName = matcher.group(i);
            if (tempName == null) continue;

            name = tempName;
            break;
        }

        CONTRACT_LIST.remove(name);
        NameTagEventHandler.refreshAllDisplayNames(); // TODO: not all display names needs to update
    }
}