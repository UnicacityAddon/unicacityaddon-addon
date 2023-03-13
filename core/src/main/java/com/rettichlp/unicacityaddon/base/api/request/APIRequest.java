package com.rettichlp.unicacityaddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.RequestBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    private static final boolean NON_PROD = false;
    private static final String ADD_SUB_PATH = "add";
    private static final String REMOVE_SUB_PATH = "remove";
    private static final String QUEUE_SUB_PATH = "queue";
    private static final String SEND_SUB_PATH = "send";
    private static final String TOP_SUB_PATH = "top";
    private static final String CREATE_SUB_PATH = "create";
    private static final String DONE_SUB_PATH = "done";
    private static final String USERS_SUB_PATH = "users";
    private static final String BOMB_SUB_PATH = "bomb";
    private static final String GANGWAR_SUB_PATH = "gangwar";

    public static void sendBannerAddRequest(Faction faction, int x, int y, int z, String navipoint) {
        RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BANNER)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "faction", faction.toString(),
                        "x", String.valueOf(x),
                        "y", String.valueOf(y),
                        "z", String.valueOf(z),
                        "navipoint", navipoint))
                .sendAsync();
    }

    public static JsonArray sendBlacklistReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(UnicacityAddon.PLAYER.getFaction().name())
                .getAsJsonArray();
    }

    public static JsonObject sendBlacklistReasonAddRequest(String reason, String price, String kills) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(UnicacityAddon.PLAYER.getFaction() + "/add")
                .parameter(mapOf(
                        "reason", reason,
                        "price", price,
                        "kills", kills))
                .getAsJsonObject();
    }

    public static JsonObject sendBlacklistReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(UnicacityAddon.PLAYER.getFaction() + "/remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonArray sendBroadcastQueueRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(QUEUE_SUB_PATH)
                .getAsJsonArray();
    }

    public static JsonObject sendBroadcastSendRequest(String message, String sendTime) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(SEND_SUB_PATH)
                .parameter(mapOf(
                        "message", message,
                        "sendTime", sendTime))
                .getAsJsonObject();
    }

    public static void sendEventBombRequest(long startTime) {
        RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.EVENT)
                .subPath(BOMB_SUB_PATH)
                .parameter(mapOf(
                        "startTime", String.valueOf(startTime)))
                .sendAsync();
    }

    public static void sendEventGangwarRequest(int attacker, int defender) {
        RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.EVENT)
                .subPath(GANGWAR_SUB_PATH)
                .parameter(mapOf(
                        "attacker", String.valueOf(attacker),
                        "defender", String.valueOf(defender)))
                .sendAsync();
    }

    public static JsonArray sendHouseBanRequest(boolean advanced) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(mapOf("advanced", String.valueOf(advanced)))
                .getAsJsonArray();
    }

    public static JsonObject sendHouseBanAddRequest(String name, String reason) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanRemoveRequest(String name, String reason) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObject();
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public static JsonArray sendHouseBanReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .getAsJsonArray();
    }

    public static JsonObject sendHouseBanReasonAddRequest(String reason, String days) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "days", days))
                .getAsJsonObject();
    }

    public static JsonObject sendHouseBanReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonObject sendManagementRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .getAsJsonObject();
    }

    public static JsonArray sendManagementUserRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .subPath(USERS_SUB_PATH)
                .getAsJsonArray();
    }

    public static JsonArray sendNaviPointRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArray();
    }

    public static JsonObject sendNaviPointAddRequest(String name, String x, String y, String z, String article) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "x", x,
                        "y", y,
                        "z", z,
                        "article", article))
                .getAsJsonObject();
    }

    public static JsonObject sendNaviPointRemoveRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerAddRequest(String name, String group) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObject();
    }

    public static JsonObject sendPlayerRemoveRequest(String name, String group) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObject();
    }

    public static JsonArray sendReviveRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .getAsJsonArray();
    }

    public static JsonArray sendReviveRankRequest(int rank) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(String.valueOf(rank))
                .getAsJsonArray();
    }

    public static JsonObject sendRevivePlayerRequest(String minecraftName) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(minecraftName)
                .getAsJsonObject();
    }

    public static JsonObject sendStatisticRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(UnicacityAddon.PLAYER.getName())
                .getAsJsonObject();
    }

    public static void sendStatisticAddRequest(StatisticType statisticType) {
        if (UnicacityAddon.isUnicacity()) {
            RequestBuilder.getBuilder()
                    .nonProd(NON_PROD)
                    .applicationPath(ApplicationPath.STATISTIC)
                    .subPath(UnicacityAddon.PLAYER.getName() + "/add")
                    .parameter(mapOf("type", statisticType.name()))
                    .sendAsync();
        }
    }

    public static JsonObject sendStatisticTopRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(TOP_SUB_PATH)
                .getAsJsonObject();
    }

    public static JsonObject sendTokenCreateRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.TOKEN)
                .subPath(CREATE_SUB_PATH)
                .parameter(mapOf(
                        "authToken", UnicacityAddon.ADDON.labyAPI().minecraft().sessionAccessor().session().getAccessToken(),
                        "version", UnicacityAddon.VERSION))
                .getAsJsonObject();
    }

    public static JsonArray sendWantedReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .getAsJsonArray();
    }

    public static JsonObject sendWantedReasonAddRequest(String reason, String points) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "points", points))
                .getAsJsonObject();
    }

    public static JsonObject sendWantedReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public static JsonArray sendYasinRequest() throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArray();
    }

    public static JsonObject sendYasinAddRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendYasinRemoveRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static JsonObject sendYasinDoneRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder()
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(DONE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }
}