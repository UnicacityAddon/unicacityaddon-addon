package com.rettichlp.unicacityaddon.controller;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class TransportController {

    public abstract void processBusRouting(UnicacityAddon unicacityAddon);

    public abstract void carInteract();
}
