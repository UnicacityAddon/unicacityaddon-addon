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
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(AbstractionLayer.getPlayer().getFaction() + "/add")
                .parameter(mapOf(
                        "reason", reason,
                        "price", price,
                        "kills", kills))
                .getAsJsonObject();
    }

    public static JsonObject sendBlacklistReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(AbstractionLayer.getPlayer().getFaction() + "/remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonArray sendBroadcastQueueRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath("queue")
                .getAsJsonArray();
    }

    public static JsonObject sendBroadcastSendRequest(String message, String sendTime) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath("send")
                .parameter(mapOf(
                        "message", message,
                        "sendTime", sendTime))
                .getAsJsonObject();
    }

    public static JsonArray sendHouseBanRequest(boolean advanced) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(mapOf("advanced", String.valueOf(advanced)))
                .getAsJsonArray();
    }

    public static JsonObject sendHouseBanAddRequest(String name, String reason) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath("add")
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanRemoveRequest(String name, String reason) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("reason", reason);

        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath("remove")
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
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
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath("add")
                .parameter(mapOf(
                        "reason", reason,
                        "days", days))
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath("remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonArray sendNaviPointRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArray();
    }

    public static JsonObject sendNaviPointAddRequest(String name, String x, String y, String z) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath("add")
                .parameter(mapOf(
                        "name", name,
                        "x", x,
                        "y", y,
                        "z", z))
                .getAsJsonObject();
    }

    public static JsonObject sendNaviPointRemoveRequest(String name) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath("remove")
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerAddRequest(String name, String group) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .subPath("add")
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRemoveRequest(String name, String group) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.PLAYER)
                .subPath("remove")
                .parameter(mapOf(
                        "name", name,
                        "group", group))
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
        RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(AbstractionLayer.getPlayer().getName() + "/add")
                .parameter(mapOf("type", statisticType.name()))
                .sendAsync();
    }

    public static JsonObject sendStatisticTopRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath("top")
                .getAsJsonObject();
    }

    public static JsonObject sendTokenCreateRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.TOKEN)
                .subPath("create")
                .parameter(mapOf(
                        "authToken", UnicacityAddon.MINECRAFT.getSession().getToken(),
                        "version", UnicacityAddon.VERSION))
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
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath("add")
                .parameter(mapOf(
                        "reason", reason,
                        "points", points))
                .getAsJsonObject();
    }

    public static JsonObject sendWantedReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath("remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonArray sendYasinRequest() {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArray();
    }

    public static JsonObject sendYasinAddRequest(String name) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("add")
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendYasinRemoveRequest(String name) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("remove")
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendYasinDoneRequest(String name) {
        return RequestBuilder.getBuilder()
                .applicationPath(ApplicationPath.YASIN)
                .subPath("done")
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    private static Map<String, String> mapOf(String k1, String v1) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }

    private static Map<String, String> mapOf(String k1, String v1, String k2, String v2) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    private static Map<String, String> mapOf(String k1, String v1, String k2, String v2, String k3, String v3) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    private static Map<String, String> mapOf(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4) {
        Map<String, String> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }
}