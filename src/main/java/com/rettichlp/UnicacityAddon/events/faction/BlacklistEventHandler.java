package com.rettichlp.UnicacityAddon.events.faction;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.base.faction.blacklist.Blacklist;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
public class BlacklistEventHandler {

    public static Blacklist BLACKLIST;
    public static final Map<String, Boolean> BLACKLIST_MAP = new HashMap<>();

    private static long blacklistShown;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlacklistShown(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        long currentTime = System.currentTimeMillis();

        Matcher blacklistStartMatcher = PatternHandler.BLACKLIST_START_PATTERN.matcher(unformattedMessage);
        if (blacklistStartMatcher.find()) {
            BLACKLIST_MAP.clear();
            blacklistShown = currentTime;
            return;
        }

        if (currentTime - blacklistShown > 1000L) return;

        Matcher matcher = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = matcher.group(1);
            String reason = matcher.group(2);

            BLACKLIST_MAP.put(name, reason.toLowerCase().contains("vogelfrei"));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlacklistAdd(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.BLACKLIST_ADDED_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = matcher.group(1);

            BLACKLIST_MAP.put(name, false);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlacklistRemove(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.BLACKLIST_REMOVED_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = matcher.group(1);

            BLACKLIST_MAP.remove(name);
        }
    }

    public static void refreshBlacklistReasons() {
        try {
            File blacklistDataFile = FileManager.getBlacklistDataFile();
            if (blacklistDataFile == null) return;
            BufferedReader reader = Files.newBufferedReader(Paths.get(blacklistDataFile.getAbsolutePath()));
            Gson g = new Gson();
            BLACKLIST = g.fromJson(reader, Blacklist.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}