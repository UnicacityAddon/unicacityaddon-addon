package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.ATM;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.enums.location.Job;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/utils/location/navigation/NavigationUtil.java">UCUtils by paulzhng</a>
 */
public class NavigationService {

    private final UnicacityAddon unicacityAddon;

    public NavigationService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public Map.Entry<Double, ATM> getNearestATM() {
        ATM nearestATM = null;
        double nearestDistance = Double.MAX_VALUE;

        for (ATM atm : ATM.values()) {
            double distance = this.unicacityAddon.player().getPosition().distance(new FloatVector3(atm.getX(), atm.getY(), atm.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestATM = atm;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestATM);
    }

    public Map.Entry<Double, Job> getNearestJob() {
        Job nearestJob = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Job job : Job.values()) {
            double distance = this.unicacityAddon.player().getPosition().distance(new FloatVector3(job.getX(), job.getY(), job.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestJob = job;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestJob);
    }

    public Map.Entry<Double, Bus> getNearestBus() {
        return getNearestBus(this.unicacityAddon.player().getPosition());
    }

    public Map.Entry<Double, Bus> getNearestBus(FloatVector3 blockPos) {
        Bus nearestBus = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Bus bus : Bus.values()) {
            double distance = blockPos.distance(new FloatVector3(bus.getX(), bus.getY(), bus.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestBus = bus;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestBus);
    }

    public Map.Entry<Double, NaviPoint> getNearestNaviPoint(int x, int y, int z) {
        return getNearestNaviPoint(new FloatVector3(x, y, z));
    }

    public Map.Entry<Double, NaviPoint> getNearestNaviPoint(AddonPlayer p) {
        return getNearestNaviPoint(p.getPosition());
    }

    public Map.Entry<Double, NaviPoint> getNearestNaviPoint(FloatVector3 blockPos) {
        NaviPoint nearestNaviPoint = null;
        double nearestDistance = Double.MAX_VALUE;

        for (NaviPoint naviPointEntry : this.unicacityAddon.api().getNaviPointList()) {
            double distance = blockPos.distance(new FloatVector3(naviPointEntry.getX(), naviPointEntry.getY(), naviPointEntry.getZ()));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestNaviPoint = naviPointEntry;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestNaviPoint);
    }
}