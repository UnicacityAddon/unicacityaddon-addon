package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class AttackableGangzoneYachthafen extends AbstractAttackableGangzone {

    public AttackableGangzoneYachthafen(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return null;
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
        return null;
    }
}
