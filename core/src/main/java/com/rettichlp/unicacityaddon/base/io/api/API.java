package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.AutoNC;
import com.rettichlp.unicacityaddon.api.BlackMarketLocation;
import com.rettichlp.unicacityaddon.api.BlacklistReason;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.api.Revive;
import com.rettichlp.unicacityaddon.api.RoleplayName;
import com.rettichlp.unicacityaddon.api.WantedReason;
import com.rettichlp.unicacityaddon.api.Yasin;
import com.rettichlp.unicacityaddon.api.event.Event;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBan;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBanReason;
import com.rettichlp.unicacityaddon.api.management.Management;
import com.rettichlp.unicacityaddon.api.management.ManagementUser;
import com.rettichlp.unicacityaddon.api.player.Player;
import com.rettichlp.unicacityaddon.api.player.PlayerEntry;
import com.rettichlp.unicacityaddon.api.statistic.Statistic;
import com.rettichlp.unicacityaddon.api.statisticTop.StatisticTop;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.RequestBuilder;
import com.rettichlp.unicacityaddon.base.enums.Activity;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.api.ApplicationPath;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.services.NotificationService;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.Laby;
import net.labymod.api.client.session.Session;
import net.labymod.api.labyconnect.TokenStorage.Purpose;
import net.labymod.api.labyconnect.TokenStorage.Token;
import net.labymod.api.notification.Notification;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * <h3>Session token</h3>
 * An important addon function is to collect statistics and make data available to all players. To ensure
 * user-friendliness, an update should not always have to be created for changes to content-related data. I utilize an
 * API to provide data, leveraging a private server. Data is available for the following purposes:
 * <ul>
 *     <li>activity check <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/activitycheck/LEMILIEU/add">API</a> (unauthorized)</li>
 *     <li>auto nc <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/autonc">API</a> (unauthorized)</li>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
 *     <li>blackmarket locations <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blackmarket">API</a></li>
 *     <li>events <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event">API</a></li>
 *     <li>house bans <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false">API</a> (unauthorized for <code>advanced=true</code>)</li>
 *     <li>house ban reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason">API</a></li>
 *     <li>users <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users">API</a></li>
 *     <li>navi points <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint">API</a></li>
 *     <li>revives <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive">API</a> (unauthorized)</li>
 *     <li>roleplay <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/roleplay">API</a></li>
 *     <li>statistics <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP">API</a></li>
 *     <li>wanted reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason">API</a></li>
 *     <li>yasin <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin">API</a></li>
 * </ul>
 * This data can change constantly. Therefore, it cannot be statically entered into the code.
 * <p>
 * Why do I need the LabyConnect session token for this? For example, the number of revives or the player name who
 * entered a house ban (advanced house ban view) should only be visible to medics. For editing any data, a faction and
 * rank in this faction is necessary.
 * <p>
 * I can read the faction and rank from the Unicacity website
 * (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>). But to assign the faction
 * information to a player, I need his UUID. I could pass these as parameters in the API call, but you could mess that
 * up by calling the endpoint with a different UUID from your own. The LabyMod session token contains the UUID. On the
 * server, I verify the session token with a public key. If the verification passes, I store the player UUID with my
 * generated API token.
 * <p>
 * An additional overview of the authorization can be found
 * <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/autorisierung/">here</a>. An overview of all stored
 * data can be found <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/daten-und-speicherung/">here</a>.
 * The LabyConnect session token is never saved or logged. Only my specially generated token is stored in a database. If
 * necessary, I can provide access to the server code and an insight into all stored data.
 *
 * @author RettichLP
 */
@Getter
public class API {

    private final String AUTHORIZE_SUB_PATH = "authorize";
    private final String ADD_SUB_PATH = "add";
    private final String REMOVE_SUB_PATH = "remove";
    private final String QUEUE_SUB_PATH = "queue";
    private final String SEND_SUB_PATH = "send";
    private final String TOP_SUB_PATH = "top";
    private final String DONE_SUB_PATH = "done";
    private final String USERS_SUB_PATH = "users";
    private final String BANK_ROB_SUB_PATH = "bankrob";
    private final String BOMB_SUB_PATH = "bomb";
    private final String GANGWAR_SUB_PATH = "gangwar";
    private final String UPDATE_SUB_PATH = "update";
    private final String BLOCK_SUB_PATH = "block";
    private final String UNBLOCK_SUB_PATH = "unblock";

    private final Map<String, Faction> playerFactionMap = new HashMap<>();
    private final Map<String, Integer> playerRankMap = new HashMap<>();

    @Setter
    private List<AutoNC> autoNCList = new ArrayList<>();
    @Setter
    private List<BlacklistReason> blacklistReasonList = new ArrayList<>();
    @Setter
    private List<BlackMarketLocation> blackMarketLocationList = new ArrayList<>();
    @Setter
    private List<HouseBan> houseBanList = new ArrayList<>();
    @Setter
    private List<HouseBanReason> houseBanReasonList = new ArrayList<>();
    @Setter
    private List<ManagementUser> managementUserList = new ArrayList<>();
    @Setter
    private List<NaviPoint> naviPointList = new ArrayList<>();
    @Setter
    private List<RoleplayName> roleplayNameList = new ArrayList<>();
    @Setter
    private List<WantedReason> wantedReasonList = new ArrayList<>();

    private String token;
    private AddonPlayer addonPlayer;

    private final UnicacityAddon unicacityAddon;

    public API(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void sync(AddonPlayer addonPlayer) {
        this.addonPlayer = addonPlayer;

        Notification syncStartedNotification = this.unicacityAddon.notificationService().sendUnicacityAddonNotification("Synchronisierung gestartet.", NotificationService.SendState.PROCESSING);

        new Thread(() -> {
            try {
                this.createToken();

                this.loadFactionData();
                this.loadPlayerData();

                this.autoNCList = this.sendAutoNCRequest();
                this.blacklistReasonList = this.sendBlacklistReasonRequest();
                this.blackMarketLocationList = this.sendBlackMarketLocationRequest();
                this.houseBanList = this.sendHouseBanRequest(this.addonPlayer.getFaction().equals(Faction.RETTUNGSDIENST));
                this.houseBanReasonList = this.sendHouseBanReasonRequest();
                this.managementUserList = this.sendManagementUserRequest();
                this.naviPointList = this.sendNaviPointRequest();
                this.roleplayNameList = this.sendRoleplayNameRequest();
                this.wantedReasonList = this.sendWantedReasonRequest();

                Laby.labyAPI().notificationController().pop(syncStartedNotification);
                this.unicacityAddon.notificationService().sendUnicacityAddonNotification("Synchronisierung abgeschlossen.", NotificationService.SendState.SUCCESS);
            } catch (TokenException | APIResponseException | IOException e) {
                this.unicacityAddon.logger().warn("Data synchronization cannot be performed!");
                this.unicacityAddon.logger().error(e.getMessage());

                Laby.labyAPI().notificationController().pop(syncStartedNotification);
                this.unicacityAddon.notificationService().sendUnicacityAddonNotification("Synchronisierung fehlgeschlagen.", NotificationService.SendState.FAILURE);
            }
        }).start();
    }

    private void loadFactionData() {
        playerFactionMap.clear();
        playerRankMap.clear();
        for (Faction faction : Faction.values()) {
            String factionWebsiteSource = this.unicacityAddon.factionService().getWebsiteSource(faction);
            List<String> nameList = this.unicacityAddon.utilService().list().getAllMatchesFromString(PatternHandler.NAME_PATTERN, factionWebsiteSource);
            List<String> rankList = this.unicacityAddon.utilService().list().getAllMatchesFromString(PatternHandler.RANK_PATTERN, factionWebsiteSource);
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
            AddonGroup.CEO.getMemberList().clear();
            AddonGroup.DEV.getMemberList().clear();
            AddonGroup.MOD.getMemberList().clear();
            AddonGroup.SUP.getMemberList().clear();
            AddonGroup.BETA.getMemberList().clear();
            AddonGroup.VIP.getMemberList().clear();
            AddonGroup.BLACKLIST.getMemberList().clear();
            AddonGroup.DYAVOL.getMemberList().clear();

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

    public List<AutoNC> sendAutoNCRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(false) // deactivated because Unicacity guidelines
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.AUTO_NC)
                .getAsJsonArrayAndParse(AutoNC.class);
    }

    public void sendAutoNCAddRequest(String words, String answer) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.AUTO_NC)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "words", words,
                        "answer", answer))
                .sendAsync();
    }

    public void sendAutoNCRemoveRequest(Long id) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.AUTO_NC)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "id", String.valueOf(id)))
                .sendAsync();
    }

    public void sendActivityCheckActivity(Activity activity, String type, String value, DrugType drugType, DrugPurity drugPurity, Long date, String screenshot) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.ACTIVITY_CHECK)
                .subPath(this.addonPlayer.getFaction() + "/add")
                .parameter(Map.of(
                        "activity", String.valueOf(activity),
                        "type", Optional.ofNullable(type).orElse("").replace(" ", "-"),
                        "value", Optional.ofNullable(value).orElse("").replace(" ", "-"),
                        "drugType", Optional.ofNullable(drugType).map(DrugType::name).orElse(""),
                        "drugPurity", String.valueOf(Optional.ofNullable(drugPurity).map(DrugPurity::getPurity).orElse(-1)),
                        "date", String.valueOf(Optional.ofNullable(date).orElse(0L)),
                        "screenshot", Optional.ofNullable(screenshot).orElse("").replace(" ", "-")))
                .sendAsync();
    }

    public void sendBannerAddRequest(@NotNull Faction faction, int x, int y, int z, String naviPoint) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.BANNER)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "faction", faction.toString(),
                        "x", String.valueOf(x),
                        "y", String.valueOf(y),
                        "z", String.valueOf(z),
                        "navipoint", naviPoint))
                .sendAsync();
    }

    public List<BlacklistReason> sendBlacklistReasonRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.addonPlayer.getFaction().isBadFaction())
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction().name())
                .getAsJsonArrayAndParse(BlacklistReason.class);
    }

    public List<BlackMarketLocation> sendBlackMarketLocationRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.BLACKMARKETLOCATION)
                .getAsJsonArrayAndParse(BlackMarketLocation.class);
    }

    public void sendBlacklistReasonAddRequest(String reason, String price, String kills) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction() + "/add")
                .parameter(Map.of(
                        "reason", reason,
                        "price", price,
                        "kills", kills))
                .sendAsync();
    }

    public void sendBlacklistReasonRemoveRequest(String reason) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.BLACKLISTREASON)
                .subPath(this.addonPlayer.getFaction() + "/remove")
                .parameter(Map.of(
                        "reason", reason))
                .sendAsync();
    }

    public Event sendEventRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.EVENT)
                .getAsJsonObjectAndParse(Event.class);
    }

    public void sendEventBankRobRequest(long startTime) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.unicacityAddon.utilService().isUnicacity())
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.EVENT)
                .subPath(BANK_ROB_SUB_PATH)
                .parameter(Map.of(
                        "startTime", String.valueOf(startTime)))
                .sendAsync();
    }

    public void sendEventBombRequest(long startTime) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.unicacityAddon.utilService().isUnicacity())
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.EVENT)
                .subPath(BOMB_SUB_PATH)
                .parameter(Map.of(
                        "startTime", String.valueOf(startTime)))
                .sendAsync();
    }

    public void sendEventGangwarRequest(int attacker, int defender) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.unicacityAddon.utilService().isUnicacity())
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.EVENT)
                .subPath(GANGWAR_SUB_PATH)
                .parameter(Map.of(
                        "attacker", String.valueOf(attacker),
                        "defender", String.valueOf(defender)))
                .sendAsync();
    }

    public List<HouseBan> sendHouseBanRequest(boolean advanced) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBAN)
                .parameter(Map.of(
                        "advanced", String.valueOf(advanced)))
                .getAsJsonArrayAndParse(HouseBan.class);
    }

    public void sendHouseBanAddRequest(String name, String reason) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "name", name,
                        "reason", reason))
                .sendAsync();
    }

    public void sendHouseBanRemoveRequest(String name, String reason) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBAN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "name", name,
                        "reason", reason))
                .sendAsync();
    }

    /**
     * Quote: "Ich teste nicht, ich versage nur..." - RettichLP, 25.09.2022
     */
    public List<HouseBanReason> sendHouseBanReasonRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .getAsJsonArrayAndParse(HouseBanReason.class);
    }

    public void sendHouseBanReasonAddRequest(String reason, String days) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "reason", reason,
                        "days", days))
                .sendAsync();
    }

    public void sendHouseBanReasonRemoveRequest(String reason) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.HOUSEBANREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "reason", reason))
                .sendAsync();
    }

    public Management sendManagementRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.MANAGEMENT)
                .getAsJsonObjectAndParse(Management.class);
    }

    public List<ManagementUser> sendManagementUserRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.MANAGEMENT)
                .subPath(USERS_SUB_PATH)
                .getAsJsonArrayAndParse(ManagementUser.class);
    }

    public List<NaviPoint> sendNaviPointRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.NAVIPOINT)
                .getAsJsonArrayAndParse(NaviPoint.class);
    }

    public void sendNaviPointAddRequest(String name, String x, String y, String z, String article) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "name", name,
                        "x", x,
                        "y", y,
                        "z", z,
                        "article", article))
                .sendAsync();
    }

    public void sendNaviPointRemoveRequest(String name) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.NAVIPOINT)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "name", name))
                .sendAsync();
    }

    public Player sendPlayerRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.PLAYER)
                .getAsJsonObjectAndParse(Player.class);
    }

    public void sendPlayerAddRequest(String name, String group) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "name", name,
                        "group", group))
                .sendAsync();
    }

    public void sendPlayerRemoveRequest(String name, String group) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.PLAYER)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "name", name,
                        "group", group))
                .sendAsync();
    }

    public List<Revive> sendReviveRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.REVIVE)
                .getAsJsonArrayAndParse(Revive.class);
    }

    public List<Revive> sendReviveRankRequest(int rank) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(String.valueOf(rank))
                .getAsJsonArrayAndParse(Revive.class);
    }

    public Revive sendRevivePlayerRequest(String minecraftName) {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.REVIVE)
                .subPath(minecraftName)
                .getAsJsonObjectAndParse(Revive.class);
    }

    public List<RoleplayName> sendRoleplayNameRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.ROLEPLAY)
                .getAsJsonArrayAndParse(RoleplayName.class);
    }

    public void sendRoleplayNameSetRequest(String roleplayName) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.ROLEPLAY)
                .subPath(UPDATE_SUB_PATH)
                .parameter(Map.of(
                        "name", roleplayName))
                .sendAsync();
    }

    public void sendRoleplayNameBlockRequest(String minecraftUuid) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.ROLEPLAY)
                .subPath(BLOCK_SUB_PATH)
                .parameter(Map.of(
                        "minecraftUuid", minecraftUuid))
                .sendAsync();
    }

    public void sendRoleplayNameUnblockRequest(String minecraftUuid) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.ROLEPLAY)
                .subPath(UNBLOCK_SUB_PATH)
                .parameter(Map.of(
                        "minecraftUuid", minecraftUuid))
                .sendAsync();
    }

    public Statistic sendStatisticRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(this.addonPlayer.getName())
                .getAsJsonObjectAndParse(Statistic.class);
    }

    public void sendStatisticAddRequest(StatisticType statisticType) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(this.unicacityAddon.utilService().isUnicacity())
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(this.addonPlayer.getName() + "/add")
                .parameter(Map.of(
                        "type", statisticType.name()))
                .sendAsync();
    }

    public StatisticTop sendStatisticTopRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.STATISTIC)
                .subPath(TOP_SUB_PATH)
                .getAsJsonObjectAndParse(StatisticTop.class);
    }

    public void sendTokenCreateRequest(Token token) throws APIResponseException, IOException {
        File addonFile = getAddonFile();
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.TOKEN)
                .subPath(AUTHORIZE_SUB_PATH)
                .parameter(Map.of(
                        "token", token.getToken(),
                        "version", this.unicacityAddon.utilService().version(),
                        "login", addonFile.exists() ? String.valueOf(getRandomNumber(Files.readAllBytes(addonFile.toPath()))) : ""))
                .send();
    }

    public List<WantedReason> sendWantedReasonRequest() {
        Faction faction = this.addonPlayer.getFaction();
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .preCondition(faction.equals(Faction.POLIZEI) || faction.equals(Faction.FBI))
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.WANTEDREASON)
                .getAsJsonArrayAndParse(WantedReason.class);
    }

    public void sendWantedReasonAddRequest(String reason, String points) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "reason", reason,
                        "points", points))
                .sendAsync();
    }

    public void sendWantedReasonRemoveRequest(String reason) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.WANTEDREASON)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "reason", reason))
                .sendAsync();
    }

    public List<Yasin> sendYasinRequest() {
        return RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.YASIN)
                .getAsJsonArrayAndParse(Yasin.class);
    }

    public void sendYasinAddRequest(String name) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.YASIN)
                .subPath(ADD_SUB_PATH)
                .parameter(Map.of(
                        "name", name))
                .sendAsync();
    }

    public void sendYasinRemoveRequest(String name) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.YASIN)
                .subPath(REMOVE_SUB_PATH)
                .parameter(Map.of(
                        "name", name))
                .sendAsync();
    }

    public void sendYasinDoneRequest(String name) {
        RequestBuilder.getBuilder(this.unicacityAddon)
                .nonProd(this.unicacityAddon.configuration().local().get())
                .applicationPath(ApplicationPath.YASIN)
                .subPath(DONE_SUB_PATH)
                .parameter(Map.of(
                        "name", name))
                .sendAsync();
    }

    public void createToken() throws TokenException, APIResponseException, IOException {
        Optional<Session> sessionOptional = Optional.ofNullable(Laby.labyAPI().minecraft().sessionAccessor().getSession());
        Optional<UUID> uniqueIdOptional = sessionOptional.map(Session::getUniqueId);
        Optional<Token> tokenOptional = uniqueIdOptional.map(uuid -> Laby.references().tokenStorage().getToken(Purpose.JWT, uuid));

        this.token = tokenOptional
                .map(t -> hash(uniqueIdOptional.get().toString().replace("-", "") + t.getToken()))
                .orElseThrow(() -> new TokenException(this.unicacityAddon, "Failed to retrieve LabyConnect session token"));

        this.sendTokenCreateRequest(tokenOptional.get());
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

    private long getRandomNumber(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    public File getAddonFile() {
        String addonName = this.unicacityAddon.addonInfo().getFileName();
        return new File(Laby.labyAPI().labyModLoader().getGameDirectory().toString() + "/labymod-neo/addons/" + addonName);
    }

    public static <T> T find(Collection<T> elements, Predicate<T> predicate) {
        return elements.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    private enum Type {
        STARTED,
        SUCCESS,
        FAILURE
    }
}