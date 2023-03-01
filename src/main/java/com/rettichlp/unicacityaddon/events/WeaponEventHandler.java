package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.models.Armament;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.commands.GetGunCommand;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/events/WeaponClickEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class WeaponEventHandler {

    public static boolean tazerLoaded = false;

    private long tazerLastWarningSend = 0;
    private int lastWindowId = 0;

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        String message = e.getMessage().getUnformattedText();
        if (message.equals("Dein Tazer ist nun bereit!")) {
            tazerLoaded = true;
        } else if (message.equals("Dein Tazer ist nun nicht mehr bereit!") || message.equals("Dein Tazer muss sich noch aufladen...")) {
            tazerLoaded = false;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (tazerLoaded && System.currentTimeMillis() - tazerLastWarningSend > TimeUnit.SECONDS.toMillis(5) && (e instanceof PlayerInteractEvent.LeftClickBlock || e instanceof PlayerInteractEvent.LeftClickEmpty || e instanceof PlayerInteractEvent.EntityInteractSpecific)) {
            AbstractionLayer.getPlayer().sendInfoMessage("Achtung! Dein Tazer ist geladen!");
            tazerLastWarningSend = System.currentTimeMillis();
        }

        if (e instanceof PlayerInteractEvent.RightClickItem || e instanceof PlayerInteractEvent.RightClickBlock || e instanceof PlayerInteractEvent.EntityInteractSpecific) {
            ItemStack is = e.getItemStack();
            Weapon weapon = Weapon.getWeaponByItemName(is.getDisplayName());
            if (weapon != null) {
                tazerLoaded = false;
                handleMunitionDisplay(is);
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiContainerEvent.DrawForeground e) {
        GuiContainer guiContainer = e.getGuiContainer();
        Armament armament = GetGunCommand.armament;
        if (armament != null && lastWindowId != guiContainer.inventorySlots.windowId) {
            lastWindowId = guiContainer.inventorySlots.windowId;

            Slot slot = guiContainer.inventorySlots.inventorySlots.stream()
                    .filter(s -> s.getStack().getDisplayName().contains(armament.getWeapon().getName()))
                    .findFirst()
                    .orElse(null);

            if (slot != null) {
                UnicacityAddon.MINECRAFT.playerController.windowClick(lastWindowId, slot.slotNumber, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
                GetGunCommand.armament = null;
                AbstractionLayer.getPlayer().sendChatMessage("/getammo " + armament.getWeapon().getName() + " " + armament.getAmount());
            }
        }
    }

    private void handleMunitionDisplay(ItemStack is) {
        String text = getText(is);
        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(text != null ? text : "", true);
    }

    private String getText(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt != null) {
            NBTTagCompound display = nbt.getCompoundTag("display");
            String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
            String[] splittedLore = lore.split("/");

            if (splittedLore.length == 2) {
                String munitionString = splittedLore[0];
                if (munitionString.length() >= 2) {
                    int munition = Integer.parseInt(munitionString.substring(2));
                    return (--munition < 1 ? ColorCode.RED.getCode() + "0" : ColorCode.GOLD.getCode() + munition) + ColorCode.GRAY.getCode() + "/" + ColorCode.GOLD.getCode() + splittedLore[1];
                }
            }
        }
        return null;
    }
}