package com.rettichlp.unicacityaddon.base.api.exception;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.HttpStatus;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.notification.Notification;

/**
 * @author RettichLP
 */
public class APIResponseException extends Throwable {

    private final String urlString;
    private final int responseCode;
    private final String infoMessage;

    public APIResponseException(String urlString, int responseCode) {
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = HttpStatus.valueOf(responseCode).getReasonPhrase();
    }

    public APIResponseException(String urlString, int responseCode, String infoMessage) {
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = infoMessage;
    }

    public void sendInfo() {
        UnicacityAddon.LOGGER.warn("APIResponseException - " + responseCode + " [" + urlString.replace(TokenManager.API_TOKEN, "TOKEN") + "]: " + infoMessage);
        UnicacityAddon.ADDON.labyAPI().notificationController().push(Notification.builder()
                .title(Message.getBuilder().of("API Fehler - " + responseCode).color(ColorCode.RED).bold().advance().createComponent())
                .text(Message.getBuilder().of(infoMessage).advance().createComponent())
                .icon(UnicacityAddon.ICON)
                .type(Notification.Type.ADVANCEMENT)
                .build());
    }
}