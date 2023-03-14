package com.rettichlp.unicacityaddon.v1_12_2;

import com.google.common.collect.Ordering;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.controller.TabListController;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.models.Implements;
import net.minecraft.client.gui.GuiPlayerTabOverlay;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author RettichLP
 */
@Singleton
@Implements(TabListController.class)
public class VersionedTabListController extends TabListController implements Comparator<NetworkPlayerInfo> {

    private static final List<String> ORDERED_ENTRIES = Arrays.asList("§1[UC]", "§1", "§9[UC]", "§9", "§4[UC]", "§4", "§6[UC]", "§6", "§8[§9UC§8]§c", "§8[§eB§8]", "§8[§6R§8]", "[UC]");

    @Inject
    public VersionedTabListController() {
    }

    @Override
    public void orderTabList() {
        try {
            Field field = GuiPlayerTabOverlay.class.getDeclaredField("ENTRY_ORDERING");
            field.setAccessible(true);
            field.set(null, Ordering.from(new VersionedTabListController()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // ReflectionUtils.setValue(GuiPlayerTabOverlay.class, Ordering.class, Ordering.from(new VersionedTabListController()));
    }

    @Override
    public String tabListName(NetworkPlayerInfo networkPlayerInfo) {
        Component displayNameComponent = networkPlayerInfo.displayName() != null
                ? networkPlayerInfo.displayName()
                : networkPlayerInfo.getTeam().formatDisplayName(Component.text(networkPlayerInfo.profile().getUsername()));

        StringBuilder tabListName = new StringBuilder();

        List<Component> children = displayNameComponent.children();
        if (children.size() > 0) {
            children.forEach(child -> {
                TextColor color = child.color();
                if (color == null)
                    return;
                tabListName.append(ColorCode.getColorCodeByTextColor(color).getCode()).append(TextUtils.plain(child));
            });
        }

        if (tabListName.toString().isEmpty() || tabListName.toString().contains("§8[§eB§8]"))
            tabListName.append(TextUtils.plain(displayNameComponent));

        if (tabListName.toString().isEmpty() || tabListName.toString().contains("§8[§6R§8]"))
            tabListName.append(TextUtils.plain(displayNameComponent));

        return tabListName.toString()
                .replace("[B]", FormattingCode.RESET.getCode())
                .replace("[R]", FormattingCode.RESET.getCode());
    }

    @Override
    public int compare(NetworkPlayerInfo o1, NetworkPlayerInfo o2) {
        String stringOne = tabListName(o1);
        String stringTwo = tabListName(o2);

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
}
