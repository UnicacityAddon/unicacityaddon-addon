package com.rettichlp.unicacityaddon.v1_16_5;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.controller.GuiController;
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
    public void inventoryClick(UnicacityAddon unicacityAddon, int slotNumber) {
        Screen screen = Minecraft.getInstance().screen;

        int containerId = 0;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            containerId = ((ContainerScreen) screen).getMenu().containerId;
        } else if (screen instanceof HopperScreen && ((HopperScreen) screen).getMenu() instanceof HopperMenu) {
            containerId = ((HopperScreen) screen).getMenu().containerId;
        }

        LocalPlayer localPlayer = Minecraft.getInstance().player;
        assert localPlayer != null;

        ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(containerId, slotNumber, 0, ClickType.PICKUP, localPlayer.inventory.getSelected(), (short) 0);
        localPlayer.connection.send(serverboundContainerClickPacket);
    }

    @Override
    public void updateDrugInventoryMap(UnicacityAddon unicacityAddon) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            ChestMenu chestMenu = ((ContainerScreen) screen).getMenu();

            int windowId = chestMenu.containerId;
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
                chestMenu.getItems().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && !itemStack.getDisplayName().getString().contains("Kokain") && !itemStack.getDisplayName().getString().contains("Marihuana") && !itemStack.getDisplayName().getString().contains("Methamphetamin"))
                        .forEach(itemStack -> {
                            assert itemStack.getTag() != null;
                            CompoundTag compoundTag = itemStack.getTag().getCompound("display");
                            if (compoundTag != null) {
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
                            }
                        });

                DropDrugAllCommand.active = false;
                DropDrugAllCommand.dropCommandExecution(unicacityAddon);
                assert Minecraft.getInstance().player != null;
                Minecraft.getInstance().player.closeContainer();
            }
        } else if (screen instanceof HopperScreen && DropDrugAllCommand.active) {
            HopperMenu hopperMenu = ((HopperScreen) screen).getMenu();

            int windowId = hopperMenu.containerId;
            if (windowId == DropDrugAllCommand.lastWindowId)
                return;

            DropDrugAllCommand.lastWindowId = windowId;

            hopperMenu.getItems().stream()
                    .filter(itemStack -> !itemStack.isEmpty())
                    .forEach(itemStack -> {
                        assert itemStack.getTag() != null;
                        CompoundTag compoundTag = itemStack.getTag().getCompound("display");
                        if (compoundTag != null) {
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
                        }
                    });

            // go back to inventory container
            this.inventoryClick(unicacityAddon, 4);
        }
    }
}