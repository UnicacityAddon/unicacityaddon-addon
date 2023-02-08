package com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.enums.faction.ModifyBlacklistType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.ModifyBlacklistCommand;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class BlacklistModifyEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        if (System.currentTimeMillis() - ModifyBlacklistCommand.executedTime > 1000L)
            return;
        UPlayer p = AbstractionLayer.getPlayer();

        String msg = e.chatMessage().getPlainText();

        // remove start message
        Matcher startPattern = PatternHandler.BLACKLIST_START_PATTERN.matcher(msg);
        if (startPattern.find()) {
            e.setCancelled(true);
            return;
        }

        Matcher listPattern = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg);
        if (!listPattern.find())
            return;

        // remove list message
        e.setCancelled(true);

        // extract variables
        String name = listPattern.group(1);
        String reason = listPattern.group(2);
        // issuer (group 3) is ignored
        int kills = Integer.parseInt(listPattern.group(4));
        int price = Integer.parseInt(listPattern.group(5));

        if (!name.equals(ModifyBlacklistCommand.target))
            return;

        int outlawPriceModificator = 0;

        if (ModifyBlacklistCommand.type == ModifyBlacklistType.OUTLAW) {
            if (reason.contains("[Vogelfrei]")) {
                p.sendErrorMessage("Der Spieler ist bereits vogelfrei!");
                return;
            }

            reason = removeModifiers(reason);
            reason += " [Vogelfrei]"; // append outlaw reason

            outlawPriceModificator = p.getFaction().equals(Faction.LEMILIEU) ? 500 : 0;
        } else {
            if (reason.contains(ModifyBlacklistCommand.addReason.getReason())) {
                p.sendErrorMessage("Der Spieler besitzt diesen Blacklistgrund bereits!");
                return;
            }

            kills = Math.min(kills + ModifyBlacklistCommand.addReason.getKills(), 100); // max 100 kills
            price = Math.min(price + ModifyBlacklistCommand.addReason.getPrice(), 10000); // max 10.000$ bounty
            if (ModifyBlacklistCommand.addReason.getReason().startsWith("[") || ModifyBlacklistCommand.addReason.getReason().startsWith("(")) {
                reason = removeModifiers(reason);
                reason = reason + " " + ModifyBlacklistCommand.addReason.getReason(); // append modifier to blacklist
            } else {
                reason = ModifyBlacklistCommand.addReason.getReason() + " + " + reason; // prepend reason to original one
            }

        }

        // delete from and re-add blacklist
        p.sendChatMessage("/bl del " + ModifyBlacklistCommand.target);
        p.sendChatMessage("/bl set " + ModifyBlacklistCommand.target + " " + kills + " " + (price + outlawPriceModificator) + " " + reason);
    }

    //Removes all known Modifiers
    private static String removeModifiers(String reason) {
        reason = reason.replaceAll(" (:?\\[|\\().+(:?]|\\))", "");

        return reason;
    }
}
