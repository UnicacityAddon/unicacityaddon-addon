package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class ShutDownEventHandler {

    public static boolean shutdownJail = false;
    public static boolean shutdownFriedhof = false;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        if (shutdownFriedhof && msg.equals("Du lebst nun wieder."))
            ForgeUtils.shutdownPC();
        if (shutdownJail && msg.equals("[Gefängnis] Du bist wieder frei!"))
            ForgeUtils.shutdownPC();
    }
}
