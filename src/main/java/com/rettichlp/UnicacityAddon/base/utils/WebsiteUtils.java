package com.rettichlp.UnicacityAddon.base.utils;

import joptsimple.internal.Strings;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;

/**
 * @author RettichLP
 */
public class WebsiteUtils {

    public static String websiteToString(String urlString) {
        String content = null;

        if (urlString != null) {
            try {
                HttpURLConnection httpURLConnection = request(new URL(urlString));
                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) return Strings.EMPTY;
                content = getContent(httpURLConnection);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return content;
    }

    public static URL createUrl(String path, Map<String, String> parameters) throws UnsupportedEncodingException, MalformedURLException {
        String urlString = path + getParamsString(parameters);
        return new URL(urlString);
    }

    public static HttpURLConnection request(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        return httpURLConnection;
    }

    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

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
    }

    public static String getContent(HttpURLConnection httpURLConnection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream()));
        while (scanner.hasNextLine()) stringBuilder.append(scanner.nextLine()).append("\n\r");
        return stringBuilder.toString();
    }
}