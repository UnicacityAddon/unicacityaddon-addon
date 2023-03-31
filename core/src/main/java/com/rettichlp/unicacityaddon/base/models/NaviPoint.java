package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 */
public class NaviPoint {

    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String article;

    public NaviPoint(String name, int x, int y, int z, String article) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getArticle() {
        return article;
    }

    public String getTabName() {
        return name.replace(" ", "-");
    }

    public String getArticleFourthCase() {
        return article.replace("der", "den");
    }

    public FloatVector3 getBlockPos() {
        return new FloatVector3(x, y, z);
    }

    public static NaviPoint getNaviPointByTabName(String tabName, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getNaviPointList().stream()
                .filter(naviPointEntry -> naviPointEntry.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }
}