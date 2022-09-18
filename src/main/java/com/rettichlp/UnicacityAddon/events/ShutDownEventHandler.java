package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class ShutDownEventHandler {

    public static boolean shutdownJail = false;
    public static boolean shutdownFriedhof = false;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (shutdownFriedhof && msg.equals("Du lebst nun wieder.")) ForgeUtils.shutdownPC();
        if (shutdownJail && msg.equals("[Gefängnis] Du bist wieder frei!")) ForgeUtils.shutdownPC();

        return false;
    }
}
