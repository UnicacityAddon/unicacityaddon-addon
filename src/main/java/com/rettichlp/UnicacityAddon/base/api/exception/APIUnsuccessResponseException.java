package com.rettichlp.UnicacityAddon.base.api.exception;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIUnsuccessResponseException extends APIException {

    public APIUnsuccessResponseException(HttpURLConnection httpURLConnection, URL url, int responseCode) throws IOException {
        super(httpURLConnection, url, responseCode);
    }
}
