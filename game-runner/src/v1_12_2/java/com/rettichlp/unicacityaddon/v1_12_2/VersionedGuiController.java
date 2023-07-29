package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.controller.GuiController;
import com.rettichlp.unicacityaddon.listener.ScreenRenderListener;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTTagType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
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

    private int windowId = 0;

    @Override
    public int getSlotNumberByDisplayName(String displayName) {
        int slotNumber = -1;

        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest containerChest) {
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
    public String getContainerLegacyName() {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        return guiScreen instanceof GuiContainer guiContainer && guiContainer.inventorySlots instanceof ContainerChest containerChest
                ? containerChest.getLowerChestInventory().getName()
                : null;
    }

    @Override
    public void inventoryClick(int slotNumber) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;

        this.windowId = 0;
        if (guiScreen instanceof GuiContainer guiContainer && guiContainer.inventorySlots instanceof ContainerChest containerChest) {
            this.windowId = containerChest.windowId;
        } else if (guiScreen instanceof GuiHopper guiHopper && guiHopper.inventorySlots instanceof ContainerHopper containerHopper) {
            this.windowId = containerHopper.windowId;
        }

        Minecraft.getMinecraft().playerController.windowClick(this.windowId, slotNumber, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
    }

    @Override
    public void updateDrugInventoryMap(UnicacityAddon unicacityAddon) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiChest guiChest) {
            this.windowId = guiChest.inventorySlots.windowId;
            if (unicacityAddon.utilService().command().getLastWindowId() == this.windowId)
                return;

            unicacityAddon.utilService().command().setLastWindowId(this.windowId);

            if (unicacityAddon.utilService().command().isCocaineCheck()) {
                unicacityAddon.utilService().command().setCocaineCheck(false);
                // select cocaine to check drug purity
                this.inventoryClick(0);
            } else if (unicacityAddon.utilService().command().isMarihuanaCheck()) {
                unicacityAddon.utilService().command().setMarihuanaCheck(false);
                // select marihuana to check drug purity
                this.inventoryClick(1);
            } else if (unicacityAddon.utilService().command().isMethCheck()) {
                unicacityAddon.utilService().command().setMethCheck(false);
                // select meth to check drug purity
                this.inventoryClick(2);
            } else {
                guiChest.inventorySlots.getInventory().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && !itemStack.getDisplayName().contains("Pulver") && !itemStack.getDisplayName().contains("Kräuter") && !itemStack.getDisplayName().contains("Kristalle"))
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

                unicacityAddon.utilService().command().setActiveDrugInventoryLoading(false);
                Minecraft.getMinecraft().player.closeScreen();
            }
        } else if (guiScreen instanceof GuiHopper guiHopper && unicacityAddon.utilService().command().isActiveDrugInventoryLoading()) {
            this.windowId = guiHopper.inventorySlots.windowId;
            if (unicacityAddon.utilService().command().getLastWindowId() == this.windowId)
                return;

            unicacityAddon.utilService().command().setLastWindowId(this.windowId);

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
            this.inventoryClick(4);
        }
    }

    @Override
    public void setSelectedHotbarSlot(int slotNumber) {
        Minecraft.getMinecraft().player.inventory.currentItem = slotNumber;
    }

    @Override
    public void updateSetting(boolean expectedValue) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiChest guiChest && !ScreenRenderListener.settingPath.isEmpty()) {
            Container container = guiChest.inventorySlots;
            if (container.windowId != this.windowId && container instanceof ContainerChest) {
                this.windowId = container.windowId;

                if (ScreenRenderListener.settingPath.size() > 1) {
                    this.inventoryClick(ScreenRenderListener.settingPath.remove(0));
                } else {
                    int slotNumber = ScreenRenderListener.settingPath.remove(0);
                    ItemStack itemStack = guiChest.inventorySlots.getInventory().get(slotNumber);
                    NBTTagCompound nbtTagCompound = itemStack.getSubCompound("display");
                    if (nbtTagCompound != null) {
                        String lore = nbtTagCompound.getTagList("Lore", NBTTagType.STRING.getId()).getStringTagAt(0);
                        if (lore.equals("§cAktiviere den Hitsound") && expectedValue) {
                            this.inventoryClick(slotNumber);
                        }
                    }
                    Minecraft.getMinecraft().player.closeScreen();
                }
            }
        }
    }
}