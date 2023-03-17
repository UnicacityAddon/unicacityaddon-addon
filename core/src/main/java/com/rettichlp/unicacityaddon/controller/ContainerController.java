package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class ContainerController {

    public abstract String getContainerName();

    public abstract List<ItemStack> getInventorySlots();

    public abstract boolean isHopperScreen();

    public abstract boolean isChestScreen();
}
