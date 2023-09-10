package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class AttackableGangzoneFarm extends AbstractAttackableGangzone {

    public AttackableGangzoneFarm(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return null;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(455, 69, 483), new FloatVector3(455, 69, 562)),
                Pair.of(new FloatVector3(455, 69, 562), new FloatVector3(522, 69, 562)),
                Pair.of(new FloatVector3(522, 69, 562), new FloatVector3(522, 69, 568)),
                Pair.of(new FloatVector3(522, 69, 568), new FloatVector3(550, 69, 568)),
                Pair.of(new FloatVector3(550, 69, 568), new FloatVector3(550, 69, 562)),
                Pair.of(new FloatVector3(550, 69, 562), new FloatVector3(551, 69, 562)),
                Pair.of(new FloatVector3(551, 69, 562), new FloatVector3(551, 69, 551)),
                Pair.of(new FloatVector3(551, 69, 551), new FloatVector3(550, 69, 551)),
                Pair.of(new FloatVector3(550, 69, 551), new FloatVector3(550, 69, 544)),
                Pair.of(new FloatVector3(550, 69, 544), new FloatVector3(525, 69, 544)),
                Pair.of(new FloatVector3(525, 69, 544), new FloatVector3(525, 69, 483)),
                Pair.of(new FloatVector3(525, 69, 483), new FloatVector3(455, 69, 483))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return null;
    }
}
