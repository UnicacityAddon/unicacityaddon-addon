package com.rettichlp.unicacityaddon.base.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.RequestBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
import com.rettichlp.unicacityaddon.base.models.Broadcast;
import com.rettichlp.unicacityaddon.base.models.HouseBan;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.models.ManagementUser;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.models.Revive;
import com.rettichlp.unicacityaddon.base.models.WantedReason;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.notification.Notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
public class API {

    private Map<String, Faction> playerFactionMap;
    private Map<String, Integer> playerRankMap;
    private List<BlacklistReason> blacklistReasonList;
    private List<HouseBan> houseBanList;
    private List<HouseBanReason> houseBanReasonList;
    private List<ManagementUser> managementUserList;
    private List<NaviPoint> naviPointList;
    private List<WantedReason> wantedReasonList;

    private final UnicacityAddon unicacityAddon;

    public API(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;

        this.playerFactionMap = new HashMap<>();
        this.playerRankMap = new HashMap<>();
        this.blacklistReasonList = new ArrayList<>();
        this.houseBanList = new ArrayList<>();
        this.houseBanReasonList = new ArrayList<>();
        this.managementUserList = new ArrayList<>();
        this.naviPointList = new ArrayList<>();
        this.wantedReasonList = new ArrayList<>();
    }

    public Map<String, Faction> getPlayerFactionMap() {
        return playerFactionMap;
    }

    public void setPlayerFactionMap(Map<String, Faction> playerFactionMap) {
        this.playerFactionMap = playerFactionMap;
    }

    public Map<String, Integer> getPlayerRankMap() {
        return playerRankMap;
    }

    public void setPlayerRankMap(Map<String, Integer> playerRankMap) {
        this.playerRankMap = playerRankMap;
    }

    public List<BlacklistReason> getBlacklistReasonList() {
        return blacklistReasonList;
    }

    public void setBlacklistReasonList(List<BlacklistReason> blacklistReasonList) {
        this.blacklistReasonList = blacklistReasonList;
    }

    public List<HouseBan> getHouseBanList() {
        return houseBanList;
    }

    public void setHouseBanList(List<HouseBan> houseBanList) {
        this.houseBanList = houseBanList;
    }

    public List<HouseBanReason> getHouseBanReasonList() {
        return houseBanReasonList;
    }

    public void setHouseBanReasonList(List<HouseBanReason> houseBanReasonList) {
        this.houseBanReasonList = houseBanReasonList;
    }

    public List<ManagementUser> getManagementUserList() {
        return managementUserList;
    }

    public void setManagementUserList(List<ManagementUser> managementUserList) {
        this.managementUserList = managementUserList;
    }

    public List<NaviPoint> getNaviPointList() {
        return naviPointList;
    }

    public void setNaviPointList(List<NaviPoint> naviPointList) {
        this.naviPointList = naviPointList;
    }

    public List<WantedReason> getWantedReasonList() {
        return wantedReasonList;
    }

    public void setWantedReasonList(List<WantedReason> wantedReasonList) {
        this.wantedReasonList = wantedReasonList;
    }

    public void syncAll() {
        new Thread(() -> {
            try {
                JsonObject response = sendPlayerRequest();
                for (AddonGroup addonGroup : AddonGroup.values()) {
                    addonGroup.getMemberList().clear();
                    for (JsonElement jsonElement : response.getAsJsonArray(addonGroup.getApiName())) {
                        addonGroup.getMemberList().add(jsonElement.getAsJsonObject().get("name").getAsString());
                    }
                }

                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Addon-Gruppen aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
            } catch (APIResponseException e) {
                e.sendInfo();
            }
        }).start();

        new Thread(() -> {
            playerFactionMap.clear();
            playerRankMap.clear();
            for (Faction faction : Faction.values()) {
                String factionWebsiteSource = this.unicacityAddon.services().factionService().getWebsiteSource(faction);
                List<String> nameList = this.unicacityAddon.utils().listUtils().getAllMatchesFromString(PatternHandler.NAME_PATTERN, factionWebsiteSource);
                List<String> rankList = this.unicacityAddon.utils().listUtils().getAllMatchesFromString(PatternHandler.RANK_PATTERN, factionWebsiteSource);
                nameList.forEach(name -> {
                    String formattedname = name.replace("<h4 class=\"h5 g-mb-5\"><strong>", "");
                    playerFactionMap.put(formattedname, faction);
                    playerRankMap.put(formattedname, Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                            .replace("<strong>Rang ", "")
                            .charAt(0))));
                });
            }

            this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                    .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                    .text(Message.getBuilder().of("Fraktionen aktualisiert.").advance().createComponent())
                    .icon(this.unicacityAddon.utils().icon())
                    .type(Notification.Type.ADVANCEMENT)
                    .build());
        }).start();

        new Thread(() -> {
            if (!(houseBanList = loadHouseBanList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Hausverbote aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();

        new Thread(() -> {
            if (!(houseBanReasonList = loadHouseBanReasonList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Hausverbot-Gründe aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();

        new Thread(() -> {
            if (!(managementUserList = loadManagementUserList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Management-User aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();

        new Thread(() -> {
            if (!(naviPointList = loadNaviPointList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Navipunkte aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();

        new Thread(() -> {
            if (!(blacklistReasonList = loadBlacklistReasonList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Blacklist-Gründe aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();

        new Thread(() -> {
            if (!(wantedReasonList = loadWantedReasonList()).isEmpty())
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Synchronisierung").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Wanted-Gründe aktualisiert.").advance().createComponent())
                        .icon(this.unicacityAddon.utils().icon())
                        .type(Notification.Type.ADVANCEMENT)
                        .build());
        }).start();
    }

    public List<BlacklistReason> loadBlacklistReasonList() {
        List<BlacklistReason> blacklistReasonList = new ArrayList<>();
        try {
            sendBlacklistReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                int kills = o.get("kills").getAsInt();
                int price = o.get("price").getAsInt();

                blacklistReasonList.add(new BlacklistReason(reason, kills, price));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return blacklistReasonList;
    }

    public List<Broadcast> loadBroadcastList() {
        List<Broadcast> broadcastList = new ArrayList<>();
        try {
            sendBroadcastQueueRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String broadcast = o.get("broadcast").getAsString();
                int id = o.get("id").getAsInt();
                String issuerName = o.get("issuerName").getAsString();
                String issuerUUID = o.get("issuerUUID").getAsString();
                long sendTime = o.get("sendTime").getAsLong();
                long time = o.get("time").getAsLong();

                broadcastList.add(new Broadcast(broadcast, id, issuerName, issuerUUID, sendTime, time));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return broadcastList;
    }

    public List<HouseBan> loadHouseBanList() {
        List<HouseBan> houseBanList = new ArrayList<>();
        try {
            sendHouseBanRequest(this.unicacityAddon.player().getFaction().equals(Faction.RETTUNGSDIENST)).forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                long duration = o.get("duration").getAsLong();
                List<HouseBanReason> houseBanReasonList = new ArrayList<>();
                long expirationTime = o.get("expirationTime").getAsLong();
                String name = o.get("name").getAsString();
                long startTime = o.get("startTime").getAsLong();
                String uuid = o.get("uuid").getAsString();

                o.get("houseBanReasonList").getAsJsonArray().forEach(jsonElement1 -> {
                    JsonObject o1 = jsonElement1.getAsJsonObject();

                    String reason = o1.get("reason").getAsString();
                    String issuerUUID = o1.has("issuerUUID") ? o1.get("issuerUUID").getAsString() : null;
                    String issuerName = o1.has("issuerName") ? o1.get("issuerName").getAsString() : null;
                    int days = o1.get("days").getAsInt();

                    houseBanReasonList.add(new HouseBanReason(reason, issuerUUID, issuerName, days));
                });

                houseBanList.add(new HouseBan(duration, houseBanReasonList, expirationTime, name, startTime, uuid));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return houseBanList;
    }

    public List<HouseBanReason> loadHouseBanReasonList() {
        List<HouseBanReason> houseBanReasonList = new ArrayList<>();
        try {
            sendHouseBanReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                String creatorUUID = o.has("creatorUUID") ? o.get("creatorUUID").getAsString() : null;
                String creatorName = o.has("creatorName") ? o.get("creatorName").getAsString() : null;
                int days = o.get("days").getAsInt();

                houseBanReasonList.add(new HouseBanReason(reason, creatorUUID, creatorName, days));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return houseBanReasonList;
    }

    private List<ManagementUser> loadManagementUserList() {
        List<ManagementUser> managementUserList = new ArrayList<>();
        try {
            sendManagementUserRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                boolean active = o.get("active").getAsBoolean();
                String uuid = o.get("uuid").getAsString();
                String version = o.get("version").getAsString();

                managementUserList.add(new ManagementUser(active, uuid, version));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return managementUserList;
    }

    private List<NaviPoint> loadNaviPointList() {
        List<NaviPoint> naviPointList = new ArrayList<>();
        try {
            sendNaviPointRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String name = o.get("name").getAsString();
                int x = o.get("x").getAsInt();
                int y = o.get("y").getAsInt();
                int z = o.get("z").getAsInt();
                String article = o.get("article").getAsString();

                naviPointList.add(new NaviPoint(name, x, y, z, article));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return naviPointList;
    }

    public List<Revive> loadReviveList() {
        List<Revive> reviveList = new ArrayList<>();
        try {
            sendReviveRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                int currentWeekReviveAmount = o.get("currentWeekReviveAmount").getAsInt();
                int lastWeekReviveAmount = o.get("lastWeekReviveAmount").getAsInt();
                String minecraftName = o.get("minecraftName").getAsString();
                String minecraftUuid = o.get("minecraftUuid").getAsString();

                reviveList.add(new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return reviveList;
    }

    public List<Revive> loadReviveRankList(int rank) {
        List<Revive> reviveList = new ArrayList<>();
        try {
            sendReviveRankRequest(rank).forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                int currentWeekReviveAmount = o.get("currentWeekReviveAmount").getAsInt();
                int lastWeekReviveAmount = o.get("lastWeekReviveAmount").getAsInt();
                String minecraftName = o.get("minecraftName").getAsString();
                String minecraftUuid = o.get("minecraftUuid").getAsString();

                reviveList.add(new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return reviveList;
    }

    public Revive loadRevivePlayer(String minecraftNameString) {
        try {
            JsonObject jsonObject = sendRevivePlayerRequest(minecraftNameString);

            int currentWeekReviveAmount = jsonObject.get("currentWeekReviveAmount").getAsInt();
            int lastWeekReviveAmount = jsonObject.get("lastWeekReviveAmount").getAsInt();
            String minecraftName = jsonObject.get("minecraftName").getAsString();
            String minecraftUuid = jsonObject.get("minecraftUuid").getAsString();

            return new Revive(currentWeekReviveAmount, lastWeekReviveAmount, minecraftName, minecraftUuid);
        } catch (APIResponseException e) {
            e.sendInfo();
            return null;
        }
    }

    private List<WantedReason> loadWantedReasonList() {
        List<WantedReason> wantedReasonList = new ArrayList<>();
        try {
            sendWantedReasonRequest().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();

                String reason = o.get("reason").getAsString();
                int points = o.get("points").getAsInt();

                wantedReasonList.add(new WantedReason(reason, points));
            });
        } catch (APIResponseException e) {
            e.sendInfo();
        }
        return wantedReasonList;
    }

    private static final boolean NON_PROD = false;
    private static final String ADD_SUB_PATH = "add";
    private static final String REMOVE_SUB_PATH = "remove";
    private static final String QUEUE_SUB_PATH = "queue";
    private static final String SEND_SUB_PATH = "send";
    private static final String TOP_SUB_PATH = "top";
    private static final String CREATE_SUB_PATH = "create";
    private static final String DONE_SUB_PATH = "done";
    private static final String USERS_SUB_PATH = "users";
    private static final String BOMB_SUB_PATH = "bomb";
    private static final String GANGWAR_SUB_PATH = "gangwar";

    public void sendBannerAddRequest(Faction faction, int x, int y, int z, String navipoint) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BANNER)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "faction", faction.toString(),
                        "x", String.valueOf(x),
                        "y", String.valueOf(y),
                        "z", String.valueOf(z),
                        "navipoint", navipoint))
                .sendAsync();
    }

    public JsonArray sendBlacklistReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.unicacityAddon.player().getFaction().name())
                .getAsJsonArray();
    }

    public JsonObject sendBlacklistReasonAddRequest(String reason, String price, String kills) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.unicacityAddon.player().getFaction() + "/add")
                .parameter(mapOf(
                        "reason", reason,
                        "price", price,
                        "kills", kills))
                .getAsJsonObject();
    }

    public JsonObject sendBlacklistReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.unicacityAddon.player().getFaction() + "/remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public JsonArray sendBroadcastQueueRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(QUEUE_SUB_PATH)
                .getAsJsonArray();
    }

    public JsonObject sendBroadcastSendRequest(String message, String sendTime) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(SEND_SUB_PATH)
                .parameter(mapOf(
                        "message", message,
                        "sendTime", sendTime))
                .getAsJsonObject();
    }

    public void sendEventBombRequest(long startTime) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.EVENT)
                .subPath(BOMB_SUB_PATH)
                .parameter(mapOf(
                        "startTime", String.valueOf(startTime)))
                .sendAsync();
    }

    public void sendEventGangwarRequest(int attacker, int defender) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.EVENT)
                .subPath(GANGWAR_SUB_PATH)
                .parameter(mapOf(
                        "attacker", String.valueOf(attacker),
                        "defender", String.valueOf(defender)))
                .sendAsync();
    }

    public JsonArray sendHouseBanRequest(boolean advanced) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(mapOf("advanced", String.valueOf(advanced)))
                .getAsJsonArray();
    }

    public JsonObject sendHouseBanAddRequest(String name, String reason) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObject();
    }

    public JsonObject sendHouseBanRemoveRequest(String name, String reason) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObject();
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public JsonArray sendHouseBanReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .getAsJsonArray();
    }

    public JsonObject sendHouseBanReasonAddRequest(String reason, String days) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "days", days))
                .getAsJsonObject();
    }

    public JsonObject sendHouseBanReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public JsonObject sendManagementRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .getAsJsonObject();
    }

    public JsonArray sendManagementUserRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .subPath(USERS_SUB_PATH)
                .getAsJsonArray();
    }

    public JsonArray sendNaviPointRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArray();
    }

    public JsonObject sendNaviPointAddRequest(String name, String x, String y, String z, String article) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "x", x,
                        "y", y,
                        "z", z,
                        "article", article))
                .getAsJsonObject();
    }

    public JsonObject sendNaviPointRemoveRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public JsonObject sendPlayerRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObject();
    }

    public JsonObject sendPlayerAddRequest(String name, String group) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObject();
    }

    public JsonObject sendPlayerRemoveRequest(String name, String group) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObject();
    }

    public JsonArray sendReviveRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .getAsJsonArray();
    }

    public JsonArray sendReviveRankRequest(int rank) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(String.valueOf(rank))
                .getAsJsonArray();
    }

    public JsonObject sendRevivePlayerRequest(String minecraftName) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(minecraftName)
                .getAsJsonObject();
    }

    public JsonObject sendStatisticRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(this.unicacityAddon.player().getName())
                .getAsJsonObject();
    }

    public void sendStatisticAddRequest(StatisticType statisticType) {
        if (this.unicacityAddon.utils().isUnicacity()) {
            RequestBuilder.getBuilder(this.unicacityAddon)
                    .nonProd(NON_PROD)
                    .applicationPath(ApplicationPath.STATISTIC)
                    .subPath(this.unicacityAddon.player().getName() + "/add")
                    .parameter(mapOf("type", statisticType.name()))
                    .sendAsync();
        }
    }

    public JsonObject sendStatisticTopRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(TOP_SUB_PATH)
                .getAsJsonObject();
    }

    public void sendTokenCreateRequest() {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.TOKEN)
                .subPath(CREATE_SUB_PATH)
                .parameter(mapOf(
                        "authToken", this.unicacityAddon.labyAPI().minecraft().sessionAccessor().session().getAccessToken(),
                        "version", this.unicacityAddon.utils().version()))
                .sendAsync();
    }

    public JsonArray sendWantedReasonRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .getAsJsonArray();
    }

    public JsonObject sendWantedReasonAddRequest(String reason, String points) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "points", points))
                .getAsJsonObject();
    }

    public JsonObject sendWantedReasonRemoveRequest(String reason) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObject();
    }

    public JsonArray sendYasinRequest() throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArray();
    }

    public JsonObject sendYasinAddRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public JsonObject sendYasinRemoveRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public JsonObject sendYasinDoneRequest(String name) throws APIResponseException {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(DONE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObject();
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }
}