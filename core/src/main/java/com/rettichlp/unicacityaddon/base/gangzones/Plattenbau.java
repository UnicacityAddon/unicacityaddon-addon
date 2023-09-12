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
public class Plattenbau extends AbstractAttackableGangzone {

    public Plattenbau(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(-525, 69, 415), new FloatVector3(-525, 69, 447)),
                Pair.of(new FloatVector3(-525, 69, 447), new FloatVector3(-510, 69, 447)),
                Pair.of(new FloatVector3(-510, 69, 447), new FloatVector3(-510, 69, 439)),
                Pair.of(new FloatVector3(-510, 69, 439), new FloatVector3(-486, 69, 439)),
                Pair.of(new FloatVector3(-486, 69, 439), new FloatVector3(-486, 69, 429)),
                Pair.of(new FloatVector3(-486, 69, 429), new FloatVector3(-411, 69, 429)),
                Pair.of(new FloatVector3(-411, 69, 429), new FloatVector3(-411, 69, 392)),
                Pair.of(new FloatVector3(-411, 69, 392), new FloatVector3(-418, 69, 392)),
                Pair.of(new FloatVector3(-418, 69, 392), new FloatVector3(-418, 69, 378)),
                Pair.of(new FloatVector3(-418, 69, 378), new FloatVector3(-480, 69, 378)),
                Pair.of(new FloatVector3(-480, 69, 378), new FloatVector3(-480, 69, 413)),
                Pair.of(new FloatVector3(-480, 69, 413), new FloatVector3(-486, 69, 413)),
                Pair.of(new FloatVector3(-486, 69, 413), new FloatVector3(-486, 69, 406)),
                Pair.of(new FloatVector3(-486, 69, 406), new FloatVector3(-496, 69, 406)),
                Pair.of(new FloatVector3(-496, 69, 406), new FloatVector3(-496, 69, 415)),
                Pair.of(new FloatVector3(-496, 69, 415), new FloatVector3(-525, 69, 415))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return List.of(
                Pair.of(new FloatVector3(-575, 69, 365), new FloatVector3(-575, 69, 497)),
                Pair.of(new FloatVector3(-575, 69, 497), new FloatVector3(-460, 69, 497)),
                Pair.of(new FloatVector3(-460, 69, 497), new FloatVector3(-460, 69, 489)),
                Pair.of(new FloatVector3(-460, 69, 489), new FloatVector3(-436, 69, 489)),
                Pair.of(new FloatVector3(-436, 69, 489), new FloatVector3(-436, 69, 479)),
                Pair.of(new FloatVector3(-436, 69, 479), new FloatVector3(-361, 69, 479)),
                Pair.of(new FloatVector3(-361, 69, 479), new FloatVector3(-361, 69, 342)),
                Pair.of(new FloatVector3(-361, 69, 342), new FloatVector3(-368, 69, 342)),
                Pair.of(new FloatVector3(-368, 69, 342), new FloatVector3(-368, 69, 328)),
                Pair.of(new FloatVector3(-368, 69, 328), new FloatVector3(-530, 69, 328)),
                Pair.of(new FloatVector3(-530, 69, 328), new FloatVector3(-530, 69, 356)),
                Pair.of(new FloatVector3(-530, 69, 356), new FloatVector3(-546, 69, 356)),
                Pair.of(new FloatVector3(-546, 69, 356), new FloatVector3(-546, 69, 365)),
                Pair.of(new FloatVector3(-546, 69, 365), new FloatVector3(-575, 69, 365))
        );
    }
}
