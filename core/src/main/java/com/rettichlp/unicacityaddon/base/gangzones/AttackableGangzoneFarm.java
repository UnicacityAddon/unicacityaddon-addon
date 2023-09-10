package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collections;
import java.util.List;

@UCGangzone
public class AttackableGangzoneFarm extends AbstractAttackableGangzone {

    public AttackableGangzoneFarm(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
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
        return Collections.emptyList();
    }
}
