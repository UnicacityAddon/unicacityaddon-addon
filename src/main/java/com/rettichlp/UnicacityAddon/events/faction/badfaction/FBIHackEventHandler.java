package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import net.labymod.utils.ModUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class FBIHackEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        Matcher fbiHackStartedMatcher = PatternHandler.FBI_HACK_STARTED_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (!fbiHackStartedMatcher.find()) return false;

        FBIHackModule.startCountdown(Integer.parseInt(fbiHackStartedMatcher.group(1)));
        return false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!FBIHackModule.fbiHackStarted|| ++FBIHackModule.currentTick != 20) return;
        FBIHackModule.currentTick = 0;

        if (FBIHackModule.currentCount-- <= 30) FBIHackModule.timer = ColorCode.RED.getCode() + ModUtils.parseTimer(FBIHackModule.currentCount);
        else FBIHackModule.timer = ModUtils.parseTimer(FBIHackModule.currentCount);

        if (FBIHackModule.currentCount <= 0) FBIHackModule.stopCountdown();
    }
}
