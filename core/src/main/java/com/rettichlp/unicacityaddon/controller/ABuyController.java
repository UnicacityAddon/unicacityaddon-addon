package com.rettichlp.unicacityaddon.controller;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class ABuyController {

    public abstract void startBuy(UnicacityAddon unicacityAddon, int slotIndex);
}
