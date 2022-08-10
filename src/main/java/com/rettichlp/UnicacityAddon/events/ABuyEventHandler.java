package com.rettichlp.UnicacityAddon.events;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/ABuyCommand.java">UCUtils by paulzhng</a>
 */
public class ABuyEventHandler {

    public static int lastHoveredSlotIndex;

    @SubscribeEvent
    public void onKeyboardClickEvent(GuiScreenEvent.KeyboardInputEvent.Post e) {
        if (!(e.getGui() instanceof GuiContainer)) return;
        GuiContainer inv = (GuiContainer) e.getGui();

        Slot slot = inv.getSlotUnderMouse();
        if (slot == null) return;

        ItemStack is = slot.inventory.getStackInSlot(slot.getSlotIndex());
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null) return;

        NBTTagCompound display = nbt.getCompoundTag("display");
        String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
        if (!lore.startsWith("§c") || !lore.endsWith("$")) return;

        lastHoveredSlotIndex = slot.getSlotIndex();
    }
}