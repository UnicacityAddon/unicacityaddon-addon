package com.rettichlp.UnicacityAddon.events;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DeathsKillsEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher jailKillMatcher = PatternHandler.WANTED_KILL.matcher(msg);
        if (jailKillMatcher.find()) {
            if (jailKillMatcher.group(2).equals(p.getName()))
                APIRequest.sendStatisticAddRequest("KILL");
            return;
        }

        Matcher contractKillPattern = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(msg);
        if (contractKillPattern.find() && msg.contains("getötet")) {
            if (msg.contains(p.getName())) {
                APIRequest.sendStatisticAddRequest("KILL");
                AFbankEinzahlenCommand.sendClockMessage();
            }
            return;
        }

        if (PatternHandler.DEATH_PATTERN.matcher(msg).find()) {
            APIRequest.sendStatisticAddRequest("DEATH");
            return;
        }

        if (PatternHandler.LAST_STATS_MESSAGE_PATTERN.matcher(msg).find()) {
            JsonObject response = APIRequest.sendStatisticRequest();
            if (response == null) return;

            JsonObject gameplayJsonObject = response.getAsJsonObject("gameplay");
            int deaths = gameplayJsonObject.get("deaths").getAsInt();
            int kills = gameplayJsonObject.get("kills").getAsInt();
            float kd = gameplayJsonObject.get("kd").getAsFloat();

            p.sendMessage(Message.getBuilder()
                    .of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("Tode").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(deaths + " Tode").color(ColorCode.RED).advance()
                    .createComponent());

            p.sendMessage(Message.getBuilder()
                    .of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("Kills").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(kills + " Kills").color(ColorCode.RED).advance()
                    .createComponent());

            p.sendMessage(Message.getBuilder()
                    .of("  - ").color(ColorCode.DARK_GRAY).advance()
                    .of("K/D").color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(MathUtils.DECIMAL_FORMAT.format(kd)).color(ColorCode.RED).advance()
                    .createComponent());
        }
    }
}