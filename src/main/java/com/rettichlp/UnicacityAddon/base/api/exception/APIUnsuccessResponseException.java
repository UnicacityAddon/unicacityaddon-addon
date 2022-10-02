package com.rettichlp.UnicacityAddon.base.api.exception;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import io.netty.handler.codec.http.HttpResponseStatus;

public class APIUnsuccessResponseException extends Throwable {

    private final String response;
    private final int responseCode;

    public APIUnsuccessResponseException(String response, int responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    public void sendInfoMessage() {
        AbstractionLayer.getPlayer().sendAPIMessage("Fehler [" + responseCode + "]: " + getReason(), false);
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