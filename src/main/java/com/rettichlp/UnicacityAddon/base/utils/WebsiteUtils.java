package com.rettichlp.UnicacityAddon.base.utils;

import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;
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

    public static String sendRequest(String urlString) throws APIUnsuccessResponseException {
        HttpURLConnection httpURLConnection;

        if (urlString == null || urlString.isEmpty())
            throw new APIUnsuccessResponseException(Strings.EMPTY, HttpURLConnection.HTTP_NOT_FOUND);

        try {
            httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");

            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK)
                throw new APIUnsuccessResponseException(urlString, statusCode);
        } catch (IOException e) {
            throw new APIUnsuccessResponseException(urlString, HttpURLConnection.HTTP_NOT_FOUND);
        }

        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine()) websiteSource.append(scanner.nextLine()).append("\n\r");
            return websiteSource.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new APIUnsuccessResponseException(urlString, HttpURLConnection.HTTP_NO_CONTENT);
    }

    public static String createUrl(String path, Map<String, String> parameters) {
        return path + getParamsString(parameters);
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