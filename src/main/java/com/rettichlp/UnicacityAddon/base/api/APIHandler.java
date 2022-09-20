package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;

public class APIHandler {

    private static String apiKey = ""; // TODO: 21.09.2022
    private static String baseurl = "http://rettichlp.de:8888/unicacityaddon/v1/" + apiKey;

    public static String getInfo(String apiToken, String s) {
        String responseString = WebsiteUtils.websiteToString(baseurl + "/" + apiToken + s);
        return new JsonParser().parse(responseString).getAsJsonObject().get("info").getAsString();
    }
}