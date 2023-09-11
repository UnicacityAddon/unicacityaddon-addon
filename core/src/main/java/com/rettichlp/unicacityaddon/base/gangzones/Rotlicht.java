package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

@UCGangzone
public class Rotlicht extends AbstractAttackableGangzone {

    public Rotlicht(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(691, 69, -29), new FloatVector3(691, 69, 60)),
                Pair.of(new FloatVector3(691, 69, 60), new FloatVector3(763, 69, 60)),
                Pair.of(new FloatVector3(763, 69, 60), new FloatVector3(763, 69, 58)),
                Pair.of(new FloatVector3(763, 69, 58), new FloatVector3(764, 69, 58)),
                Pair.of(new FloatVector3(764, 69, 58), new FloatVector3(764, 69, 56)),
                Pair.of(new FloatVector3(764, 69, 56), new FloatVector3(765, 69, 56)),
                Pair.of(new FloatVector3(765, 69, 56), new FloatVector3(765, 69, 55)),
                Pair.of(new FloatVector3(765, 69, 55), new FloatVector3(767, 69, 55)),
                Pair.of(new FloatVector3(767, 69, 55), new FloatVector3(767, 69, 54)),
                Pair.of(new FloatVector3(767, 69, 54), new FloatVector3(807, 69, 54)),
                Pair.of(new FloatVector3(807, 69, 54), new FloatVector3(807, 69, -29)),
                Pair.of(new FloatVector3(807, 69, -29), new FloatVector3(691, 69, -29))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return List.of(
                Pair.of(new FloatVector3(641, 69, -79), new FloatVector3(641, 69, 110)),
                Pair.of(new FloatVector3(641, 69, 110), new FloatVector3(813, 69, 110)),
                Pair.of(new FloatVector3(813, 69, 110), new FloatVector3(813, 69, 108)),
                Pair.of(new FloatVector3(813, 69, 108), new FloatVector3(814, 69, 108)),
                Pair.of(new FloatVector3(814, 69, 108), new FloatVector3(814, 69, 106)),
                Pair.of(new FloatVector3(814, 69, 106), new FloatVector3(815, 69, 106)),
                Pair.of(new FloatVector3(815, 69, 106), new FloatVector3(815, 69, 105)),
                Pair.of(new FloatVector3(815, 69, 105), new FloatVector3(817, 69, 105)),
                Pair.of(new FloatVector3(817, 69, 105), new FloatVector3(817, 69, 104)),
                Pair.of(new FloatVector3(817, 69, 104), new FloatVector3(857, 69, 104)),
                Pair.of(new FloatVector3(857, 69, 104), new FloatVector3(857, 69, -79)),
                Pair.of(new FloatVector3(857, 69, -79), new FloatVector3(641, 69, -79))
        );
    }
}
