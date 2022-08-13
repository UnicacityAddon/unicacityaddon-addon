package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.BlacklistInfoCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class BlacklistInfoEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (System.currentTimeMillis() - BlacklistInfoCommand.executedTime > 1000) return false;

        String msg = e.getMessage().getUnformattedText();
        Matcher blacklistMatcher = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg);
        if (!blacklistMatcher.find()) return false;

        if (!blacklistMatcher.group(1).equalsIgnoreCase(BlacklistInfoCommand.target)) e.setCanceled(true);

        return false;
    }
}
