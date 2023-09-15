package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.NotificationService;

/**
 * @author RettichLP
 */
public class UnicacityAddonException extends Exception {

    private final String notificationMessage;

    private final UnicacityAddon unicacityAddon;

    public UnicacityAddonException(String message) {
        super(message);
        this.unicacityAddon = null;
        this.notificationMessage = "";
    }

    public UnicacityAddonException(UnicacityAddon unicacityAddon, String message, String notificationMessage) {
        super(unicacityAddon.utilService().messageWithHiddenToken(message));
        this.unicacityAddon = unicacityAddon;
        this.notificationMessage = unicacityAddon.utilService().messageWithHiddenToken(notificationMessage);
    }

    public void sendNotification() {
        if (this.unicacityAddon != null && !this.notificationMessage.isBlank()) {
            this.unicacityAddon.notificationService().sendUnicacityAddonNotification(
                    this.notificationMessage,
                    NotificationService.SendState.FAILURE
            );
        }
    }
}