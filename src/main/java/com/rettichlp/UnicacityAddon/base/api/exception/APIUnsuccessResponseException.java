package com.rettichlp.UnicacityAddon.base.api.exception;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import io.netty.handler.codec.http.HttpResponseStatus;

public class APIUnsuccessResponseException extends Throwable {

    private final String urlString;
    private final String response;
    private final int responseCode;

    public APIUnsuccessResponseException(String urlString, String response, int responseCode) {
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
    }

    private String getReason() {
        if (response != null) {

            System.out.println("RE: " + response);

            JsonElement jsonElement = new JsonParser().parse(response);

            boolean isJsonObject = jsonElement.isJsonObject();

            System.out.println("B: " + isJsonObject);

            if (jsonElement.getAsJsonObject().has("info")) {
                System.out.println("cond true - " + jsonElement.getAsJsonObject().get("info").getAsString());
                return jsonElement.getAsJsonObject().get("info").getAsString();
            }
        }
        return HttpResponseStatus.valueOf(responseCode).reasonPhrase();
    }
}