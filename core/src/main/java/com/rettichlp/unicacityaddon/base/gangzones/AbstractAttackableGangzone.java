package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public abstract class AbstractAttackableGangzone extends AbstractGangzone {

    private final UnicacityAddon unicacityAddon;

    public AbstractAttackableGangzone(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
        this.unicacityAddon = unicacityAddon;
    }

    public abstract List<Pair<FloatVector3, FloatVector3>> gangwarFacades();

    public void renderGangwarArea() {
        Color color = ColorCode.GRAY.getLabymodColor();
        this.gangzoneFacades().forEach(posPair -> this.unicacityAddon.renderController().drawFacade(posPair.getFirst(), posPair.getSecond(), color));
    }
}
