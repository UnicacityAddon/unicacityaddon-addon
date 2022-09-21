package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DeathsKillsEventHandler {

    public static int kills;
    public static int deaths;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher karmaMatcher = PatternHandler.KILL_PATTERN.matcher(msg);

        if (karmaMatcher.find()) {
            if (Integer.parseInt(karmaMatcher.group(1)) < 6)
                kills++;

            return false;
        }

        Matcher jailKillMatcher = PatternHandler.WANTED_KILL.matcher(msg);
        if (jailKillMatcher.find()) {
            if (jailKillMatcher.group(2).equals(p.getName()))
                kills++;

            return false;
        }

        Matcher contractKillPattern = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(msg);
        if (contractKillPattern.find()) {
            if (contractKillPattern.group(1).equals(p.getName()))
                kills++;

            return false;
        }

        if (PatternHandler.DEATH_PATTERN.matcher(msg).find()) {
            deaths++;
            return false;
        }

        if (PatternHandler.LAST_STATS_MESSAGE_PATTERN.matcher(msg).find()) {
            final DecimalFormat format = new DecimalFormat("###0.0#");

            Message.getBuilder().of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("Tode").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(deaths + " Tode").color(ColorCode.RED).advance().sendTo(p.getPlayer());

            Message.getBuilder().of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("Kills").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(kills + " Kills").color(ColorCode.RED).advance().sendTo(p.getPlayer());

            double kd;
            if (kills == 0 && deaths == 0)
                kd = 0.0;
            else
                kd = ((double) kills) / ((double) deaths);

            Message.getBuilder().of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("K/D").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of("" + format.format(kd)).color(ColorCode.RED).advance().sendTo(p.getPlayer());
        }
        return false;
    }
}
