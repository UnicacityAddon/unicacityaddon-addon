package com.rettichlp.unicacityaddon.base.teamspeak;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class CommandResponse {

    private final String rawResponse;
    private Map<String, String> response;
    private List<Map<String, String>> responseList;

    public CommandResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public static int parseInt(String str) {
        if (str == null)
            return 0;

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseDouble(String str) {
        if (str == null)
            return 0;

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long parseLong(String str) {
        if (str == null)
            return 0;

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean parseBoolean(String str) {
        return str != null && str.equals("1");
    }

    public boolean failed() {
        String msg = getResponse().get("msg");
        return msg == null || !msg.equals("ok");
    }

    public Map<String, String> getResponse() {
        if (response == null) {
            response = TSParser.parse(rawResponse);
        }

        return response;
    }

    public List<Map<String, String>> getResponseList() {
        if (responseList == null) {
            responseList = TSParser.parseMap(rawResponse);
        }

        return responseList;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommandResponse.class.getSimpleName() + "[", "]")
                .add("rawResponse='" + rawResponse + "'")
                .add("response=" + response)
                .add("responseList=" + responseList)
                .toString();
    }
}
