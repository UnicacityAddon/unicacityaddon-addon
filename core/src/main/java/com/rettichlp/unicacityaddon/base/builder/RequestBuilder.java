package com.rettichlp.unicacityaddon.base.builder;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        private final Set<Boolean> preConditionList = new HashSet<>();
        private boolean nonProd;
        private ApplicationPath applicationPath;
        private String subPath;
        private Map<String, String> parameter;

        private final UnicacityAddon unicacityAddon;

        public Builder(UnicacityAddon unicacityAddon) {
            this.unicacityAddon = unicacityAddon;
        }

        /**
         * IMPORTANT: Preconditions can cause {@link NullPointerException}s in single object responses.
         * Use only on void or multi object responses (e.g. if the response type is a {@link  Collections}).
         */
        public Builder preCondition(boolean preCondition) {
            this.preConditionList.add(preCondition || this.unicacityAddon.player().isSuperUser());
            return this;
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
            this.preConditionList.removeIf(preCondition -> preCondition);
            if (this.preConditionList.isEmpty()) {
                String urlString = this.unicacityAddon.services().webService().createUrl(this.nonProd, this.applicationPath, this.subPath, this.parameter);
                String response = this.unicacityAddon.services().webService().sendRequest(urlString);
                return new JsonParser().parse(response);
            }
            return JsonNull.INSTANCE;
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

        @SuppressWarnings("unchecked")
        public <T> T getAsJsonObjectAndParse(Class<T> responseSchemaClass) {
            try {
                JsonElement jsonElement = send();
                return parse(jsonElement.getAsJsonObject(), responseSchemaClass);
            } catch (APIResponseException e) {
                e.sendInfo();
                return (T) e.failureResponse();
            } catch (IllegalStateException e) {
                this.unicacityAddon.logger().info("Precondition(s) failed! Parsing of JSON response skipped for: {}", responseSchemaClass.getSimpleName());
                return null;
            }
        }

        public <T> List<T> getAsJsonArrayAndParse(Class<T> responseSchemaClass) {
            try {
                JsonElement jsonElement = send();
                return parse(jsonElement.getAsJsonArray(), responseSchemaClass);
            } catch (APIResponseException e) {
                e.sendInfo();
            } catch (IllegalStateException e) {
                this.unicacityAddon.logger().info("Precondition(s) failed! Parsing of JSON response skipped for: {}", responseSchemaClass.getSimpleName());
            }
            return Collections.emptyList();
        }

        private <T> T parse(JsonObject jsonObject, Class<T> responseSchemaClass) throws JsonSyntaxException {
            TypeToken<T> typeToken = TypeToken.of(responseSchemaClass);
            Gson gson = new Gson();
            return gson.fromJson(jsonObject, typeToken.getType());
        }

        public <T> List<T> parse(JsonArray jsonArray, Class<T> responseSchemaClass) throws JsonSyntaxException {
            TypeToken<List<T>> typeToken = new TypeToken<List<T>>() {}.where(new TypeParameter<>() {}, responseSchemaClass);
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, typeToken.getType());
        }
    }
}