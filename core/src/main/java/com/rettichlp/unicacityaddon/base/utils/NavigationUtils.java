package com.rettichlp.unicacityaddon.base.utils;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.enums.location.ATM;
import com.rettichlp.unicacityaddon.base.enums.location.Job;
import com.rettichlp.unicacityaddon.base.models.NaviPointEntry;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/utils/location/navigation/NavigationUtil.java">UCUtils by paulzhng</a>
 */
public class NavigationUtils {

    public static long routeMessageClearExecuteTime = -1;

    public static Map.Entry<Double, ATM> getNearestATM() {
        ATM nearestATM = null;
        double nearestDistance = Double.MAX_VALUE;

        for (ATM atm : ATM.values()) {
            double distance = AbstractionLayer.getPlayer().getPosition().distance(new FloatVector3(atm.getX(), atm.getY(), atm.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestATM = atm;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestATM);
    }

    public static Map.Entry<Double, Job> getNearestJob() {
        Job nearestJob = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Job job : Job.values()) {
            double distance = AbstractionLayer.getPlayer().getPosition().distance(new FloatVector3(job.getX(), job.getY(), job.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestJob = job;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestJob);
    }

    public static Map.Entry<Double, NaviPointEntry> getNearestNaviPoint(int x, int y, int z) {
        return getNearestNaviPoint(new FloatVector3(x, y, z));
    }

    public static Map.Entry<Double, NaviPointEntry> getNearestNaviPoint(UPlayer p) {
        return getNearestNaviPoint(p.getPosition());
    }

    public static Map.Entry<Double, NaviPointEntry> getNearestNaviPoint(FloatVector3 blockPos) {
        NaviPointEntry nearestNaviPoint = null;
        double nearestDistance = Double.MAX_VALUE;

        for (NaviPointEntry naviPointEntry : Syncer.NAVIPOINTLIST) {
            double distance = blockPos.distance(new FloatVector3(naviPointEntry.getX(), naviPointEntry.getY(), naviPointEntry.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNaviPoint = naviPointEntry;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestNaviPoint);
    }

    public static void stopRoute() {
        routeMessageClearExecuteTime = System.currentTimeMillis();
        AbstractionLayer.getPlayer().sendChatMessage("/stoproute");
    }
}