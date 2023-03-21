package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class WorldInteractionController {

    public abstract FloatVector3 getClickedBlockLocation();

    public abstract boolean isHouseNumberSign(FloatVector3 pos);

    public abstract boolean isBanner(FloatVector3 pos);
}
