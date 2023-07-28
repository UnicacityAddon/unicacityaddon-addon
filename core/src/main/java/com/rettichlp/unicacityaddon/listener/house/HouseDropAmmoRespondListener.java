package com.rettichlp.unicacityaddon.listener.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.house.ADropAmmoCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Kifo
 */
@UCEvent
public class HouseDropAmmoRespondListener {

    private final UnicacityAddon unicacityAddon;

    public HouseDropAmmoRespondListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher houseNotHomeMatcher = PatternHandler.HOUSE_NOT_HOME_PATTERN.matcher(msg);
        if(houseNotHomeMatcher.find()) {
            if(ADropAmmoCommand.RUNNING.get()) {
                ADropAmmoCommand.RUNNING.set(false);
                e.setMessage(Message.getBuilder().error()
                        .of("Du bist nicht zuhause.").advance().createComponent());
                return;
            }
        }

        Matcher houseAmmunitionDropCommandMatcher = PatternHandler.HOUSE_AMMUNITION_DROP_COMMAND_PATTERN.matcher(msg);
        if(houseAmmunitionDropCommandMatcher.find()) {
            if(ADropAmmoCommand.RUNNING.get()) {
                ADropAmmoCommand.RUNNING.set(false);
                e.setMessage(Message.getBuilder().error()
                        .of("Beim Verwenden des Befehls ist ein Fehler aufgetreten.").advance().createComponent());
                return;
            }
        }

        Matcher houseAmmunitionDropWeaponNotInInventoryMatcher = PatternHandler.HOUSE_AMMUNITION_DROP_WEAPON_NOT_IN_INVENTORY_PATTERN.matcher(msg);
        if(houseAmmunitionDropWeaponNotInInventoryMatcher.find()) {
            if(ADropAmmoCommand.RUNNING.get()) {
                ADropAmmoCommand.RUNNING.set(false);
                e.setMessage(Message.getBuilder().error()
                        .of("Du hast diese Waffe nicht bei dir.").advance().createComponent());
            }
        }
    }

}
