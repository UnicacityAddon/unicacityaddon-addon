package com.rettichlp.UnicacityAddon.base.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    public static JsonArray sendBlacklistRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getBlacklistResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendBlacklistAddRequest(String reason, String price, String kills) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("price", price);
        parameters.put("kills", kills);

        try {
            return APIResponseHandler.getBlacklistAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendBlacklistRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getBlacklistRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonArray sendHouseBanRequest(boolean advanced) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advanced", String.valueOf(advanced));

        try {
            return APIResponseHandler.getHouseBanResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendHouseBanAddRequest(String name, String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendHouseBanRemoveRequest(String name, String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public static JsonArray sendHouseBanReasonRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getHouseBanReasonResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendHouseBanReasonAddRequest(String reason, String days) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("days", String.valueOf(days));

        try {
            return APIResponseHandler.getHouseBanReasonAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendHouseBanReasonRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanReasonRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonArray sendNaviPointRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getNaviPointResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendNaviPointAddRequest(String name, String x, String y, String z) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("x", x);
        parameters.put("y", y);
        parameters.put("z", z);

        try {
            return APIResponseHandler.getNaviPointAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendNaviPointRemoveRequest(String name) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);

        try {
            return APIResponseHandler.getNaviPointRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonArray sendWantedReasonRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getWantedReasonResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendWantedReasonAddRequest(String reason, String points) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("points", points);

        try {
            return APIResponseHandler.getWantedReasonAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendWantedReasonRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getWantedReasonRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendTokenCreateRequest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("authToken", UnicacityAddon.MINECRAFT.getSession().getToken());

        try {
            return APIResponseHandler.getTokenCreateResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }

    public static JsonObject sendTokenRevokeRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getTokenRevokeResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendIngameInfoMessage();
            return null;
        }
    }
}