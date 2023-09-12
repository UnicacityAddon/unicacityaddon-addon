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
public class Altstadt extends AbstractAttackableGangzone {

    public Altstadt(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(195, 69, 589), new FloatVector3(195, 69, 637)),
                Pair.of(new FloatVector3(195, 69, 637), new FloatVector3(182, 69, 637)),
                Pair.of(new FloatVector3(182, 69, 637), new FloatVector3(182, 69, 647)),
                Pair.of(new FloatVector3(182, 69, 647), new FloatVector3(174, 69, 647)),
                Pair.of(new FloatVector3(174, 69, 647), new FloatVector3(174, 69, 677)),
                Pair.of(new FloatVector3(174, 69, 677), new FloatVector3(178, 69, 677)),
                Pair.of(new FloatVector3(178, 69, 677), new FloatVector3(178, 69, 687)),
                Pair.of(new FloatVector3(178, 69, 687), new FloatVector3(182, 69, 687)),
                Pair.of(new FloatVector3(182, 69, 687), new FloatVector3(182, 69, 745)),
                Pair.of(new FloatVector3(182, 69, 745), new FloatVector3(176, 69, 745)),
                Pair.of(new FloatVector3(176, 69, 745), new FloatVector3(176, 69, 768)),
                Pair.of(new FloatVector3(176, 69, 768), new FloatVector3(182, 69, 768)),
                Pair.of(new FloatVector3(182, 69, 768), new FloatVector3(182, 69, 794)),
                Pair.of(new FloatVector3(182, 69, 794), new FloatVector3(192, 69, 794)),
                Pair.of(new FloatVector3(192, 69, 794), new FloatVector3(192, 69, 848)),
                Pair.of(new FloatVector3(192, 69, 848), new FloatVector3(304, 69, 848)),
                Pair.of(new FloatVector3(304, 69, 848), new FloatVector3(304, 69, 824)),
                Pair.of(new FloatVector3(304, 69, 824), new FloatVector3(316, 69, 824)),
                Pair.of(new FloatVector3(316, 69, 824), new FloatVector3(316, 69, 803)),
                Pair.of(new FloatVector3(316, 69, 803), new FloatVector3(314, 69, 803)),
                Pair.of(new FloatVector3(314, 69, 803), new FloatVector3(314, 69, 786)),
                Pair.of(new FloatVector3(314, 69, 786), new FloatVector3(313, 69, 786)),
                Pair.of(new FloatVector3(313, 69, 786), new FloatVector3(313, 69, 589)),
                Pair.of(new FloatVector3(313, 69, 589), new FloatVector3(195, 69, 589))
        );
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangwarFacades() {
        return List.of(
                Pair.of(new FloatVector3(145, 69, 539), new FloatVector3(145, 69, 587)),
                Pair.of(new FloatVector3(145, 69, 587), new FloatVector3(132, 69, 587)),
                Pair.of(new FloatVector3(132, 69, 587), new FloatVector3(132, 69, 597)),
                Pair.of(new FloatVector3(132, 69, 597), new FloatVector3(124, 69, 597)),
                Pair.of(new FloatVector3(124, 69, 597), new FloatVector3(124, 69, 727)),
                Pair.of(new FloatVector3(124, 69, 727), new FloatVector3(126, 69, 727)),
                Pair.of(new FloatVector3(126, 69, 727), new FloatVector3(126, 69, 818)),
                Pair.of(new FloatVector3(126, 69, 818), new FloatVector3(132, 69, 818)),
                Pair.of(new FloatVector3(132, 69, 818), new FloatVector3(132, 69, 844)),
                Pair.of(new FloatVector3(132, 69, 844), new FloatVector3(142, 69, 844)),
                Pair.of(new FloatVector3(142, 69, 844), new FloatVector3(142, 69, 898)),
                Pair.of(new FloatVector3(142, 69, 898), new FloatVector3(354, 69, 898)),
                Pair.of(new FloatVector3(354, 69, 898), new FloatVector3(354, 69, 874)),
                Pair.of(new FloatVector3(354, 69, 874), new FloatVector3(366, 69, 874)),
                Pair.of(new FloatVector3(366, 69, 874), new FloatVector3(366, 69, 753)),
                Pair.of(new FloatVector3(366, 69, 753), new FloatVector3(364, 69, 753)),
                Pair.of(new FloatVector3(364, 69, 753), new FloatVector3(364, 69, 736)),
                Pair.of(new FloatVector3(364, 69, 736), new FloatVector3(363, 69, 736)),
                Pair.of(new FloatVector3(363, 69, 736), new FloatVector3(363, 69, 539)),
                Pair.of(new FloatVector3(363, 69, 539), new FloatVector3(145, 69, 539))
        );
    }
}
