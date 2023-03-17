package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.OverlayMessageController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(overlayMessage, true);
    }
}
