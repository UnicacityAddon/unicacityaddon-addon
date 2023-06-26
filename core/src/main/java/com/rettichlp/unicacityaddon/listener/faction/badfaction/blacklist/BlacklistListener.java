package com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.BlacklistInfoCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class BlacklistListener {

    private static long blacklistShown;

    private final UnicacityAddon unicacityAddon;

    public BlacklistListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher blacklistAddedMatcher = PatternHandler.BLACKLIST_ADDED_PATTERN.matcher(msg);
        if (blacklistAddedMatcher.find()) {
            String name = blacklistAddedMatcher.group(1);

            Map<String, Boolean> blacklistMap = this.unicacityAddon.nameTagService().getBlacklistPlayerMap();
            blacklistMap.put(name, false);
            this.unicacityAddon.nameTagService().setBlacklistPlayerMap(blacklistMap);

            return;
        }

        Matcher blacklistRemovedMatcher = PatternHandler.BLACKLIST_REMOVED_PATTERN.matcher(msg);
        if (blacklistRemovedMatcher.find()) {
            String name = blacklistRemovedMatcher.group(1);

            Map<String, Boolean> blacklistMap = this.unicacityAddon.nameTagService().getBlacklistPlayerMap();
            blacklistMap.remove(name);
            this.unicacityAddon.nameTagService().setBlacklistPlayerMap(blacklistMap);

            return;
        }

        Matcher blacklistStartMatcher = PatternHandler.BLACKLIST_START_PATTERN.matcher(msg);
        if (blacklistStartMatcher.find()) {
            this.unicacityAddon.nameTagService().setBlacklistPlayerMap(new HashMap<>());
            blacklistShown = System.currentTimeMillis();
            return;
        }

        Matcher blacklistListMatcher = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg);
        if (blacklistListMatcher.find() && System.currentTimeMillis() - blacklistShown < 1000) {
            String name = blacklistListMatcher.group(1);
            String reason = blacklistListMatcher.group(2);

            Map<String, Boolean> blacklistMap = this.unicacityAddon.nameTagService().getBlacklistPlayerMap();
            blacklistMap.put(name, reason.toLowerCase().contains("vogelfrei"));
            this.unicacityAddon.nameTagService().setBlacklistPlayerMap(blacklistMap);

            // BlacklistInfoCommand: hide all but one
            if (System.currentTimeMillis() - BlacklistInfoCommand.executedTime < 1000 && !name.equalsIgnoreCase(BlacklistInfoCommand.target))
                e.setCancelled(true);
        }
    }
}