package com.rettichlp.UnicacityAddon.base.api.exception;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import io.netty.handler.codec.http.HttpResponseStatus;

public class APIUnsuccessResponseException extends APIException {

    private final String urlString;
    private final String response;
    private final int responseCode;

    public APIUnsuccessResponseException(String urlString, String response, int responseCode) {
        super(urlString, response);
        this.urlString = urlString;
        this.response = response;
        this.responseCode = responseCode;
    }

    public String getUrlString() {
        return urlString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void sendIngameInfoMessage() {
        AbstractionLayer.getPlayer().sendAPIMessage("Fehler [" + responseCode + "]: " + getReason(), false);
        System.out.println("DEBUG: " + responseCode + " - " + urlString); // TODO: 26.09.2022  
    }

    private String getReason() {
        if (response != null) {
            JsonElement jsonElement = new JsonParser().parse(response);
            if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("info")) {
                return jsonElement.getAsJsonObject().get("info").getAsString();
            }
        }
        return HttpResponseStatus.valueOf(responseCode).reasonPhrase();
    }
}