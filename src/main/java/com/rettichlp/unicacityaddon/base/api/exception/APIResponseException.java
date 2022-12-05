package com.rettichlp.unicacityaddon.base.api.exception;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import org.apache.logging.log4j.Level;

public class APIResponseException extends Throwable {

    private final String urlString;
    private final int responseCode;

    public APIResponseException(String urlString, int responseCode) {
        this.urlString = urlString;
        this.responseCode = responseCode;
    }

    public void sendInfoMessage() {
        String message;
        Level level;

        switch (responseCode) {
            case 200:
                message = "Ok";
                level = Level.INFO;
                break;
            case 204:
                message = "Inhalt nicht gefunden.";
                level = Level.WARN;
                break;
            case 400:
                message = "Fehlerhafte Anfrage.";
                level = Level.WARN;
                break;
            case 401:
                message = "Der Zugriff wurde abgelehnt.";
                level = Level.WARN;
                break;
            case 404:
                message = "Endpunkt nicht gefunden.";
                level = Level.WARN;
                break;
            case 423:
                message = "Endpunkt gesperrt.";
                level = Level.WARN;
                break;
            case 500:
                message = "Der Server konnte die Anfrage nicht verarbeiten.";
                level = Level.WARN;
                break;
            case 503:
                message = "Der Server ist aktuell nicht erreichbar.";
                level = Level.WARN;
                break;
            default:
                message = "API Fehler.";
                level = Level.WARN;
        }

        UnicacityAddon.LOGGER.atLevel(level).log("APIResponseException - " + responseCode + " [" + urlString + "]: " + message);
    }
}