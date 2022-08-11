package com.rettichlp.UnicacityAddon.events;

import com.google.common.collect.ImmutableSet;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.modules.AmmunitionModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

/**
 * @author RettichLP
 */
public class WeaponClickEventHandler {

    private static final Set<String> WEAPONS = ImmutableSet.of("§8M4", "§8MP5", "§8Pistole", "§8Jagdflinte");
    public static boolean tazerLoaded = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickItem || e instanceof PlayerInteractEvent.RightClickBlock || e instanceof PlayerInteractEvent.EntityInteractSpecific))
            return;

        ItemStack is = e.getItemStack();
        if (!isWeapon(is)) {
            AmmunitionModule.isShown = false;
            return;
        }
        AmmunitionModule.isShown = true;

        tazerLoaded = false;

        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null) return;

        NBTTagCompound display = nbt.getCompoundTag("display");

        String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
        if (!lore.startsWith(ColorCode.GOLD.getCode()) || !lore.contains("/")) return;

        String[] splittetLore = lore.split("/");
        if (splittetLore[0].length() < 2) return;

        AmmunitionModule.currentLoadedAmmunition = Integer.parseInt(splittetLore[0].substring(2));
        AmmunitionModule.currentTakenAmmunition = Integer.parseInt(splittetLore[1]);
    }

    private boolean isWeapon(ItemStack is) {
        if (is == null) return false;

        return WEAPONS.contains(is.getDisplayName());
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.getMessage().getUnformattedText();
        if (message.equals("Dein Tazer ist nun bereit!")) {
            tazerLoaded = true;
        } else if (message.equals("Dein Tazer ist nun nicht mehr bereit!") || message.equals("Dein Tazer muss sich noch aufladen...")) {
            tazerLoaded = false;
        }
    }

    @SubscribeEvent
    public void onWeaponInteract(PlayerInteractEvent e) {
        if (!tazerLoaded) return;
        if (!(e instanceof PlayerInteractEvent.LeftClickBlock || e instanceof PlayerInteractEvent.EntityInteractSpecific || e instanceof PlayerInteractEvent.LeftClickEmpty))
            return;

        AbstractionLayer.getPlayer().sendInfoMessage("Achtung! Dein Tazer ist geladen!");
    }
}