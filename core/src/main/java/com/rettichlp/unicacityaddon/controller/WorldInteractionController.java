package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class WorldInteractionController {

    public abstract FloatVector3 getClickedBlockLocation();

    public abstract boolean isHouseNumberSign(FloatVector3 location);

    public abstract boolean isBanner(FloatVector3 location);

    public abstract boolean isPlant(FloatVector3 location);

    public abstract Collection<FloatVector3> getFireBlocksInBox(FloatVector3 one, FloatVector3 two);
}
