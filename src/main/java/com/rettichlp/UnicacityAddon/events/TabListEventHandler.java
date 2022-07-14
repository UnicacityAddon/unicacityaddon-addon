/*
package com.rettichlp.UnicacityAddon.events;

import com.google.common.collect.Ordering;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.reflection.ReflectionUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.gui.screen.overlay.PlayerTabListOverlayEvent;
import net.labymod.core_implementation.mc116.gui.ModPlayerTabOverlay;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

*/
/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/tree/master/src/main/java/de/fuzzlemann/ucutils/utils/tablist">UCUtils by paulzhng</a>
 *//*

@UCEventLabymod
public class TabListEventHandler implements Comparator<NetworkPlayerInfo> {

    private static final List<String> ORDERED_ENTRIES = Arrays.asList(
            "§1[UC]",
            "§1",
            "§9[UC]",
            "§9",
            "§4[UC]",
            "§4",
            "§6[UC]",
            "§6",
            "§8[§9UC§8]§c",
            "§8[§6R§8]",
            "[UC]"
    );

    @Override
    public int compare(NetworkPlayerInfo o1, NetworkPlayerInfo o2) {
        String stringOne = getTablistName(o1);
        String stringTwo = getTablistName(o2);

        String stringOneStartsWith = ORDERED_ENTRIES.stream().filter(stringOne::startsWith).findAny().orElse(null);
        String stringTwoStartsWith = ORDERED_ENTRIES.stream().filter(stringTwo::startsWith).findAny().orElse(null);

        if (stringOneStartsWith != null && stringTwoStartsWith != null) {
            int sgn = ORDERED_ENTRIES.indexOf(stringOneStartsWith) - ORDERED_ENTRIES.indexOf(stringTwoStartsWith);
            return sgn != 0 ? sgn : stringOne.compareTo(stringTwo);
        }

        if (stringOneStartsWith != null) return -1;
        if (stringTwoStartsWith != null) return 1;

        return stringOne.compareTo(stringTwo);
    }

    @Subscribe
    public void onPlayerTabListOverlay(PlayerTabListOverlayEvent e) {
        if (!ConfigElements.getEventTabList()) return;
        ReflectionUtils.setValue(ModPlayerTabOverlay.class, Ordering.class, Ordering.from(new TabListEventHandler()));
    }

    private String getFormattedDisplayName(ITextComponent displayName) {
        StringBuilder formattedDisplayName = new StringBuilder();

        List<ITextComponent> siblings = displayName.getSiblings();
        if (siblings.size() > 0) {
            siblings.forEach(sibling -> {
                Color color = sibling.getStyle().getColor();
                if (color == null) return;
                formattedDisplayName.append(ColorCode.valueOf(color.getName().toUpperCase()).getCode()).append(sibling.getString());
            });
        }

        if (formattedDisplayName.toString().isEmpty() || formattedDisplayName.toString().contains("§8[§6R§8]")) formattedDisplayName.append(displayName.getString());

        return formattedDisplayName.toString().replace("[R]", FormattingCode.RESET.getCode());
    }

    private String getTablistName(NetworkPlayerInfo networkPlayerInfo) {
        return getFormattedDisplayName(networkPlayerInfo.getDisplayName() != null ? networkPlayerInfo.getDisplayName() :
                ScorePlayerTeam.func_237500_a_(networkPlayerInfo.getPlayerTeam(), ITextComponent.getTextComponentOrEmpty(networkPlayerInfo.getGameProfile().getName())));
    }
}*/
