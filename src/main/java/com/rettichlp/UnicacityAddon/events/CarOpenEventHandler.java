package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import net.labymod.api.events.MessageReceiveEvent;

@UCEventLabymod(event = "MessageReceiveEvent")
public class CarOpenEventHandler implements MessageReceiveEvent {

    @Override
    public boolean onReceive(String s, String s1) {
        if (PatternHandler.CAR_OPEN_PATTERN.matcher(s1).find()) {
            CarOpenModule.info = ColorCode.GREEN.getCode() + "offen";
            return false;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(s1).find()) {
            CarOpenModule.info = ColorCode.RED.getCode() + "zu";
        }
        return false;
    }
}
