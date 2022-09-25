package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils.getContent;

public class APIResponseHandler {

    public static JsonArray getBlacklistFactionResponse() throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getPath(), new HashMap<>());
        return getJsonArray(url);
    }

    public static JsonObject getBlacklistFactionAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getAddPath(), parameters);
        return getJsonObject(url);
    }

    public JsonObject getBlacklistFactionRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.BLACKLISTREASON.getRemovePath(), parameters);
        return getJsonObject(url);
    }

    public static JsonArray getHouseBanResponse(Map<String, String> parameters) throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getPath(), parameters);
        return getJsonArray(url);
    }

    public static JsonObject getHouseBanAddResponse(Map<String, String> parameters) throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getAddPath(), parameters);
        return getJsonObject(url);
    }

    public static JsonObject getHouseBanRemoveResponse(Map<String, String> parameters) throws APIUnsuccessResponseException, IOException {
        URL url = WebsiteUtils.createUrl(BaseUrl.HOUSEBAN.getRemovePath(), parameters);
        return getJsonObject(url);
    }

    private static JsonObject getJsonObject(URL url) throws APIUnsuccessResponseException, IOException {
        HttpURLConnection httpURLConnection = WebsiteUtils.request(url);
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode != HttpStatus.SC_OK)
            throw new APIUnsuccessResponseException(httpURLConnection, url, responseCode);

        JsonElement jsonParser = new JsonParser().parse(getContent(httpURLConnection));
        return jsonParser.getAsJsonObject();
    }

    private static JsonArray getJsonArray(URL url) throws APIUnsuccessResponseException, IOException {
        HttpURLConnection httpURLConnection = WebsiteUtils.request(url);
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode != HttpStatus.SC_OK)
            throw new APIUnsuccessResponseException(httpURLConnection, url, responseCode);

        JsonElement jsonParser = new JsonParser().parse(getContent(httpURLConnection));
        return jsonParser.getAsJsonArray();
    }
}