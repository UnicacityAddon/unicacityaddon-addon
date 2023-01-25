package com.rettichlp.unicacityaddon.base.utils;

import com.google.common.collect.Maps;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.enums.location.ATM;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.enums.location.Job;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import net.minecraft.util.math.BlockPos;

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
            double distance = AbstractionLayer.getPlayer().getPosition().getDistance(atm.getX(), atm.getY(), atm.getZ());
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
            double distance = AbstractionLayer.getPlayer().getPosition().getDistance(job.getX(), job.getY(), job.getZ());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestJob = job;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestJob);
    }

    public static Map.Entry<Double, Bus> getNearestBus() {
        return getNearestBus(AbstractionLayer.getPlayer().getPosition());
    }

    public static Map.Entry<Double, Bus> getNearestBus(BlockPos blockPos) {
        Bus nearestBus = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Bus bus : Bus.values()) {
            double distance = blockPos.getDistance(bus.getX(), bus.getY(), bus.getZ());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestBus = bus;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestBus);
    }

    public static Map.Entry<Double, NaviPoint> getNearestNaviPoint(int x, int y, int z) {
        return getNearestNaviPoint(new BlockPos(x, y, z));
    }

    public static Map.Entry<Double, NaviPoint> getNearestNaviPoint(BlockPos blockPos) {
        NaviPoint nearestNaviPoint = null;
        double nearestDistance = Double.MAX_VALUE;

        for (NaviPoint naviPoint : Syncer.NAVIPOINTLIST) {
            double distance = blockPos.getDistance(naviPoint.getX(), naviPoint.getY(), naviPoint.getZ());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNaviPoint = naviPoint;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestNaviPoint);
    }

    public static void stopRoute() {
        routeMessageClearExecuteTime = System.currentTimeMillis();
        AbstractionLayer.getPlayer().sendChatMessage("/stoproute");
    }
}