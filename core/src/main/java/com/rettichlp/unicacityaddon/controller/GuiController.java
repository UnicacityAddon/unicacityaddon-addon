package com.rettichlp.unicacityaddon.controller;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class GuiController {

    public abstract int getSlotNumberByDisplayName(String displayName);

    @Nullable
    public abstract String getContainerLegacyName();

    public abstract int getContainerId();

    public abstract void inventoryClick(int slotNumber);

    public abstract void updateDrugInventoryMap(UnicacityAddon unicacityAddon);

    public abstract void setSelectedHotbarSlot(int slotNumber);

    public abstract void updateSetting(boolean expectedValue);
}
