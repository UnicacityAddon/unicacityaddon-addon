package com.rettichlp.unicacityaddon.v1_18_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.controller.GuiController;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTTagType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;

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

        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            ChestMenu chestMenu = ((ContainerScreen) screen).getMenu();

            NonNullList<ItemStack> itemStacks = chestMenu.getItems();
            Optional<ItemStack> hoveredItemStackOptional = itemStacks.stream()
                    .filter(itemStack -> itemStack.getDisplayName().getString().contains(displayName))
                    .findFirst();

            if (hoveredItemStackOptional.isPresent()) {
                slotNumber = itemStacks.indexOf(hoveredItemStackOptional.get());
            }
        }

        return slotNumber;
    }

    @Override
    public void inventoryClick(int slotNumber) {
        Screen screen = Minecraft.getInstance().screen;

        int containerId = 0;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            containerId = ((ContainerScreen) screen).getMenu().containerId;
        } else if (screen instanceof HopperScreen && ((HopperScreen) screen).getMenu() instanceof HopperMenu) {
            containerId = ((HopperScreen) screen).getMenu().containerId;
        }

        LocalPlayer localPlayer = Minecraft.getInstance().player;
        assert localPlayer != null;

        ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(containerId, 0, slotNumber, 1, ClickType.PICKUP, localPlayer.containerMenu.getCarried(), Int2ObjectMaps.emptyMap());
        localPlayer.connection.send(serverboundContainerClickPacket);
    }

    @Override
    public void updateDrugInventoryMap(UnicacityAddon unicacityAddon) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            ChestMenu chestMenu = ((ContainerScreen) screen).getMenu();

            int windowId = chestMenu.containerId;
            if (unicacityAddon.utilService().command().getLastWindowId() == windowId)
                return;

            unicacityAddon.utilService().command().setLastWindowId(windowId);

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
                chestMenu.getItems().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && !itemStack.getDisplayName().getString().contains("Kokain") && !itemStack.getDisplayName().getString().contains("Kräuter") && !itemStack.getDisplayName().getString().contains("Kristalle"))
                        .forEach(itemStack -> {
                            assert itemStack.getTag() != null;
                            CompoundTag compoundTag = itemStack.getTag().getCompound("display");
                            String lore = compoundTag.getList("Lore", NBTTagType.STRING.getId()).getString(0);

                            Matcher loreMatcher = Pattern.compile("» (?<amount>\\d+)(g| Pillen| Flaschen| Päckchen| Stück| Kisten)").matcher(lore);
                            if (loreMatcher.find()) {
                                int amount = Integer.parseInt(loreMatcher.group("amount"));
                                DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName().getString());

                                if (drugType != null) {
                                    Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap = unicacityAddon.fileService().data().getDrugInventoryMap();
                                    Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
                                    drugPurityMap.put(DrugPurity.BEST, amount);
                                    drugInventoryMap.put(drugType, drugPurityMap);
                                    unicacityAddon.fileService().data().setDrugInventoryMap(drugInventoryMap);
                                }
                            }
                        });

                unicacityAddon.utilService().command().setActiveDrugInventoryLoading(false);
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.closeContainer();
            }
        } else if (screen instanceof HopperScreen && unicacityAddon.utilService().command().isActiveDrugInventoryLoading()) {
            HopperMenu hopperMenu = ((HopperScreen) screen).getMenu();

            int windowId = hopperMenu.containerId;
            if (unicacityAddon.utilService().command().getLastWindowId() == windowId)
                return;

            unicacityAddon.utilService().command().setLastWindowId(windowId);

            hopperMenu.getItems().stream()
                    .filter(itemStack -> !itemStack.isEmpty())
                    .forEach(itemStack -> {
                        assert itemStack.getTag() != null;
                        CompoundTag compoundTag = itemStack.getTag().getCompound("display");
                        ListTag lore = compoundTag.getList("Lore", NBTTagType.STRING.getId());
                        String drugPurityNbt = lore.getString(1);
                        String amountNbt = lore.getString(2);

                        Matcher loreMatcher = Pattern.compile("» (?<amount>\\d+)g").matcher(amountNbt);
                        if (loreMatcher.find()) {
                            int amount = Integer.parseInt(loreMatcher.group("amount"));
                            DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName().getString());
                            DrugPurity drugPurity = DrugPurity.getDrugPurity(drugPurityNbt);

                            if (drugType != null) {
                                Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap = unicacityAddon.fileService().data().getDrugInventoryMap();
                                Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
                                drugPurityMap.put(drugPurity, amount);
                                drugInventoryMap.put(drugType, drugPurityMap);
                                unicacityAddon.fileService().data().setDrugInventoryMap(drugInventoryMap);
                            }
                        }
                    });

            // go back to inventory container
            this.inventoryClick(4);
        }
    }

    @Override
    public void setSelectedHotbarSlot(int slotNumber) {
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.getInventory().selected = slotNumber;
    }
}