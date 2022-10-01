package com.rettichlp.UnicacityAddon.base.api.exception;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;
import io.netty.handler.codec.http.HttpResponseStatus;

import static com.rettichlp.UnicacityAddon.base.utils.DebugUtils.Debug;

public class APIUnsuccessResponseException extends Throwable {

    private final String urlString;
    private final String response;
    private final int responseCode;

    public APIUnsuccessResponseException(String urlString, String response, int responseCode) {
        this.urlString = urlString.replace(TokenManager.API_TOKEN, "TOKEN");
        this.response = response;
        this.responseCode = responseCode;
    }

    public void sendInfoMessage() {
        AbstractionLayer.getPlayer().sendAPIMessage("Fehler [" + responseCode + "]: " + getReason(), false);
        Debug(APIUnsuccessResponseException.class, "Fehler [" + responseCode + "]: " + getReason());
        Debug(APIUnsuccessResponseException.class, "Request: " + urlString);
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