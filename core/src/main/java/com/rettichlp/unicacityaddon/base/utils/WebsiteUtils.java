package com.rettichlp.unicacityaddon.base.utils;

import com.google.common.collect.Maps;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.HttpStatus;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import jdk.internal.joptsimple.internal.Strings;

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

    // TODO: 31.03.2023 remove static method (not only here)

    public static String sendRequest(String urlString, UnicacityAddon unicacityAddon) throws APIResponseException {
        Map.Entry<HttpURLConnection, Integer> httpURLConnection = getHttpURLConnection(urlString, unicacityAddon);

        String websiteSource = getWebsiteSource(httpURLConnection, unicacityAddon);

        int responseCode = httpURLConnection.getValue();

        boolean isApiRequest = urlString.startsWith("http://rettichlp.de:8888/unicacityaddon/v1/");

        if (responseCode == HttpURLConnection.HTTP_OK) {
            unicacityAddon.logger().info("APIResponse - " + responseCode + " [" + urlString.replace(unicacityAddon.tokenManager().getApiToken(), "TOKEN") + "]");
            return websiteSource;
        } else {
            throw new APIResponseException(unicacityAddon, urlString, responseCode, isApiRequest
                    ? new JsonParser().parse(websiteSource).getAsJsonObject().get("info").getAsString()
                    : HttpStatus.valueOf(responseCode).getReasonPhrase());
        }
    }

    public static String createUrl(UnicacityAddon unicacityAddon, boolean nonProd, ApplicationPath applicationPath, String subPath, Map<String, String> parameter) {
        return (nonProd ? "http://localhost:8888/unicacityaddon/v1/" : "http://rettichlp.de:8888/unicacityaddon/v1/")
                + unicacityAddon.tokenManager().getApiToken()
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

    private static Map.Entry<HttpURLConnection, Integer> getHttpURLConnection(String urlString, UnicacityAddon unicacityAddon) throws APIResponseException {
        HttpURLConnection httpURLConnection;

        if (urlString != null && !urlString.isEmpty()) {
            try {
                httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
                httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");
                return Maps.immutableEntry(httpURLConnection, httpURLConnection.getResponseCode());
            } catch (IOException e) {
                throw new APIResponseException(unicacityAddon, urlString, HttpURLConnection.HTTP_NOT_FOUND);
            }
        } else
            throw new APIResponseException(unicacityAddon, "URL is null or empty", HttpURLConnection.HTTP_NOT_FOUND);
    }

    private static String getWebsiteSource(Map.Entry<HttpURLConnection, Integer> httpURLConnectionIntegerEntry, UnicacityAddon unicacityAddon) throws APIResponseException {
        HttpURLConnection httpURLConnection = httpURLConnectionIntegerEntry.getKey();
        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine())
                websiteSource.append(scanner.nextLine()).append("\n\r");
            return websiteSource.toString();
        } catch (IOException e) {
            throw new APIResponseException(unicacityAddon, httpURLConnection.getURL().toString(), httpURLConnectionIntegerEntry.getValue());
        }
    }
}