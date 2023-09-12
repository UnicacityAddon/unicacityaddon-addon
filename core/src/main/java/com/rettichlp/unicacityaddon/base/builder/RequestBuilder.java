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
import com.rettichlp.unicacityaddon.api.response.Success;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.Laby;
import net.labymod.api.notification.Notification;

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
                String response = this.unicacityAddon.webService().sendApiRequest(this.nonProd, this.applicationPath, this.subPath, this.parameter);
                return new JsonParser().parse(response);
            }
            return JsonNull.INSTANCE;
        }

        public void sendAsync() {
            new Thread(() -> {
                try {
                    JsonElement jsonElement = send();
                    if (jsonElement.isJsonObject()) {
                        Success success = parse(jsonElement.getAsJsonObject(), Success.class);
                        Laby.labyAPI().notificationController().push(Notification.builder()
                                .title(Message.getBuilder()
                                        .of("UnicacityAddon").color(ColorCode.DARK_AQUA).bold().advance().space()
                                        .of("API").color(ColorCode.AQUA).advance()
                                        .createComponent())
                                .text(Message.getBuilder().of(success.getInfo()).advance().createComponent())
                                .icon(this.unicacityAddon.utilService().icon())
                                .type(Notification.Type.ADVANCEMENT)
                                .build());
                    }
                } catch (APIResponseException e) {
                    e.sendNotification();
                    this.unicacityAddon.logger().error(e.getMessage());
                }
            }).start();
        }

        public <T> T getAsJsonObjectAndParse(Class<T> responseSchemaClass) {
            try {
                JsonElement jsonElement = send();
                return parse(jsonElement.getAsJsonObject(), responseSchemaClass);
            } catch (APIResponseException e) {
                this.unicacityAddon.logger().error(e.getMessage());
                e.sendNotification();
            } catch (IllegalStateException e) {
                this.unicacityAddon.logger().info("Precondition(s) failed! Parsing of JSON response skipped for: {}", responseSchemaClass.getSimpleName());
            }
            return null;
        }

        public <T> List<T> getAsJsonArrayAndParse(Class<T> responseSchemaClass) {
            try {
                JsonElement jsonElement = send();
                return parse(jsonElement.getAsJsonArray(), responseSchemaClass);
            } catch (APIResponseException e) {
                this.unicacityAddon.logger().error(e.getMessage());
                e.sendNotification();
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

        private <T> List<T> parse(JsonArray jsonArray, Class<T> responseSchemaClass) throws JsonSyntaxException {
            TypeToken<List<T>> typeToken = new TypeToken<List<T>>() {}.where(new TypeParameter<>() {}, responseSchemaClass);
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, typeToken.getType());
        }
    }
}