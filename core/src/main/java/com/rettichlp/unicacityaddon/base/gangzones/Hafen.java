package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

/**
 * @author RettichLP
 */
@UCGangzone
public class Hafen extends AbstractAttackableGangzone {

    public Hafen(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(-423, 69, 2), new FloatVector3(-423, 69, 177)),
                Pair.of(new FloatVector3(-423, 69, 177), new FloatVector3(-322, 69, 177)),
                Pair.of(new FloatVector3(-322, 69, 177), new FloatVector3(-322, 69, 2)),
                Pair.of(new FloatVector3(-322, 69, 2), new FloatVector3(-423, 69, 2))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return List.of(
                Pair.of(new FloatVector3(-473, 69, -48), new FloatVector3(-473, 69, 227)),
                Pair.of(new FloatVector3(-473, 69, 227), new FloatVector3(-272, 69, 227)),
                Pair.of(new FloatVector3(-272, 69, 227), new FloatVector3(-272, 69, -48)),
                Pair.of(new FloatVector3(-272, 69, -48), new FloatVector3(-473, 69, -48))
        );
    }
}
