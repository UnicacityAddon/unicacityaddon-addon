package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIHandler {

    private static final String apiKey = "bj4tdsgsof83Inf45zsdtsdf4067dohd"; // TODO: 21.09.2022
    private static final String baseurl = "http://rettichlp.de:8888/unicacityaddon/v1/" + ConfigElements.getAPIToken();

    private static final List<String> successResponses = Arrays.asList("Entry added", "Entry removed");

    private JsonObject jsonObject;

    public static Map.Entry<String, Boolean> getInfo(String apiToken, String url) {
        JsonObject response = request(apiToken, url).getAsJsonObject();
        String info = response.get("info").getAsString();
        boolean successResponse = successResponses.contains(info);

        return new AbstractMap.SimpleEntry<>(info, successResponse);
    }

    public static JsonArray getArray(String apiToken, String url) {
        return request(apiToken, url).getAsJsonArray();
    }

    private static JsonElement request(String apiToken, String url) {
        System.out.println(baseurl + "/" + apiToken + url);
        String responseString = WebsiteUtils.websiteToString(baseurl + "/" + apiToken + url);
        return new JsonParser().parse(responseString);


    }

    public static void getBlacklistFactionResponse() {
        String responseString = WebsiteUtils.websiteToString(baseurl + "/blacklistreason/" + AbstractionLayer.getPlayer().getFaction());

        JsonElement jsonElement = new JsonParser().parse(responseString);
        JsonElement jsonElement1 = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : jsonElement.getAsJsonArray();
    }

    public static void getBlacklistFactionAddResponse(String reason, int price, int kills) {
        Map<String, String> queryParameter = new HashMap<>();
        queryParameter.put("reason", reason);
        queryParameter.put("price", String.valueOf(price));
        queryParameter.put("kills", String.valueOf(kills));

        String url = createUrl(baseurl + "/blacklistreason/" + AbstractionLayer.getPlayer().getFaction() + "/add", queryParameter);
        String responseString = WebsiteUtils.websiteToString(url);

        JsonElement jsonElement = new JsonParser().parse(responseString);
        JsonElement jsonElement1 = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : jsonElement.getAsJsonArray();
    }

    private static String createUrl(String baseurl, Map<String, String> queryParameter) {
        StringBuilder sb = new StringBuilder();
        queryParameter.forEach((key, value) -> {
            sb.append("&").append(key).append("=").append(value);
        });
        sb.repl


        for (queryParameter)

    }

    public static void getBlacklistFactionRemoveResponse() {
        String responseString = WebsiteUtils.websiteToString(baseurl + "/blacklistreason/" + AbstractionLayer.getPlayer().getFaction());

        JsonElement jsonElement = new JsonParser().parse(responseString);
        JsonElement jsonElement1 = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : jsonElement.getAsJsonArray();
    }
}