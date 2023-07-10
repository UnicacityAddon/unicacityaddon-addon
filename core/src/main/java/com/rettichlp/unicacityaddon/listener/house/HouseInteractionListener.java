package com.rettichlp.unicacityaddon.listener.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class HouseInteractionListener {

    /**
     * Progress array holds data for akku and heal progresses:
     * <ol>
     *     <li>akku progress</li>
     *     <li>heal progress</li>
     * </ol>
     */
    public static final int[] progress = {-1, -1};

    private final UnicacityAddon unicacityAddon;

    public HouseInteractionListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher houseAkkuMatcher = PatternHandler.HOUSE_AKKU_PATTERN.matcher(msg);
        if (houseAkkuMatcher.find()) {
            progress[0] = 0;
            setMessage(progress[0]);
        }

        Matcher houseHealMatcher = PatternHandler.HOUSE_HEAL_PATTERN.matcher(msg);
        if (houseHealMatcher.find()) {
            progress[1] = 0;
            setMessage(progress[1]);
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_3)) {
            increaseProgress(1);
        }

        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_5)) {
            increaseProgress(0);
        }
    }

    public void increaseProgress(int progressIndex) {
        switch (progress[progressIndex]) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                progress[progressIndex]++;
                setMessage(progress[progressIndex]);
            }
            case 10 -> progress[progressIndex] = -1;
        }
    }

    private void setMessage(int progress) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder
                    .append(i < progress ? ColorCode.GREEN.getCode() : ColorCode.GRAY.getCode())
                    .append("█");
        }
        Laby.labyAPI().minecraft().chatExecutor().displayActionBar(stringBuilder.toString());
    }
}