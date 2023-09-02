package com.rettichlp.unicacityaddon.base.services;

import com.google.gson.Gson;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.response.Failure;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import org.jetbrains.annotations.NotNull;

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
        HttpURLConnection httpURLConnection = getHttpURLConnection(urlString);
        return getHttpURLConnectionBody(httpURLConnection);
    }

    public String sendApiRequest(boolean nonProd, ApplicationPath applicationPath, String subPath, Map<String, String> parameter) throws APIResponseException {
        String urlString = createUrl(nonProd, applicationPath, subPath, parameter);
        HttpURLConnection httpURLConnection = getHttpURLConnection(urlString);

        String body = getHttpURLConnectionBody(httpURLConnection);

        try {
            int responseCode = httpURLConnection.getResponseCode();
            // this.unicacityAddon.utilService().debug("[API] [" + responseCode + "] " + this.unicacityAddon.utilService().messageWithHiddenToken(urlString));

            if (responseCode >= 200 && responseCode < 300) {
                // return body
                return body;
            } else {
                // parse failure entity and throw APIResponseException
                Gson gson = new Gson();
                Failure failure = gson.fromJson(body, Failure.class);
                throw new APIResponseException(this.unicacityAddon, urlString, failure);
            }
        } catch (IOException e) {
            throw new APIResponseException("Failed to get response code from HTTP Connection with URL " + urlString);
        }
    }

    /**
     * Creates an url as {@link String}
     *
     * @param nonProd         If <code>true</code> the url is created on production environment <a href="http://rettichlp.de:8888/unicacityaddon/v1/">http://rettichlp.de:8888/unicacityaddon/v1/</a> otherwise <a href="http://localhost:8888/unicacityaddon/v1/">http://localhost:8888/unicacityaddon/v1/</a>
     * @param applicationPath The application type which is requested
     * @param subPath         The ongoing path parameters for match application requirements
     * @param parameter       Parameters as {@link Map}, will be mapped as query parameters
     * @return Url as {@link String}
     * @see ApplicationPath
     */
    @NotNull
    private String createUrl(boolean nonProd, ApplicationPath applicationPath, String subPath, Map<String, String> parameter) {
        return (nonProd ? "http://localhost:8888/unicacityaddon/v1/" : "http://rettichlp.de:8888/unicacityaddon/v1/")
                + this.unicacityAddon.api().getToken()
                + applicationPath.getApplicationPath()
                + (subPath == null ? "" : "/" + subPath)
                + (parameter == null || parameter.isEmpty() ? "" : getParamsString(parameter));
    }

    /**
     * Creates a http query parameter string for an url
     *
     * @param params {@link Map} of key and values of the query parameters
     * @return Http query parameter string in format <pre>?key1=value1&key2=value2&...</pre>
     */
    @NotNull
    private String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }
        String resultString = result.toString();
        return !resultString.isEmpty()
                ? "?" + resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * Opens a {@link HttpURLConnection}
     *
     * @param urlString Url as {@link String} from which the {@link HttpURLConnection} will be created
     * @return {@link HttpURLConnection} from the given url
     * @throws APIResponseException if creating connection failed or http response code is not between 200 and 300
     */
    @NotNull
    private HttpURLConnection getHttpURLConnection(@NotNull String urlString) throws APIResponseException {
        HttpURLConnection httpURLConnection;

        try {
            httpURLConnection = (HttpURLConnection) new URL(urlString).openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 Gecko/20100101 Firefox/25.0");
            return httpURLConnection;
        } catch (IOException e) {
            throw new APIResponseException("Failed to create HTTP Connection with URL " + urlString);
        }
    }

    /**
     * Reads the body from an {@link HttpURLConnection} and parse it as {@link String}
     *
     * @param httpURLConnection {@link HttpURLConnection} of the connection from which the body should be retrieved
     * @return Body of {@link HttpURLConnection} as {@link String}
     * @throws APIResponseException if reading data from http connection failed
     */
    @NotNull
    private String getHttpURLConnectionBody(@NotNull HttpURLConnection httpURLConnection) throws APIResponseException {
        try {
            StringBuilder websiteSource = new StringBuilder();
            Scanner scanner = new Scanner(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            while (scanner.hasNextLine()) {
                websiteSource.append(scanner.nextLine()).append("\n\r");
            }
            return websiteSource.toString();
        } catch (IOException e) {
            throw new APIResponseException("Failed to read data from HTTP Connection with URL " + httpURLConnection.getURL().toString());
        }
    }
}