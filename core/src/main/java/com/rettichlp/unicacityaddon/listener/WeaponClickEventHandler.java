package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.WeaponShotEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/events/WeaponClickEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class WeaponClickEventHandler {

    private static boolean tazerLoaded = false;
    private long tazerLastWarningSend = 0;

    private final UnicacityAddon unicacityAddon;

    public WeaponClickEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        ClientPlayerInteractEvent.InteractionType interactionType = e.type();

        if (interactionType.equals(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            ClientPlayer clientPlayer = e.clientPlayer();
            ItemStack mainHandItemStack = clientPlayer.getMainHandItemStack();

            Weapon weapon = Weapon.getWeaponByItemName(TextUtils.legacy(mainHandItemStack.getDisplayName()));
            if (weapon != null) {
                NBTTagCompound nbtTagCompound = mainHandItemStack.getNBTTag();
                if (nbtTagCompound != null) {
                    NBTTag<?> nbtTag = nbtTagCompound.get("display");
                    if (nbtTag != null) {
                        Matcher matcher = Pattern.compile("(\\d+)/(\\d+)").matcher(nbtTag.value().toString());
                        if (matcher.find()) {
                            int loaded = Integer.parseInt(matcher.group(1)) - 1;
                            int backup = Integer.parseInt(matcher.group(2));
                            UnicacityAddon.ADDON.labyAPI().eventBus().fire(new WeaponShotEvent(weapon, loaded, backup));
                        }
                    }
                }
            }
        } else if (interactionType.equals(ClientPlayerInteractEvent.InteractionType.ATTACK) && tazerLoaded && System.currentTimeMillis() - tazerLastWarningSend > TimeUnit.SECONDS.toMillis(5)) {
            UnicacityAddon.PLAYER.sendInfoMessage("Achtung! Dein Tazer ist geladen!");
            tazerLastWarningSend = System.currentTimeMillis();
        }
    }

    @Subscribe
    public void onWeaponShot(WeaponShotEvent e) {
        tazerLoaded = false;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        if (msg.equals("Dein Tazer ist nun bereit!")) {
            tazerLoaded = true;
        } else if (msg.equals("Dein Tazer ist nun nicht mehr bereit!") || msg.equals("Dein Tazer muss sich noch aufladen...")) {
            tazerLoaded = false;
        }
    }
}