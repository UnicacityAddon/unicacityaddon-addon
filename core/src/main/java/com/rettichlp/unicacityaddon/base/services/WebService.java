package com.rettichlp.unicacityaddon.base.services;

import com.google.common.collect.Maps;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import com.rettichlp.unicacityaddon.base.io.api.HttpStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

/**
 * @author RettichLP
 */
public class WebService {

    private final UnicacityAddon unicacityAddon;

    public WebService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public String sendRequest(String urlString) throws APIResponseException {
        Map.Entry<HttpURLConnection, Integer> httpURLConnection = getHttpURLConnection(urlString);

        String websiteSource = getWebsiteSource(httpURLConnection);

        int responseCode = httpURLConnection.getValue();

        boolean isApiRequest = urlString.startsWith("http://rettichlp.de:8888/unicacityaddon/v1/");

        if (responseCode == HttpURLConnection.HTTP_OK) {
            this.unicacityAddon.logger().info("APIResponse - " + responseCode + " [" + urlString.replace(this.unicacityAddon.api().getToken(), "TOKEN") + "]");
            return websiteSource;
        } else {
            throw new APIResponseException(this.unicacityAddon, urlString, responseCode, isApiRequest
                    ? new JsonParser().parse(websiteSource).getAsJsonObject().get("info").getAsString()
                    : HttpStatus.valueOf(responseCode).getReasonPhrase());
        }
    }

    public String createUrl(boolean nonProd, ApplicationPath applicationPath, String subPath, Map<String, String> parameter) {
        return (nonProd ? "http://localhost:8888/unicacityaddon/v1/" : "http://rettichlp.de:8888/unicacityaddon/v1/")
                + this.unicacityAddon.api().getToken()
                + applicationPath.getApplicationPath()
                + (subPath == null ? "" : "/" + subPath)
                + (parameter == null || parameter.isEmpty() ? "" : getParamsString(parameter));
    }

    public String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? "?" + resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    private Map.Entry<HttpURLConnection, Integer> getHttpURLConnection(String urlString) throws APIResponseException {
        HttpURLConnection httpURLConnection;

        if (urlString != null && !urlString.isEmpty()) {
            try {
                httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
                httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");
                return Maps.immutableEntry(httpURLConnection, httpURLConnection.getResponseCode());
            } catch (IOException e) {
                throw new APIResponseException(this.unicacityAddon, urlString, HttpURLConnection.HTTP_NOT_FOUND);
            }
        } else
            throw new APIResponseException(this.unicacityAddon, "URL is null or empty", HttpURLConnection.HTTP_NOT_FOUND);
    }

    private String getWebsiteSource(Map.Entry<HttpURLConnection, Integer> httpURLConnectionIntegerEntry) throws APIResponseException {
        HttpURLConnection httpURLConnection = httpURLConnectionIntegerEntry.getKey();
        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine())
                websiteSource.append(scanner.nextLine()).append("\n\r");
            return websiteSource.toString();
        } catch (IOException e) {
            throw new APIResponseException(this.unicacityAddon, httpURLConnection.getURL().toString(), httpURLConnectionIntegerEntry.getValue());
        }
    }
}