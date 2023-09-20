package com.rettichlp.unicacityaddon.listener.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class HouseRenterListener {

    public static final Map<Integer, Map.Entry<Integer, Integer>> HOUSE_RENTER_MAP = new HashMap<>();
    private final Timer TIMER = new Timer();
    private int lastHouseNumber = 0;
    private int lastRenterAmount = 0;
    private int lastRenterOnlineAmount = 0;

    private final UnicacityAddon unicacityAddon;

    public HouseRenterListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();
        if (!msg.equals("/mieters"))
            return;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                AddonPlayer p = HouseRenterListener.this.unicacityAddon.player();
                p.sendEmptyMessage();

                HOUSE_RENTER_MAP.put(lastHouseNumber, Maps.immutableEntry(lastRenterAmount, lastRenterOnlineAmount));
                lastHouseNumber = lastRenterAmount = lastRenterOnlineAmount = 0;
                HOUSE_RENTER_MAP.forEach((houseNumber, mapEntry) -> {
                    if (houseNumber > 0)
                        p.sendMessage(Message.getBuilder().space().space()
                                .of("➡").color(ColorCode.GRAY).advance().space()
                                .of("Haus").color(ColorCode.GOLD).advance().space()
                                .of(String.valueOf(houseNumber)).color(ColorCode.YELLOW).advance()
                                .of(":").color(ColorCode.GOLD).advance().space()
                                .of(String.valueOf(mapEntry.getValue())).color(ColorCode.GREEN).advance()
                                .of("/").color(ColorCode.GRAY).advance()
                                .of(String.valueOf(mapEntry.getKey())).color(ColorCode.YELLOW).advance()
                                .createComponent());
                });
            }
        }, 500);
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher houseRenterMatcher = PatternHandler.HOUSE_RENTER_HEADER_PATTERN.matcher(msg);
        if (houseRenterMatcher.find()) {
            HOUSE_RENTER_MAP.put(lastHouseNumber, Maps.immutableEntry(lastRenterAmount, lastRenterOnlineAmount));
            lastHouseNumber = Integer.parseInt(houseRenterMatcher.group(1));
            lastRenterAmount = 0;
            lastRenterOnlineAmount = 0;
        }

        Matcher renterMatcher = PatternHandler.HOUSE_RENTER_VALUE_PATTERN.matcher(msg);
        if (renterMatcher.find()) {
            lastRenterAmount++;
            boolean isOnline = renterMatcher.group(2).equals("Online");
            String offlineForXDays = "";

            if (isOnline) {
                lastRenterOnlineAmount++;
            } else {
                Date date = null;

                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    date = simpleDateFormat.parse(renterMatcher.group(3) + "/" + renterMatcher.group(4) + "/" + renterMatcher.group(5));
                } catch (ParseException ex) {
                    this.unicacityAddon.logger().error(ex.getMessage());
                }

                if (date == null)
                    return;

                long diffInMillies = Math.abs(new Date().getTime() - date.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                offlineForXDays = diff + (diff == 1 ? " Tag" : " Tage");
            }

            e.setMessage(Message.getBuilder().space().space()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(renterMatcher.group(1)).color(ColorCode.GOLD).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(renterMatcher.group(2)).color(isOnline ? ColorCode.GREEN : ColorCode.RED).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().space()
                    .of(offlineForXDays.isEmpty() ? "" : "➡ ").color(ColorCode.GRAY).advance()
                    .of(offlineForXDays).color(ColorCode.RED).advance().space()
                    .of("[✕]").color(ColorCode.DARK_RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Mietvertrag kündigen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/unrent " + lastHouseNumber + " " + renterMatcher.group(1))
                            .advance()
                    .createComponent()
            );
        }
    }
}