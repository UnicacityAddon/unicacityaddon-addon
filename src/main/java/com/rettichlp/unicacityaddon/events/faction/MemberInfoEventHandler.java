package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class MemberInfoEventHandler {

    private static final Timer TIMER = new Timer();
    private static final Pattern FACTION_MEMBERS_PATTERN = Pattern.compile("^ {2}=== Fraktionsmitglieder \\[(.+)] ===$");
    private static final Map<Boolean, Integer> MEMBER_MAP = new HashMap<>();
    private static long memberlistShown;
    public static CompletableFuture<Map<Boolean, Integer>> future;

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        String message = e.getMessage().getUnformattedText();
        long currentTime = System.currentTimeMillis();

        Matcher factionMembersMatcher = FACTION_MEMBERS_PATTERN.matcher(message);
        if (factionMembersMatcher.find()) {
            memberlistShown = currentTime;
            MEMBER_MAP.put(false, 0);
            MEMBER_MAP.put(true, 0);

            if (future != null) {
                TIMER.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        future.complete(MEMBER_MAP);
                    }
                }, 300L);

                e.setCanceled(true);
            }

            String factionString = factionMembersMatcher.group(1);
            Faction faction = Faction.getFactionByDisplayName(factionString);

            e.setMessage(Message.getBuilder().space().space()
                    .of("===").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Fraktionsmitglieder").color(ColorCode.DARK_AQUA).advance().space()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of(factionString).color(ColorCode.DARK_AQUA).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(String.valueOf(Syncer.PLAYERFACTIONMAP.values().stream().filter(f -> f.equals(faction)).count())).color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().space()
                    .of("===").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
        }

        if (currentTime - memberlistShown > 200L || !message.startsWith(" » "))
            return;

        boolean inactive = !message.endsWith("AFK") && !message.endsWith("Nicht im Dienst");
        MEMBER_MAP.merge(inactive, 1, Integer::sum);

        if (future == null) {
            String name = message.substring(3).split(" ")[0];
            Integer rank = Syncer.PLAYERRANKMAP.get(name.replace("[UC]", ""));

            String formattedMessage = ColorCode.GRAY.getCode() + message
                    .replace(" » ", "")
                    .replace("|", ColorCode.DARK_GRAY.getCode() + "|")
                    .replace("AFK", ColorCode.RED.getCode() + "AFK")
                    .replace("Nicht im Dienst", ColorCode.RED.getCode() + "Nicht im Dienst")
                    .replace("Im Dienst", ColorCode.GREEN.getCode() + "Im Dienst");

            e.setMessage(Message.getBuilder().space()
                    .of("» Rang:").color(ColorCode.GRAY).advance().space()
                    .of(rank != null ? String.valueOf(rank) : "X").color(ColorCode.AQUA).advance().space()
                    .of("|").color(ColorCode.DARK_GRAY).advance().space()
                    .add(formattedMessage)
                    .createComponent());
        } else {
            e.setCanceled(true);
        }
    }
}