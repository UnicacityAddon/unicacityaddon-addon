package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.controller.GuiController;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTTagType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@Singleton
@Implements(GuiController.class)
public class VersionedGuiController extends GuiController {

    @Override
    public int getSlotNumberByDisplayName(String displayName) {
        int slotNumber = -1;

        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) ((GuiContainer) guiScreen).inventorySlots;

            NonNullList<ItemStack> itemStacks = containerChest.getInventory();
            Optional<ItemStack> hoveredItemStackOptional = itemStacks.stream()
                    .filter(ItemStack::hasDisplayName)
                    .filter(itemStack -> itemStack.getDisplayName().contains(displayName))
                    .findFirst();

            if (hoveredItemStackOptional.isPresent()) {
                slotNumber = itemStacks.indexOf(hoveredItemStackOptional.get());
            }
        }

        return slotNumber;
    }

    @Override
    public void inventoryClick(UnicacityAddon unicacityAddon, int slotNumber) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;

        int windowId = 0;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            windowId = ((GuiContainer) guiScreen).inventorySlots.windowId;
        } else if (guiScreen instanceof GuiHopper && ((GuiHopper) guiScreen).inventorySlots instanceof ContainerHopper) {
            windowId = ((GuiHopper) guiScreen).inventorySlots.windowId;
        }

        Minecraft.getMinecraft().playerController.windowClick(windowId, slotNumber, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
    }

    @Override
    public void updateDrugInventoryMap(UnicacityAddon unicacityAddon) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest) guiScreen;

            int windowId = guiChest.inventorySlots.windowId;
            if (DropDrugAllCommand.lastWindowId == windowId)
                return;

            DropDrugAllCommand.lastWindowId = windowId;

            if (DropDrugAllCommand.cocaineCheck) {
                DropDrugAllCommand.cocaineCheck = false;
                // select cocaine to check drug purity
                this.inventoryClick(unicacityAddon, 0);
            } else if (DropDrugAllCommand.marihuanaCheck) {
                DropDrugAllCommand.marihuanaCheck = false;
                // select marihuana to check drug purity
                this.inventoryClick(unicacityAddon, 1);
            } else if (DropDrugAllCommand.methCheck) {
                DropDrugAllCommand.methCheck = false;
                // select meth to check drug purity
                this.inventoryClick(unicacityAddon, 2);
            } else {
                guiChest.inventorySlots.getInventory().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && !itemStack.getDisplayName().contains("Kokain") && !itemStack.getDisplayName().contains("Marihuana") && !itemStack.getDisplayName().contains("Methamphetamin"))
                        .forEach(itemStack -> {
                            NBTTagCompound nbtTagCompound = itemStack.getSubCompound("display");
                            if (nbtTagCompound != null) {
                                String lore = nbtTagCompound.getTagList("Lore", NBTTagType.STRING.getId()).getStringTagAt(0);

                                Matcher loreMatcher = Pattern.compile("» (?<amount>\\d+)(g| Pillen| Flaschen| Päckchen| Stück| Kisten)").matcher(lore);
                                if (loreMatcher.find()) {
                                    int amount = Integer.parseInt(loreMatcher.group("amount"));
                                    DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName());

                                    if (drugType != null) {
                                        Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap = unicacityAddon.fileService().data().getDrugInventoryMap();
                                        Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
                                        drugPurityMap.put(DrugPurity.BEST, amount);
                                        drugInventoryMap.put(drugType, drugPurityMap);
                                        unicacityAddon.fileService().data().setDrugInventoryMap(drugInventoryMap);
                                    }
                                }
                            }
                        });

                DropDrugAllCommand.active = false;
                DropDrugAllCommand.dropCommandExecution(unicacityAddon);
            }
        } else if (guiScreen instanceof GuiHopper && DropDrugAllCommand.active) {
            GuiHopper guiHopper = (GuiHopper) guiScreen;

            int windowId = guiHopper.inventorySlots.windowId;
            if (windowId == DropDrugAllCommand.lastWindowId)
                return;

            DropDrugAllCommand.lastWindowId = windowId;

            guiHopper.inventorySlots.getInventory().stream()
                    .filter(itemStack -> !itemStack.isEmpty())
                    .forEach(itemStack -> {
                        NBTTagCompound nbtTagCompound = itemStack.getSubCompound("display");
                        if (nbtTagCompound != null) {
                            NBTTagList lore = nbtTagCompound.getTagList("Lore", NBTTagType.STRING.getId());
                            String drugPurityNbt = lore.getStringTagAt(1);
                            String amountNbt = lore.getStringTagAt(2);

                            Matcher loreMatcher = Pattern.compile("» (?<amount>\\d+)g").matcher(amountNbt);
                            if (loreMatcher.find()) {
                                int amount = Integer.parseInt(loreMatcher.group("amount"));
                                DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName());
                                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugPurityNbt);

                                if (drugType != null) {
                                    Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap = unicacityAddon.fileService().data().getDrugInventoryMap();
                                    Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
                                    drugPurityMap.put(drugPurity, amount);
                                    drugInventoryMap.put(drugType, drugPurityMap);
                                    unicacityAddon.fileService().data().setDrugInventoryMap(drugInventoryMap);
                                }
                            }
                        }
                    });

            // go back to inventory container
            this.inventoryClick(unicacityAddon, 4);
        }
    }
}