package com.rettichlp.UnicacityAddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.enums.ApplicationPath;
import com.rettichlp.UnicacityAddon.base.api.enums.StatisticType;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    public static JsonArray sendBlacklistReasonRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(AbstractionLayer.getPlayer().getFaction().name())
                .getAsJsonArray();
    }

    public static JsonObject sendBlacklistReasonAddRequest(String reason, String price, String kills) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);
        parameter.put("price", price);
        parameter.put("kills", kills);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(AbstractionLayer.getPlayer().getFaction() + "/add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendBlacklistReasonRemoveRequest(String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(AbstractionLayer.getPlayer().getFaction() + "/remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonArray sendBroadcastQueueRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath("queue")
                .getAsJsonArray();
    }

    public static JsonObject sendBroadcastSendRequest(String message, String sendTime) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("message", message);
        parameter.put("sendTime", sendTime);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath("send")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonArray sendHouseBanRequest(boolean advanced) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("advanced", String.valueOf(advanced));

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(parameter)
                .getAsJsonArray();
    }

    public static JsonObject sendHouseBanAddRequest(String name, String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanRemoveRequest(String name, String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public static JsonArray sendHouseBanReasonRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .getAsJsonArray();
    }

    public static JsonObject sendHouseBanReasonAddRequest(String reason, String days) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);
        parameter.put("days", String.valueOf(days));

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanReasonRemoveRequest(String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonArray sendNaviPointRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArray();
    }

    public static JsonObject sendNaviPointAddRequest(String name, String x, String y, String z) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("x", x);
        parameter.put("y", y);
        parameter.put("z", z);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendNaviPointRemoveRequest(String name) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerAddRequest(String name, String group) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("group", group);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRemoveRequest(String name, String group) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("group", group);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonArray sendPlayerGroupRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .subPath("groups")
                .getAsJsonArray();
    }

    public static JsonObject sendStatisticRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(AbstractionLayer.getPlayer().getName())
                .getAsJsonObject();
    }

    public static JsonObject sendStatisticRequest(String name) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(name)
                .getAsJsonObject();
    }

    public static void sendStatisticAddRequest(StatisticType statisticType) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("type", statisticType.name());

        RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(AbstractionLayer.getPlayer().getName() + "/add")
                .parameter(parameter)
                .sendAsync();
    }

    public static JsonObject sendStatisticTopRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath("top")
                .getAsJsonObject();
    }

    public static JsonObject sendTokenCreateRequest() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("authToken", UnicacityAddon.MINECRAFT.getSession().getToken());
        parameter.put("version", UnicacityAddon.VERSION);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.TOKEN)
                .subPath("create")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendTokenRevokeRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.TOKEN)
                .subPath("revoke")
                .getAsJsonObject();
    }

    public static JsonArray sendWantedReasonRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.WANTEDREASON)
                .getAsJsonArray();
    }

    public static JsonObject sendWantedReasonAddRequest(String reason, String points) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);
        parameter.put("points", points);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendWantedReasonRemoveRequest(String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonArray sendYasinRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArray();
    }

    public static JsonObject sendYasinAddRequest(String name) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("add")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendYasinRemoveRequest(String name) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("remove")
                .parameter(parameter)
                .getAsJsonObject();
    }

    public static JsonObject sendYasinDoneRequest(String name) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("done")
                .parameter(parameter)
                .getAsJsonObject();
    }
}