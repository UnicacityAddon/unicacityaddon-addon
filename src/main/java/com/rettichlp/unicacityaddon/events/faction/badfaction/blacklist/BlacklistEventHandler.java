package com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.BlacklistInfoCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class BlacklistEventHandler {

    public static final Map<String, Boolean> BLACKLIST_MAP = new HashMap<>();

    private static long blacklistShown;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlacklistShown(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher blacklistAddedMatcher = PatternHandler.BLACKLIST_ADDED_PATTERN.matcher(msg);
        if (blacklistAddedMatcher.find()) {
            String name = blacklistAddedMatcher.group(1);
            BLACKLIST_MAP.put(name, false);
            return;
        }

        Matcher blacklistRemovedMatcher = PatternHandler.BLACKLIST_REMOVED_PATTERN.matcher(msg);
        if (blacklistRemovedMatcher.find()) {
            String name = blacklistRemovedMatcher.group(1);
            BLACKLIST_MAP.remove(name);
            return;
        }

        Matcher blacklistStartMatcher = PatternHandler.BLACKLIST_START_PATTERN.matcher(msg);
        if (blacklistStartMatcher.find()) {
            BLACKLIST_MAP.clear();
            blacklistShown = System.currentTimeMillis();
            return;
        }

        Matcher blacklistListMatcher = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg);
        if (blacklistListMatcher.find() && System.currentTimeMillis() - blacklistShown < 1000) {
            String name = blacklistListMatcher.group(1);
            String reason = blacklistListMatcher.group(2);
            BLACKLIST_MAP.put(name, reason.toLowerCase().contains("vogelfrei"));

            // BlacklistInfoCommand: hide all but one
            if (System.currentTimeMillis() - BlacklistInfoCommand.executedTime < 1000 && !name.equalsIgnoreCase(BlacklistInfoCommand.target))
                e.setCanceled(true);
        }
    }
}