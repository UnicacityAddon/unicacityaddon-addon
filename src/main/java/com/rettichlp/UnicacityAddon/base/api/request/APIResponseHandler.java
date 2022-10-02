package com.rettichlp.UnicacityAddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;
import org.apache.http.HttpStatus;

import java.util.Map;

import static com.rettichlp.UnicacityAddon.base.utils.DebugUtils.Debug;

public class APIResponseHandler {

    public static JsonArray getBlacklistResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getFactionPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getBlacklistAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getFactionAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getBlacklistRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getFactionRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getBroadcastQueueResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BROADCAST_QUEUE.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getBroadcastSendResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BROADCAST_SEND.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getHouseBanResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getHouseBanAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getHouseBanRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getHouseBanReasonResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN_REASON.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getHouseBanReasonAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN_REASON.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getHouseBanReasonRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN_REASON.getRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getNaviPointResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.NAVIPOINT.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getNaviPointAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.NAVIPOINT.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getNaviPointRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.NAVIPOINT.getRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getPlayerResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.PLAYER.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getPlayerGroupResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.PLAYER_GROUP.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getPlayerAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.PLAYER.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getPlayerRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.PLAYER.getRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getStatisticResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.STATISTIC.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static void getStatisticAddKillResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        WebsiteUtils.createUrl(BaseUrl.STATISTIC_KILL.getPath(), parameters);
    }

    public static void getStatisticAddDeathResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        WebsiteUtils.createUrl(BaseUrl.STATISTIC_DEATH.getPath(), parameters);
    }

    public static void getStatisticAddReviveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        WebsiteUtils.createUrl(BaseUrl.STATISTIC_REVIVE.getPath(), parameters);
    }

    public static void getStatisticAddServiceResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        WebsiteUtils.createUrl(BaseUrl.STATISTIC_SERVICE.getPath(), parameters);
    }

    public static JsonObject getTokenCreateResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.TOKEN_CREATE.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getTokenRevokeResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.TOKEN_REVOKE.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonArray getWantedReasonResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.WANTED_REASON.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getWantedReasonAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.WANTED_REASON.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getWantedReasonRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.WANTED_REASON.getRemovePath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    private static JsonElement getJsonElement(String urlString) throws APIUnsuccessResponseException {
        Map.Entry<String, Integer> response = WebsiteUtils.websiteToString(urlString);

        Debug(APIResponseHandler.class, urlString.replace(TokenManager.API_TOKEN, "TOKEN") + " - " + response.getValue());

        if (response.getValue() != HttpStatus.SC_OK) {
            throw new APIUnsuccessResponseException(response.getKey(), response.getValue());
        }

        return new JsonParser().parse(response.getKey());
    }
}