package com.rettichlp.unicacityaddon.base.api.exception;

import com.rettichlp.unicacityaddon.UnicacityAddon;

public class APIResponseException extends Throwable {

    private final String urlString;
    private final int responseCode;

    public APIResponseException(String urlString, int responseCode) {
        this.urlString = urlString;
        this.responseCode = responseCode;
    }

    public void sendInfoMessage() {
        String message;

        switch (responseCode) {
            case 200:
                message = "Ok";
                break;
            case 204:
                message = "Inhalt nicht gefunden.";
                break;
            case 400:
                message = "Fehlerhafte Anfrage.";
                break;
            case 401:
                message = "Der Zugriff wurde abgelehnt.";
                break;
            case 404:
                message = "Endpunkt nicht gefunden.";
                break;
            case 423:
                message = "Endpunkt gesperrt.";
                break;
            case 500:
                message = "Der Server konnte die Anfrage nicht verarbeiten.";
                break;
            case 503:
                message = "Der Server ist aktuell nicht erreichbar.";
                break;
            default:
                message = "API Fehler.";
        }

        UnicacityAddon.LOGGER.info("APIResponseException - " + responseCode + " [" + urlString + "]: " + message);
    }
}