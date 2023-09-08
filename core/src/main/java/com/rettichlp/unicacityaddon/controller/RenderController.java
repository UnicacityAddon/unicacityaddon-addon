package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class RenderController {

    public abstract void drawFacade(FloatVector3 first, FloatVector3 second, Color c);
}
