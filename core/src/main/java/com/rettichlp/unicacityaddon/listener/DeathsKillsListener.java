package com.rettichlp.unicacityaddon.listener;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DeathsKillsListener {

    private final UnicacityAddon unicacityAddon;

    public DeathsKillsListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        Matcher jailKillMatcher = PatternHandler.WANTED_KILL.matcher(msg);
        if (jailKillMatcher.find()) {
            if (jailKillMatcher.group(2).equals(p.getName()))
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
            return;
        }

        Matcher contractKillPattern = PatternHandler.CONTRACT_REMOVED_PATTERN.matcher(msg);
        if (contractKillPattern.find() && msg.contains("getötet")) {
            if (msg.contains(p.getName())) {
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
                AFbankEinzahlenCommand.sendClockMessage();
            }
            return;
        }

        if (PatternHandler.ACCOUNT_TREUEBONUS_PATTERN.matcher(msg).find()) {
            JsonObject response = APIRequest.sendStatisticRequest();
            if (response == null)
                return;

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