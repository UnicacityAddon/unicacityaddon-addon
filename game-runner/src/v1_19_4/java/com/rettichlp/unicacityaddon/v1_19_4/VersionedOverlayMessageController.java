package com.rettichlp.unicacityaddon.v1_19_4;

import com.rettichlp.unicacityaddon.controller.OverlayMessageController;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * @author RettichLP
 */
@Singleton
@Implements(OverlayMessageController.class)
public class VersionedOverlayMessageController extends OverlayMessageController {

    @Inject
    public VersionedOverlayMessageController() {
    }

    @Override
    public void sendOverlayMessage(String overlayMessage) {
        Minecraft.getInstance().gui.setOverlayMessage(Component.nullToEmpty(overlayMessage), true);
    }
}
