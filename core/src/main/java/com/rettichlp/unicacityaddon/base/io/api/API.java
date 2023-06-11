package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.BlacklistReason;
import com.rettichlp.unicacityaddon.api.Broadcast;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.api.Revive;
import com.rettichlp.unicacityaddon.api.WantedReason;
import com.rettichlp.unicacityaddon.api.Yasin;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBan;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBanReason;
import com.rettichlp.unicacityaddon.api.management.Management;
import com.rettichlp.unicacityaddon.api.management.ManagementUser;
import com.rettichlp.unicacityaddon.api.player.Player;
import com.rettichlp.unicacityaddon.api.player.PlayerEntry;
import com.rettichlp.unicacityaddon.api.response.Success;
import com.rettichlp.unicacityaddon.api.statistic.Statistic;
import com.rettichlp.unicacityaddon.api.statisticTop.StatisticTop;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.RequestBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.session.Session;
import net.labymod.api.notification.Notification;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author RettichLP
 */
@Getter
public class API {

    private final boolean NON_PROD = false;
    private final String ADD_SUB_PATH = "add";
    private final String REMOVE_SUB_PATH = "remove";
    private final String QUEUE_SUB_PATH = "queue";
    private final String SEND_SUB_PATH = "send";
    private final String TOP_SUB_PATH = "top";
    private final String CREATE_SUB_PATH = "create";
    private final String DONE_SUB_PATH = "done";
    private final String USERS_SUB_PATH = "users";
    private final String BOMB_SUB_PATH = "bomb";
    private final String GANGWAR_SUB_PATH = "gangwar";

    private final Map<String, Faction> playerFactionMap = new HashMap<>();
    private final Map<String, Integer> playerRankMap = new HashMap<>();

    @Setter private List<BlacklistReason> blacklistReasonList = new ArrayList<>();
    @Setter private List<HouseBan> houseBanList = new ArrayList<>();
    @Setter private List<HouseBanReason> houseBanReasonList = new ArrayList<>();
    @Setter private List<ManagementUser> managementUserList = new ArrayList<>();
    @Setter private List<NaviPoint> naviPointList = new ArrayList<>();
    @Setter private List<WantedReason> wantedReasonList = new ArrayList<>();

    private String token;
    private AddonPlayer addonPlayer;

    private final UnicacityAddon unicacityAddon;

    public API(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void sync(AddonPlayer addonPlayer) {
        this.addonPlayer = addonPlayer;

        this.unicacityAddon.labyAPI().notificationController().push(syncNotification(Type.STARTED));

        new Thread(() -> {
            try {
                this.createToken();

                this.loadFactionData();
                this.loadPlayerData();

                this.blacklistReasonList = this.sendBlacklistReasonRequest();
                this.houseBanList = this.sendHouseBanRequest(this.addonPlayer.getFaction().equals(Faction.RETTUNGSDIENST));
                this.houseBanReasonList = this.sendHouseBanReasonRequest();
                this.managementUserList = this.sendManagementUserRequest();
                this.naviPointList = this.sendNaviPointRequest();
                this.wantedReasonList = this.sendWantedReasonRequest();

                this.unicacityAddon.labyAPI().notificationController().pop(syncNotification(Type.STARTED));
                this.unicacityAddon.labyAPI().notificationController().push(syncNotification(Type.SUCCESS));
            } catch (APIResponseException e) {
                this.unicacityAddon.logger().warn("API Token was not generated successfully. Data synchronization cannot be performed!");

                this.unicacityAddon.labyAPI().notificationController().pop(syncNotification(Type.STARTED));
                this.unicacityAddon.labyAPI().notificationController().push(syncNotification(Type.FAILURE));
            }
        }).start();
    }

    private Notification syncNotification(Type type) {
        Component text = null;
        ColorCode colorCode = ColorCode.WHITE;

        switch (type) {
            case STARTED -> {
                colorCode = ColorCode.AQUA;
                text = Message.getBuilder().of("Aktualisierung gestartet.").advance().createComponent();
            }
            case SUCCESS -> {
                colorCode = ColorCode.GREEN;
                text = Message.getBuilder().of("Aktualisierung abgeschlossen.").advance().createComponent();
            }
            case FAILURE -> {
                colorCode = ColorCode.RED;
                text = Message.getBuilder().of("Aktualisierung fehlgeschlagen.").advance().createComponent();
            }
        }

        return Notification.builder()
                .title(Message.getBuilder().of("Synchronisierung").color(colorCode).bold().advance().createComponent())
                .text(text)
                .icon(this.unicacityAddon.services().util().icon())
                .type(Notification.Type.ADVANCEMENT)
                .build();
    }

    private enum Type {
        STARTED,
        SUCCESS,
        FAILURE
    }

    private void loadFactionData() {
        playerFactionMap.clear();
        playerRankMap.clear();
        for (Faction faction : Faction.values()) {
            String factionWebsiteSource = this.unicacityAddon.services().faction().getWebsiteSource(faction);
            List<String> nameList = this.unicacityAddon.services().util().listUtils().getAllMatchesFromString(PatternHandler.NAME_PATTERN, factionWebsiteSource);
            List<String> rankList = this.unicacityAddon.services().util().listUtils().getAllMatchesFromString(PatternHandler.RANK_PATTERN, factionWebsiteSource);
            nameList.forEach(name -> {
                String formattedName = name.replace("<h4 class=\"h5 g-mb-5\"><strong>", "");
                playerFactionMap.put(formattedName, faction);
                playerRankMap.put(formattedName, Integer.parseInt(String.valueOf(rankList.get(nameList.indexOf(name))
                        .replace("<strong>Rang ", "")
                        .charAt(0))));
            });
        }
    }

    private void loadPlayerData() {
        Player player = sendPlayerRequest();
        if (player != null) {
            AddonGroup.CEO.getMemberList().addAll(player.getCEO().stream().map(PlayerEntry::getName).toList());
            AddonGroup.DEV.getMemberList().addAll(player.getDEV().stream().map(PlayerEntry::getName).toList());
            AddonGroup.MOD.getMemberList().addAll(player.getMOD().stream().map(PlayerEntry::getName).toList());
            AddonGroup.SUP.getMemberList().addAll(player.getSUP().stream().map(PlayerEntry::getName).toList());
            AddonGroup.BETA.getMemberList().addAll(player.getBETA().stream().map(PlayerEntry::getName).toList());
            AddonGroup.VIP.getMemberList().addAll(player.getVIP().stream().map(PlayerEntry::getName).toList());
            AddonGroup.BLACKLIST.getMemberList().addAll(player.getBLACKLIST().stream().map(PlayerEntry::getName).toList());
            AddonGroup.DYAVOL.getMemberList().addAll(player.getDYAVOL().stream().map(PlayerEntry::getName).toList());
        }
    }

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

    public List<BlacklistReason> sendBlacklistReasonRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.addonPlayer.getFaction().isBadFaction())
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction().name())
                .getAsJsonArrayAndParse(BlacklistReason.class);
    }

    public Success sendBlacklistReasonAddRequest(String reason, String price, String kills) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction() + "/add")
                .parameter(mapOf(
                        "reason", reason,
                        "price", price,
                        "kills", kills))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendBlacklistReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction() + "/remove")
                .parameter(mapOf("reason", reason))
                .getAsJsonObjectAndParse(Success.class);
    }

    public List<Broadcast> sendBroadcastQueueRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(QUEUE_SUB_PATH)
                .getAsJsonArrayAndParse(Broadcast.class);
    }

    public Success sendBroadcastSendRequest(String message, String sendTime) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.BROADCAST)
                .subPath(SEND_SUB_PATH)
                .parameter(mapOf(
                        "message", message,
                        "sendTime", sendTime))
                .getAsJsonObjectAndParse(Success.class);
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

    public List<HouseBan> sendHouseBanRequest(boolean advanced) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(mapOf("advanced", String.valueOf(advanced)))
                .getAsJsonArrayAndParse(HouseBan.class);
    }

    public Success sendHouseBanAddRequest(String name, String reason) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendHouseBanRemoveRequest(String name, String reason) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "reason", reason))
                .getAsJsonObjectAndParse(Success.class);
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public List<HouseBanReason> sendHouseBanReasonRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .getAsJsonArrayAndParse(HouseBanReason.class);
    }

    public Success sendHouseBanReasonAddRequest(String reason, String days) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "days", days))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendHouseBanReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Management sendManagementRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .getAsJsonObjectAndParse(Management.class);
    }

    public List<ManagementUser> sendManagementUserRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.MANAGEMENT)
                .subPath(USERS_SUB_PATH)
                .getAsJsonArrayAndParse(ManagementUser.class);
    }

    public List<NaviPoint> sendNaviPointRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArrayAndParse(NaviPoint.class);
    }

    public Success sendNaviPointAddRequest(String name, String x, String y, String z, String article) {
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
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendNaviPointRemoveRequest(String name) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Player sendPlayerRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObjectAndParse(Player.class);
    }

    public Success sendPlayerAddRequest(String name, String group) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendPlayerRemoveRequest(String name, String group) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf(
                        "name", name,
                        "group", group))
                .getAsJsonObjectAndParse(Success.class);
    }

    public List<Revive> sendReviveRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .getAsJsonArrayAndParse(Revive.class);
    }

    public List<Revive> sendReviveRankRequest(int rank) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(String.valueOf(rank))
                .getAsJsonArrayAndParse(Revive.class);
    }

    public Revive sendRevivePlayerRequest(String minecraftName) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(minecraftName)
                .getAsJsonObjectAndParse(Revive.class);
    }

    public Statistic sendStatisticRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(this.addonPlayer.getName())
                .getAsJsonObjectAndParse(Statistic.class);
    }

    public void sendStatisticAddRequest(StatisticType statisticType) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.unicacityAddon.services().util().isUnicacity())
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(this.addonPlayer.getName() + "/add")
                .parameter(mapOf("type", statisticType.name()))
                .sendAsync();
    }

    public StatisticTop sendStatisticTopRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(TOP_SUB_PATH)
                .getAsJsonObjectAndParse(StatisticTop.class);
    }

    public void sendTokenCreateRequest() throws APIResponseException {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.TOKEN)
                .subPath(CREATE_SUB_PATH)
                .parameter(mapOf(
                        "authToken", this.unicacityAddon.labyAPI().minecraft().sessionAccessor().session().getAccessToken(),
                        "version", this.unicacityAddon.services().util().version()))
                .send();
    }

    public List<WantedReason> sendWantedReasonRequest() {
        Faction faction = this.addonPlayer.getFaction();
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(faction.equals(Faction.POLIZEI) || faction.equals(Faction.FBI))
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .getAsJsonArrayAndParse(WantedReason.class);
    }

    public Success sendWantedReasonAddRequest(String reason, String points) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf(
                        "reason", reason,
                        "points", points))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendWantedReasonRemoveRequest(String reason) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("reason", reason))
                .getAsJsonObjectAndParse(Success.class);
    }

    public List<Yasin> sendYasinRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArrayAndParse(Yasin.class);
    }

    public Success sendYasinAddRequest(String name) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(ADD_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendYasinRemoveRequest(String name) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObjectAndParse(Success.class);
    }

    public Success sendYasinDoneRequest(String name) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(NON_PROD)
                .applicationPath(ApplicationPath.YASIN)
                .subPath(DONE_SUB_PATH)
                .parameter(mapOf("name", name))
                .getAsJsonObjectAndParse(Success.class);
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

    public static <T> T find(Collection<T> elements, Predicate<T> predicate) {
        return elements.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public void createToken() throws APIResponseException {
        Session session = this.unicacityAddon.labyAPI().minecraft().sessionAccessor().session();
        String uuid = session.getUniqueId().toString().replace("-", "");
        String salt = "423WhKRMTfRv4mn6u8hLcPj7bYesKh4Ex4yRErYuW4KsgYjpo35nSU11QYj3OINAJwcd0TPDD6AkqhSq";
        String authToken = session.getAccessToken();
        this.token = hash(uuid + salt + authToken);
        this.sendTokenCreateRequest();
    }

    public String hash(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            // return the HashText
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}