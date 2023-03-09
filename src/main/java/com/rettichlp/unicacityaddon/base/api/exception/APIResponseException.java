package com.rettichlp.unicacityaddon.base.api.exception;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.labymod.main.LabyMod;

public class APIResponseException extends Throwable {

    private final String urlString;
    private final int responseCode;
    private final String infoMessage;

    public APIResponseException(String urlString, int responseCode) {
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = HttpResponseStatus.valueOf(responseCode).reasonPhrase();
    }

    public APIResponseException(String urlString, int responseCode, String infoMessage) {
        this.urlString = urlString;
        this.responseCode = responseCode;
        this.infoMessage = infoMessage;
    }

    public void sendInfoMessage() {
        UnicacityAddon.LOGGER.warn("APIResponseException - " + responseCode + " [" + urlString.replace(TokenManager.API_TOKEN, "TOKEN") + "]: " + infoMessage);
        LabyMod.getInstance().notifyMessageRaw(ColorCode.RED.getCode() + "API Fehler - " + responseCode, infoMessage);
    }
}