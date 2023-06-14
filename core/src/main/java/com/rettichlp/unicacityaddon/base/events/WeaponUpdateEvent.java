package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.Event;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@Getter
public class WeaponUpdateEvent implements Event {

    private final Weapon weapon;
    private final int loaded;
    private final int backup;

    public WeaponUpdateEvent(UnicacityAddon unicacityAddon, Weapon weapon) {
        this.weapon = weapon;

        Matcher matcher = getAmmunitionNBTTagMatcher(unicacityAddon);
        this.loaded = matcher != null ? Integer.parseInt(matcher.group(1)) : 0;
        this.backup = matcher != null ? Integer.parseInt(matcher.group(2)) : 0;
    }

    public String getWeaponAmmunitionText() {
        return Message.getBuilder()
                .of(String.valueOf(Math.max(loaded, 0))).color(loaded == 0 ? ColorCode.RED : ColorCode.GOLD).advance()
                .of("/").color(ColorCode.DARK_GRAY).advance()
                .of(String.valueOf(backup)).color(ColorCode.GOLD).advance()
                .create();
    }

    private Matcher getAmmunitionNBTTagMatcher(UnicacityAddon unicacityAddon) {
        ClientPlayer clientPlayer = unicacityAddon.player().getPlayer();
        if (clientPlayer != null) {
            NBTTagCompound nbtTagCompound = clientPlayer.getMainHandItemStack().getNBTTag();
            if (nbtTagCompound != null) {
                NBTTag<?> nbtTag = nbtTagCompound.get("display");
                Matcher matcher = Pattern.compile("(\\d+)/(\\d+)").matcher(nbtTag != null ? nbtTag.toString() : "");
                if (matcher.find()) {
                    return matcher;
                }
            }
        }
        return null;
    }
}