package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import lombok.NoArgsConstructor;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Set;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/events/WeaponClickEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
@NoArgsConstructor
public class WeaponClickEventHandler {

    private static final Set<String> WEAPONS = Set.of("§8M4", "§8MP5", "§8Pistole", "§8Jagdflinte");
    public static boolean tazerLoaded = false;
    private long tazerLastWarningSend = 0;

//    @Subscribe
//    public void onPlayerInteract(PlayerInteractEvent e) {
//        if (!(e instanceof PlayerInteractEvent.RightClickItem || e instanceof PlayerInteractEvent.RightClickBlock || e instanceof PlayerInteractEvent.EntityInteractSpecific))
//            return;
//
//        ItemStack is = e.getItemStack();
//        if (!isWeapon(is))
//            return;
//
//        tazerLoaded = false;
//        handleMunitionDisplay(is);
//    }

    private static void handleMunitionDisplay(ItemStack is) {
        String text = getText(is);
        if (text == null)
            return;

//        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(text, true);
    }

    private static String getText(ItemStack is) {
//        NBTTagCompound nbt = is.getTagCompound();
//        if (nbt == null)
//            return null;
//
//        NBTTagCompound display = nbt.getCompoundTag("display");
//
//        String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
//        String[] splittedLore = lore.split("/");
//        if (splittedLore.length != 2)
//            return null;
//
//        String munitionString = splittedLore[0];
//        if (munitionString.length() < 2)
//            return null;
//
//        int munition = Integer.parseInt(munitionString.substring(2));
//
//        return (--munition < 1 ? ColorCode.RED.getCode() + "0" : ColorCode.GOLD.getCode() + munition) + ColorCode.GRAY.getCode() + "/" + ColorCode.GOLD.getCode() + splittedLore[1];
        return "";
    }

    private boolean isWeapon(ItemStack is) {
        if (is == null)
            return false;

        return WEAPONS.contains(is.getDisplayName());
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String message = e.chatMessage().getPlainText();
        if (message.equals("Dein Tazer ist nun bereit!")) {
            tazerLoaded = true;
        } else if (message.equals("Dein Tazer ist nun nicht mehr bereit!") || message.equals("Dein Tazer muss sich noch aufladen...")) {
            tazerLoaded = false;
        }
    }

//    @SubscribeEvent
//    public void onWeaponInteract(PlayerInteractEvent e) {
//        if (!tazerLoaded)
//            return;
//        if (!(e instanceof PlayerInteractEvent.LeftClickBlock || e instanceof PlayerInteractEvent.EntityInteractSpecific || e instanceof PlayerInteractEvent.LeftClickEmpty))
//            return;
//
//        if (System.currentTimeMillis() - tazerLastWarningSend < TimeUnit.SECONDS.toMillis(5))
//            return;
//
//        AbstractionLayer.getPlayer().sendInfoMessage("Achtung! Dein Tazer ist geladen!");
//        tazerLastWarningSend = System.currentTimeMillis();
//    }
}