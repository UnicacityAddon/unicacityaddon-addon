package com.rettichlp.unicacityaddon.base;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.models.ManagementUser;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.listener.NavigationListener;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultAddonPlayer implements AddonPlayer {

    private static String latestVersion = null;
    private final UnicacityAddon unicacityAddon;

    public DefaultAddonPlayer(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public ClientPlayer getPlayer() {
        return this.unicacityAddon.labyAPI().minecraft().getClientPlayer();
    }

    @Override
    public String getName() {
        return getPlayer().getName();
    }

    @Override
    public UUID getUniqueId() {
        return getPlayer().getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        this.unicacityAddon.displayMessage(message);
    }

    @Override
    public void sendMessage(Component component) {
        this.unicacityAddon.displayMessage(component);
    }

    @Override
    public void sendAPIMessage(String message, boolean success) {
        sendMessage(Message.getBuilder()
                .prefix()
                .of("API Response:").color(ColorCode.GRAY).advance().space()
                .of(message).color(success ? ColorCode.GREEN : ColorCode.RED).advance()
                .createComponent());
    }

    @Override
    public void sendEmptyMessage() {
        sendMessage("");
    }

    @Override
    public void sendErrorMessage(String message) {
        this.unicacityAddon.displayMessage(Message.getBuilder()
                .error().space()
                .of(message).color(ColorCode.GRAY).advance()
                .createComponent());
    }

    @Override
    public void sendInfoMessage(String message) {
        this.unicacityAddon.displayMessage(Message.getBuilder()
                .info().space()
                .of(message).color(ColorCode.WHITE).advance()
                .createComponent());
    }

    @Override
    public void sendSyntaxMessage(String message) {
        sendErrorMessage("Syntax: " + message);
    }

    @Override
    public void sendServerMessage(String message) {
        this.unicacityAddon.sendMessage(message);
        this.unicacityAddon.logger().info("UPlayer sent chat message: " + message);
    }

    @Override
    public ClientWorld getWorld() {
        return this.unicacityAddon.labyAPI().minecraft().clientWorld();
    }

    @Override
    public float getHealth() {
        return getPlayer().getHealth();
    }

    @Override
    public FloatVector3 getPosition() {
        return getPlayer().position();
    }

    @Override
    public Scoreboard getScoreboard() {
        return this.unicacityAddon.labyAPI().minecraft().getScoreboard();
    }

    @Override
    public Inventory getInventory() {
        return getPlayer().inventory();
    }

    @Override
    public Faction getFaction() {
        return APIConverter.PLAYERFACTIONMAP.getOrDefault(getName(), Faction.NULL);
    }

    @Override
    public int getRank() {
        return APIConverter.PLAYERRANKMAP.getOrDefault(getName(), -1);
    }

    @Override
    public boolean inDuty() {
        return FactionManager.checkPlayerDuty(getName());
    }

    @Override
    public void sellMedication(String target, DrugType medication) {
        sendServerMessage("/rezept " + target + " " + medication.name());
    }

    @Override
    public void acceptOffer() {
        sendServerMessage("/annehmen");
    }

    @Override
    public void stopRoute() {
        NavigationListener.routeMessageClearExecuteTime = System.currentTimeMillis();
        sendServerMessage("/stoproute");
    }

    @Override
    public void setNaviRoute(int x, int y, int z) {
        setNaviRoute(new FloatVector3(x, y, z));
    }

    @Override
    public void setNaviRoute(FloatVector3 floatVector3) {
        stopRoute();
        sendServerMessage("/navi " + (int) floatVector3.getX() + "/" + (int) floatVector3.getY() + "/" + (int) floatVector3.getZ());
    }

    @Override
    public void copyToClipboard(String string) {
        this.unicacityAddon.labyAPI().minecraft().setClipboard(string);
    }

    @Override
    public boolean isSuperUser() {
        String uuid = getUniqueId().toString().replace("-", "");
        return uuid.equals("25855f4d38744a7fa6ade9e4f3042e19") || uuid.equals("6e49e42eefca4d9389f9f395b887809e");
    }

    @Override
    public boolean hasGangwar() {
        return getScoreboard().getScores(getScoreboard().getObjective(DisplaySlot.SIDEBAR)).stream()
                .map(ScoreboardScore::getName)
                .anyMatch(s -> s.contains("Angreifer") || s.contains("Verteidiger"));
    }

    @Override
    public boolean isPrioritizedMember() {
        Map<String, Integer> filteredPlayerMap = APIConverter.PLAYERFACTIONMAP.entrySet().stream()
                .filter(e -> e.getValue().equals(getFaction())) // name and faction from faction
                .map(Map.Entry::getKey) // name of players from faction
                .filter(s -> ForgeUtils.getOnlinePlayers().contains(s)) // is online
                .filter(this::hasPlayerLatestAddonVersion) // has supported addon version
                .collect(Collectors.toMap(s -> s, APIConverter.PLAYERRANKMAP::get)); // collect name and rank of players from faction

        boolean hasAnyPlayerHigherRank = filteredPlayerMap.entrySet().stream()
                .anyMatch(stringIntegerEntry -> stringIntegerEntry.getValue() > getRank()); // has a higher rank than himself

        boolean hasRankPriority = filteredPlayerMap.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(getRank()))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList())
                .get(0)
                .equals(getName());

        return !hasAnyPlayerHigherRank && hasRankPriority;
    }

    public boolean hasPlayerLatestAddonVersion(String name) {
        Optional<ManagementUser> managementUserOptional = APIConverter.MANAGEMENTUSERLIST.stream()
                .filter(mu -> mu.getUuid().equals(UnicacityAddon.ADDON.labyAPI().minecraft().getClientPacketListener().getNetworkPlayerInfo(name).profile().getUniqueId().toString().replace("-", "")))
                .findAny();

        return managementUserOptional.isPresent() && (managementUserOptional.get().getVersion().equals(getLatestVersion()) || managementUserOptional.get().getVersion().contains("dev"));
    }

    private static String getLatestVersion() {
        if (latestVersion == null) {
            String mgmtVersion;
            try {
                JsonObject response = APIRequest.sendManagementRequest();
                mgmtVersion = response.get("latestVersion").getAsString();
            } catch (APIResponseException e) {
                e.sendInfo();
                mgmtVersion = UnicacityAddon.VERSION;
            }
            latestVersion = mgmtVersion;
        }
        return latestVersion;
    }
}