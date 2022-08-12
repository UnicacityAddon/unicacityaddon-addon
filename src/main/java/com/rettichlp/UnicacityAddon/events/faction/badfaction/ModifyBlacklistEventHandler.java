package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.ModifyBlacklistCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class ModifyBlacklistEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (System.currentTimeMillis() - ModifyBlacklistCommand.executedTime > 1000L) return false;

        String msg = e.getMessage().getUnformattedText();

        // remove start message
        Matcher startPattern = PatternHandler.BLACKLIST_START_PATTERN.matcher(msg);
        if (startPattern.find()) {
            e.setCanceled(true);
            return false;
        }

        Matcher listPattern = PatternHandler.BLACKLIST_LIST_PATTERN.matcher(msg);
        if (!listPattern.find()) return false;

        // remove list message
        e.setCanceled(true);

        // extract variables
        String name = listPattern.group(1);
        String reason = listPattern.group(2);
        // issuer (group 3) is ignored
        int kills = Integer.parseInt(listPattern.group(4));
        int price = Integer.parseInt(listPattern.group(5));

        if (!name.equals(ModifyBlacklistCommand.target)) return false;

        if (ModifyBlacklistCommand.type == ModifyBlacklistCommand.ModifyBlacklistType.OUTLAW) {
            if (reason.contains("[Vogelfrei]")) {
                Message.getBuilder().error().space().of("Der Spieler ist bereits vogelfrei.").color(ColorCode.GRAY)
                .advance().sendTo(AbstractionLayer.getPlayer().getPlayer());
                return false;
            }

            reason = removeModifiers(reason);
            reason += " [Vogelfrei]"; // append outlaw reason
        } else {
            if (reason.contains(ModifyBlacklistCommand.addReason.getReason())) {
                Message.getBuilder().error().space().of("Der Spieler besitzt diesen Blacklistgrund bereits.").color(ColorCode.GRAY)
                        .advance().sendTo(AbstractionLayer.getPlayer().getPlayer());
                return false;
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
        UPlayer p = AbstractionLayer.getPlayer();
        p.sendChatMessage("/bl del " + ModifyBlacklistCommand.target);
        p.sendChatMessage("/bl set " + ModifyBlacklistCommand.target + " " + kills + " " + price + " " + reason);

        return false;
    }

    //Removes all known Modifiers
    private static String removeModifiers(String reason) {
        reason = reason.replaceAll(" (:?\\[|\\().+(:?\\]|\\))", "");

        return reason;
    }
}