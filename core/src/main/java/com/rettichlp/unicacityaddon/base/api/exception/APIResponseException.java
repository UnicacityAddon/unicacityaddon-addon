package com.rettichlp.unicacityaddon.base.api.exception;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.response.Failure;
import com.rettichlp.unicacityaddon.base.api.HttpStatus;
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

    private final UnicacityAddon unicacityAddon;

    public APIResponseException(UnicacityAddon unicacityAddon, String urlString, int responseCode) {
        this.unicacityAddon = unicacityAddon;
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = HttpStatus.valueOf(responseCode).getReasonPhrase();
    }

    public APIResponseException(UnicacityAddon unicacityAddon, String urlString, int responseCode, String infoMessage) {
        this.unicacityAddon = unicacityAddon;
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = infoMessage;
    }

    public void sendInfo() {
        this.unicacityAddon.logger().warn("APIResponseException - " + responseCode + " [" + urlString.replace(this.unicacityAddon.api().getToken(), "TOKEN") + "]: " + infoMessage);
        this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                .title(Message.getBuilder().of("API Fehler - " + responseCode).color(ColorCode.RED).bold().advance().createComponent())
                .text(Message.getBuilder().of(infoMessage).advance().createComponent())
                .icon(this.unicacityAddon.services().utilService().icon())
                .type(Notification.Type.ADVANCEMENT)
                .build());
    }

    public Failure failureResponse() {
        return new Failure(responseCode, HttpStatus.valueOf(responseCode).getReasonPhrase(), infoMessage);
    }
}