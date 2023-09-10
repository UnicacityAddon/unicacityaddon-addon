package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class AttackableGangzoneRotlicht extends AbstractAttackableGangzone {

    public AttackableGangzoneRotlicht(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return null;
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
        return null;
    }
}
