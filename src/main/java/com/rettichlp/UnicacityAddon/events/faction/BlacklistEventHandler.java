package com.rettichlp.UnicacityAddon.events.faction;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.faction.blacklist.Blacklist;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

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
            NameTagEventHandler.refreshAllDisplayNames();
            return;
        }

        if (currentTime - blacklistShown > 1000L) return;

        Matcher matcher = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String name = matcher.group(1);
            String reason = matcher.group(2);

            BLACKLIST_MAP.put(name, reason.toLowerCase().contains("vogelfrei"));
            NameTagEventHandler.refreshAllDisplayNames();
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
            NameTagEventHandler.refreshAllDisplayNames();
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
            NameTagEventHandler.refreshAllDisplayNames();
        }
    }

    public static void refreshBlacklistReasons() {
        try {
            File unicacityAddonDir = new File(UnicacityAddon.MINECRAFT.mcDataDir.getAbsolutePath() + "/unicacityAddon/");
            if (unicacityAddonDir.exists() || unicacityAddonDir.mkdir()) {
                File blacklistData = new File(unicacityAddonDir.getAbsolutePath() + "/blacklistData.json");
                if (blacklistData.exists() || blacklistData.createNewFile()) {
                    Gson g = new Gson();
                    Path path = Paths.get(blacklistData.getAbsolutePath());
                    BufferedReader reader = Files.newBufferedReader(path);
                    BLACKLIST = g.fromJson(reader, Blacklist.class);
                } else AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                        .error().space()
                        .of("Datei 'blacklistData.json' wurde nicht gefunden!").color(ColorCode.GRAY).advance()
                        .createComponent());
            } else  AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                    .error().space()
                    .of("Ordner 'unicacityAddon' wurde nicht gefunden!").color(ColorCode.GRAY).advance()
                    .createComponent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}