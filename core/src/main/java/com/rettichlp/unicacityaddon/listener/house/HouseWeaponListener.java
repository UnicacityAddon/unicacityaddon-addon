package com.rettichlp.unicacityaddon.listener.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.house.HouseDropAmmunitionCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author Kifo
 * @author RettichLP
 */
@UCEvent
public class HouseWeaponListener {

    private final UnicacityAddon unicacityAddon;

    public HouseWeaponListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        Matcher houseDropAmmunitionMatcher = PatternHandler.HOUSE_AMMUNITION_DROP_PATTERN.matcher(msg);
        if (houseDropAmmunitionMatcher.find()) {
            int droppedAmount = Integer.parseInt(houseDropAmmunitionMatcher.group("amount"));
            Weapon weapon = Weapon.getWeaponByName(houseDropAmmunitionMatcher.group("weapon"));

            int newDropAmount = HouseDropAmmunitionCommand.dropAmount - droppedAmount;

            if (newDropAmount > 0) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendServerMessage("/dropammo " + weapon.getName() + " " + Math.min(newDropAmount, 350));
                        HouseDropAmmunitionCommand.dropAmount = newDropAmount;
                    }
                }, TimeUnit.SECONDS.toMillis(1));
            }
        }
    }
}