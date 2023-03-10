package com.rettichlp.unicacityaddon.base.utils;

import com.google.common.collect.Maps;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import io.netty.handler.codec.http.HttpResponseStatus;
import joptsimple.internal.Strings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

/**
 * @author RettichLP
 */
public class WebsiteUtils {

    public static String sendRequest(String urlString) throws APIResponseException {
        Map.Entry<HttpURLConnection, Integer> httpURLConnection = getHttpURLConnection(urlString);

        String websiteSource = getWebsiteSource(httpURLConnection.getKey());

        int responseCode = httpURLConnection.getValue();

        boolean isApiRequest = urlString.startsWith("http://rettichlp.de:8888/unicacityaddon/v1/") && FileManager.isValidJson(websiteSource);

        if (responseCode != HttpURLConnection.HTTP_OK)
            throw new APIResponseException(urlString, responseCode, isApiRequest
                    ? new JsonParser().parse(websiteSource).getAsJsonObject().get("info").getAsString()
                    : HttpResponseStatus.valueOf(responseCode).reasonPhrase());

        UnicacityAddon.LOGGER.info("APIResponse - " + responseCode + " [" + urlString.replace(TokenManager.API_TOKEN, "TOKEN") + "]");
        return websiteSource;
    }

    public static String createUrl(boolean nonProd, ApplicationPath applicationPath, String subPath, Map<String, String> parameter) {
        return (nonProd ? "http://localhost:8888/unicacityaddon/v1/" : "http://rettichlp.de:8888/unicacityaddon/v1/")
                + TokenManager.API_TOKEN
                + applicationPath.getApplicationPath()
                + (subPath == null ? Strings.EMPTY : "/" + subPath)
                + (parameter == null || parameter.isEmpty() ? Strings.EMPTY : getParamsString(parameter));
    }

    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }
            String resultString = result.toString();
            return resultString.length() > 0
                    ? "?" + resultString.substring(0, resultString.length() - 1)
                    : resultString;
        } catch (UnsupportedEncodingException e) {
            return Strings.EMPTY;
        }
    }

    private static Map.Entry<HttpURLConnection, Integer> getHttpURLConnection(String urlString) throws APIResponseException {
        HttpURLConnection httpURLConnection;

        if (urlString != null && !urlString.isEmpty()) {
            try {
                httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
                httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");
                return Maps.immutableEntry(httpURLConnection, httpURLConnection.getResponseCode());
            } catch (IOException e) {
                throw new APIResponseException(urlString, HttpURLConnection.HTTP_NOT_FOUND);
            }
        } else
            throw new APIResponseException("URL is null or empty", HttpURLConnection.HTTP_NOT_FOUND);
    }

    private static String getWebsiteSource(HttpURLConnection httpURLConnection) throws APIResponseException {
        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine())
                websiteSource.append(scanner.nextLine()).append("\n\r");
            return websiteSource.toString();
        } catch (IOException e) {
            throw new APIResponseException(httpURLConnection.getURL().toString(), HttpURLConnection.HTTP_NOT_FOUND);
        }
    }
}