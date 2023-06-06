package com.rettichlp.unicacityaddon.base.models.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class NaviPoint {

    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String article;

    public String getTabName() {
        return name.replace(" ", "-");
    }

    public String getArticleFourthCase() {
        return article.replace("der", "den");
    }

    public FloatVector3 getLocation() {
        return new FloatVector3(x, y, z);
    }

    public static NaviPoint getNaviPointByTabName(String tabName, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getNaviPointList().stream()
                .filter(naviPointEntry -> naviPointEntry.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }
}