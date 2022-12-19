package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.client.scoreboard.TabListUpdateEvent;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/tree/master/src/main/java/de/fuzzlemann/ucutils/utils/tablist">UCUtils by paulzhng</a>
 */
public class TabListEventHandler extends TabListUpdateEvent implements Comparator<NetworkPlayerInfo> {

    private static final List<String> ORDERED_ENTRIES = Arrays.asList("§1[UC]", "§1", "§9[UC]", "§9", "§4[UC]", "§4", "§6[UC]", "§6", "§8[§9UC§8]§c", "§8[§6R§8]", "[UC]");

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

        if (stringOneStartsWith != null)
            return -1;
        if (stringTwoStartsWith != null)
            return 1;

        return stringOne.compareTo(stringTwo);
    }

//    @Override
//    public void onUpdate(Type type, String s, String s1) {
//        if (!ConfigElements.getEventTabList())
//            return;
//        ReflectionUtils.setValue(ModPlayerTabOverlay.class, Ordering.class, Ordering.from(new TabListEventHandler()));
//    }

    public static String getFormattedDisplayName(Component component) {
        StringBuilder formattedDisplayName = new StringBuilder();

        List<Component> children = component.children();
        if (children.size() > 0) {
            children.forEach(child -> {
                TextColor color = child.color();
                if (color == null)
                    return;
                formattedDisplayName.append(ColorCode.getColorCodeByTextColor(color).getCode()).append(TextUtils.plain(child));
            });
        }

        if (formattedDisplayName.toString().isEmpty() || formattedDisplayName.toString().contains("§8[§6R§8]"))
            formattedDisplayName.append(TextUtils.plain(component));

        return formattedDisplayName.toString().replace("[R]", FormattingCode.RESET.getCode());
    }

    public static String getTablistName(NetworkPlayerInfo networkPlayerInfo) {
        return getFormattedDisplayName(networkPlayerInfo.displayName() != null
                ? networkPlayerInfo.displayName()
                : networkPlayerInfo.getTeam().formatDisplayName(Component.text(networkPlayerInfo.profile().getUsername())));
    }
}