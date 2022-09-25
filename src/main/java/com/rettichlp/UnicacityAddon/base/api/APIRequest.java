package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    public static JsonArray sendHouseBanRequest(boolean advanced) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advanced", String.valueOf(advanced));

        try {
            return APIResponseHandler.getHouseBanResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static JsonObject sendHouseBanAddRequest(String name, String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static JsonObject sendHouseBanRemoveRequest(String name, String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /*public static JsonObject sendBlacklistFactionAddRequest(String reason, int price, int kills) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("price", String.valueOf(price));
        parameters.put("kills", String.valueOf(kills));

        try {
            return APIResponseHandler.getBlacklistFactionAddResponse(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
        }

        return null;
    }

    public static JsonObject sendBlacklistFactionAddRequest(String reason, int price, int kills) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("price", String.valueOf(price));
        parameters.put("kills", String.valueOf(kills));

        try {
            return APIResponseHandler.getBlacklistFaction(parameters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
        }

        return null;
    }*/
}