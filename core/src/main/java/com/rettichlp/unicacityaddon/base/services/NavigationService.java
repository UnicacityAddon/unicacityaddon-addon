package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.enums.location.ATM;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.enums.location.Job;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        return getNearest(this.unicacityAddon.player().getLocation(), ATM.values(), ATM::getLocation);
    }

    public Map.Entry<Double, Job> getNearestJob() {
        return getNearest(this.unicacityAddon.player().getLocation(), Job.values(), Job::getLocation);
    }

    public Map.Entry<Double, Bus> getNearestBus(FloatVector3 location) {
        return getNearest(location, Bus.values(), Bus::getLocation);
    }

    public Map.Entry<Double, NaviPoint> getNearestNaviPoint(int x, int y, int z) {
        return getNearestNaviPoint(new FloatVector3(x, y, z));
    }

    public Map.Entry<Double, NaviPoint> getNearestNaviPoint(FloatVector3 location) {
        return getNearest(location, this.unicacityAddon.api().getNaviPointList(), NaviPoint::getLocation);
    }

    public <T> Map.Entry<Double, T> getNearest(FloatVector3 location, T[] elements, Function<T, FloatVector3> locationExtractor) {
        return getNearest(location, List.of(elements), locationExtractor);
    }

    public <T> Map.Entry<Double, T> getNearest(FloatVector3 location, Collection<T> elements, Function<T, FloatVector3> locationExtractor) {
        T nearestElement = null;
        double nearestDistance = Double.MAX_VALUE;

        for (T element : elements) {
            double distance = location.distance(locationExtractor.apply(element));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestElement = element;
            }
        }

        return Maps.immutableEntry(nearestDistance, nearestElement);
    }
}