package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;
import org.apache.http.HttpStatus;

import java.util.Map;

public class APIResponseHandler {

    public static JsonArray getBlacklistResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getPath(), parameters);
        return getJsonElement(urlString).getAsJsonArray();
    }

    public static JsonObject getBlacklistAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getAddPath(), parameters);
        return getJsonElement(urlString).getAsJsonObject();
    }

    public static JsonObject getBlacklistRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException {
        String urlString = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getRemovePath(), parameters);
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

        System.out.println(urlString.replace(ConfigElements.getAPIToken(), "TOKEN") + " - " + response.getValue()); // TODO: 26.09.2022 DEBUG

        if (response.getValue() != HttpStatus.SC_OK)
            throw new APIUnsuccessResponseException(urlString, response.getKey(), response.getValue());

        return new JsonParser().parse(response.getKey());
    }
}