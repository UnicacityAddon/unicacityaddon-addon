package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.response.Failure;

/**
 * @author RettichLP
 */
public class APIResponseException extends UnicacityAddonException {

    public APIResponseException(String message) {
        super(message);
    }

    public APIResponseException(UnicacityAddon unicacityAddon, String urlString, Failure failure) {
        super(unicacityAddon, "APIResponseException - " + failure.getHttpStatusCode() + " [" + urlString + "]: " + failure.getInfo(), "API Fehler " + failure.getHttpStatusCode() + " - " + failure.getInfo());
    }
}