package com.rettichlp.UnicacityAddon.base.utils;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import joptsimple.internal.Strings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author RettichLP
 */
public class WebsiteUtils {

    public static Map.Entry<String, Integer> websiteToString(String urlString) {
        Scanner scanner;
        StringBuilder websiteSource = new StringBuilder();
        Map.Entry<String, Integer> response;
        int statusCode;

        if (urlString != null) {
            try {
                HttpURLConnection openConnection = (HttpURLConnection) new URL(urlString).openConnection();
                openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                statusCode = openConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    scanner = new Scanner(new InputStreamReader(openConnection.getInputStream(), StandardCharsets.UTF_8));
                    while (scanner.hasNextLine()) websiteSource.append(scanner.nextLine()).append("\n\r");
                }
                response = new AbstractMap.SimpleEntry<>(websiteSource.toString(), statusCode);
            } catch (IOException e) {
                AbstractionLayer.getPlayer().sendAPIMessage("Der Server ist nicht erreichbar!", false);
                return new AbstractMap.SimpleEntry<>(Strings.EMPTY, HttpURLConnection.HTTP_UNAVAILABLE);
            }
        } else {
            response = new AbstractMap.SimpleEntry<>(Strings.EMPTY, HttpURLConnection.HTTP_BAD_REQUEST);
        }
        return response;
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