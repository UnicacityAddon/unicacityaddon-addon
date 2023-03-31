package com.rettichlp.unicacityaddon.base.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.utils.WebsiteUtils;

import java.util.Map;

/**
 * @author RettichLP
 */
public class RequestBuilder {

    private RequestBuilder() {
    }

    public static Builder getBuilder(UnicacityAddon unicacityAddon) {
        return new Builder(unicacityAddon);
    }

    public static class Builder {

        private boolean nonProd;
        private ApplicationPath applicationPath;
        private String subPath;
        private Map<String, String> parameter;

        private UnicacityAddon unicacityAddon;

        public Builder(UnicacityAddon unicacityAddon) {
            this.unicacityAddon = unicacityAddon;
        }

        public Builder nonProd(boolean nonProd) {
            this.nonProd = nonProd;
            return this;
        }

        public Builder applicationPath(ApplicationPath applicationPath) {
            this.applicationPath = applicationPath;
            return this;
        }

        public Builder subPath(String subPath) {
            this.subPath = subPath;
            return this;
        }

        public Builder parameter(Map<String, String> parameter) {
            this.parameter = parameter;
            return this;
        }

        public JsonElement send() throws APIResponseException {
            String urlString = WebsiteUtils.createUrl(this.nonProd, this.applicationPath, this.subPath, this.parameter);
            String response = WebsiteUtils.sendRequest(urlString, this.unicacityAddon);
            return new JsonParser().parse(response);
        }

        public void sendAsync() {
            new Thread(() -> {
                try {
                    send();
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            }).start();
        }

        public JsonObject getAsJsonObject() throws APIResponseException {
            JsonElement jsonElement = send();
            return jsonElement.getAsJsonObject();
        }

        public JsonArray getAsJsonArray() throws APIResponseException {
            JsonElement jsonElement = send();
            return jsonElement.getAsJsonArray();
        }
    }
}