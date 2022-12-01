package com.rettichlp.UnicacityAddon.base.utils;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;
import com.rettichlp.UnicacityAddon.base.api.enums.ApplicationPath;
import com.rettichlp.UnicacityAddon.base.api.exception.APIResponseException;
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
        HttpURLConnection httpURLConnection;

        if (urlString == null || urlString.isEmpty())
            throw new APIResponseException("URL is null or empty", HttpURLConnection.HTTP_NOT_FOUND);

        try {
            httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");

            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
                throw new APIResponseException(urlString, statusCode);
        } catch (IOException e) {
            throw new APIResponseException(urlString, HttpURLConnection.HTTP_NOT_FOUND);
        }

        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine()) websiteSource.append(scanner.nextLine()).append("\n\r");
            UnicacityAddon.LOGGER.info("APIResponse - " + httpURLConnection.getResponseCode() + " [" + urlString.replace(TokenManager.API_TOKEN, "TOKEN") + "]");
            return websiteSource.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new APIResponseException(urlString, HttpURLConnection.HTTP_NO_CONTENT);
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
}