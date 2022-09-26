package com.rettichlp.UnicacityAddon.base.api.exception;

public class APIException extends Throwable {

    private final String urlString;
    private final String response;

    public APIException(String urlString, String response) {
        this.urlString = urlString;
        this.response = response;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getResponse() {
        return response;
    }
}
