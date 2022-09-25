package com.rettichlp.UnicacityAddon.base.api.exception;

import com.google.gson.JsonParser;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIException extends Throwable {

    private final HttpURLConnection httpURLConnection;
    private final URL url;
    private final int responseCode;
    private final String info;

    public APIException(HttpURLConnection httpURLConnection, URL url, int responseCode) throws IOException {
        this.httpURLConnection = httpURLConnection;
        this.url = url;
        this.responseCode = responseCode;
        this.info = new JsonParser().parse(WebsiteUtils.getContent(httpURLConnection)).getAsJsonObject().get("info").getAsString();
    }

    public void sendIngameInfoMessage() {
        AbstractionLayer.getPlayer().sendAPIMessage("Fehler [" + responseCode + "]: " + info, false);
    }
}
