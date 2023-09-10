package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collections;
import java.util.List;

@UCGangzone
public class Yachthafen extends AbstractAttackableGangzone {

    public Yachthafen(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(236, 69, -551), new FloatVector3(236, 69, -495)),
                Pair.of(new FloatVector3(236, 69, -495), new FloatVector3(292, 69, -495)),
                Pair.of(new FloatVector3(292, 69, -495), new FloatVector3(292, 69, -551)),
                Pair.of(new FloatVector3(292, 69, -551), new FloatVector3(236, 69, -551))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return Collections.emptyList();
    }
}
