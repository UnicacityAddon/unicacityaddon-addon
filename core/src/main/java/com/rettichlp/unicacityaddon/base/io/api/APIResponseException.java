package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.response.Failure;

/**
 * @author RettichLP
 */
public class APIResponseException extends UnicacityAddonException {

    private final int responseCode;
    private final String infoMessage;

    public APIResponseException(UnicacityAddon unicacityAddon, String urlString, int responseCode) {
        super(unicacityAddon, "APIResponseException - " + responseCode + " [" + urlString + "]: " + HttpStatus.valueOf(responseCode).getReasonPhrase(), "API Fehler - " + responseCode);
        this.responseCode = responseCode;
        this.infoMessage = HttpStatus.valueOf(responseCode).getReasonPhrase();
    }

    public APIResponseException(UnicacityAddon unicacityAddon, String urlString, int responseCode, String infoMessage) {
        super(unicacityAddon, "APIResponseException - " + responseCode + " [" + urlString + "]: " + infoMessage, "API Fehler - " + responseCode);
        this.responseCode = responseCode;
        this.infoMessage = infoMessage;
    }

    public Failure failureResponse() {
        return new Failure(this.responseCode, HttpStatus.valueOf(this.responseCode).getReasonPhrase(), this.infoMessage);
    }
}