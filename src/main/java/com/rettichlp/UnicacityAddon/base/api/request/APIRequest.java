package com.rettichlp.UnicacityAddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.exception.APIUnsuccessResponseException;

import java.util.HashMap;
import java.util.Map;

public class APIRequest {

    public static JsonArray sendBlacklistReasonRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getBlacklistResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendBlacklistReasonAddRequest(String reason, String price, String kills) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);
        parameters.put("price", price);
        parameters.put("kills", kills);

        try {
            return APIResponseHandler.getBlacklistAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendBlacklistReasonRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getBlacklistRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendBroadcastQueueRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getBroadcastQueueResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            return null;
        }
    }

    public static JsonObject sendBroadcastSendRequest(String message) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("message", message);

        try {
            return APIResponseHandler.getBroadcastSendResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendHouseBanRequest(boolean advanced) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("advanced", String.valueOf(advanced));

        try {
            return APIResponseHandler.getHouseBanResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
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
            e.sendInfoMessage();
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
            e.sendInfoMessage();
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
            e.sendInfoMessage();
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
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendHouseBanReasonRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getHouseBanReasonRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendNaviPointRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getNaviPointResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
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
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendNaviPointRemoveRequest(String name) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);

        try {
            return APIResponseHandler.getNaviPointRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendPlayerRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getPlayerResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendPlayerGroupRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getPlayerGroupResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendPlayerAddRequest(String name, String group) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("group", group);

        try {
            return APIResponseHandler.getPlayerAddResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendPlayerRemoveRequest(String name, String group) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("group", group);

        try {
            return APIResponseHandler.getPlayerRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendStatisticRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getStatisticResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static void sendStatisticAddKillRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            APIResponseHandler.getStatisticAddKillResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
        }
    }

    public static void sendStatisticAddDeathRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            APIResponseHandler.getStatisticAddDeathResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
        }
    }

    public static void sendStatisticAddReviveRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            APIResponseHandler.getStatisticAddReviveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
        }
    }

    public static void sendStatisticAddServiceRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            APIResponseHandler.getStatisticAddServiceResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
        }
    }

    public static JsonObject sendTokenCreateRequest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("authToken", UnicacityAddon.MINECRAFT.getSession().getToken());

        try {
            return APIResponseHandler.getTokenCreateResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendTokenRevokeRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getTokenRevokeResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonArray sendWantedReasonRequest() {
        Map<String, String> parameters = new HashMap<>();

        try {
            return APIResponseHandler.getWantedReasonResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
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
            e.sendInfoMessage();
            return null;
        }
    }

    public static JsonObject sendWantedReasonRemoveRequest(String reason) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("reason", reason);

        try {
            return APIResponseHandler.getWantedReasonRemoveResponse(parameters);
        } catch (APIUnsuccessResponseException e) {
            e.sendInfoMessage();
            return null;
        }
    }
}