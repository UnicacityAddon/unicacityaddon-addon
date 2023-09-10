package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.management.ManagementUser;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.listener.NavigationListener;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class DefaultAddonPlayer implements AddonPlayer {

    private static String latestVersion = null;
    private boolean shouting = false;
    private boolean whispering = false;
    private boolean tempDuty = false;

    private final UnicacityAddon unicacityAddon;

    public DefaultAddonPlayer(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public ClientPlayer getPlayer() {
        return Laby.labyAPI().minecraft().getClientPlayer();
    }

    @Override
    public String getName() {
        return Laby.labyAPI().getName();
    }

    @Override
    public UUID getUniqueId() {
        return Laby.labyAPI().getUniqueId();
    }

    @Override
    public String getShortUniqueId() {
        return getUniqueId().toString().replace("-", "");
    }

    @Override
    public Float getHealth() {
        return getPlayer() != null ? getPlayer().getHealth() : null;
    }

    @Override
    public FloatVector3 getLocation() {
        return getPlayer() != null ? getPlayer().position() : null;
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
        return Laby.labyAPI().minecraft().clientWorld();
    }

    @Override
    public Scoreboard getScoreboard() {
        return Laby.labyAPI().minecraft().getScoreboard();
    }

    @Override
    public Faction getFaction() {
        return this.unicacityAddon.api().getPlayerFactionMap().getOrDefault(getName(), Faction.NULL);
    }

    @Override
    public int getRank() {
        return this.unicacityAddon.api().getPlayerRankMap().getOrDefault(getName(), -1);
    }

    @Override
    public boolean isDuty() {
        return this.tempDuty || this.unicacityAddon.factionService().checkPlayerDuty(getName());
    }

    @Override
    public void setTempDuty(boolean tempDuty) {
        this.tempDuty = tempDuty;
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
    public void setNaviRoute(FloatVector3 location) {
        stopRoute();
        sendServerMessage("/navi " + (int) location.getX() + "/" + (int) location.getY() + "/" + (int) location.getZ());
    }

    @Override
    public void copyToClipboard(String string) {
        Laby.labyAPI().minecraft().setClipboard(string);
    }

    @Override
    public boolean isSuperUser() {
        String uuid = this.getShortUniqueId();
        return uuid.equals("25855f4d38744a7fa6ade9e4f3042e19") || uuid.equals("6e49e42eefca4d9389f9f395b887809e");
    }

    @Override
    public boolean hasGangwar() {
        return getScoreboard().getScores(getScoreboard().getObjective(DisplaySlot.SIDEBAR)).stream()
                .map(ScoreboardScore::getName)
                .anyMatch(s -> s.contains("Angreifer") || s.contains("Verteidiger"));
    }

    @Override
    public Weapon getWeaponInMainHand() {
        Weapon weapon = null;
        if (getPlayer() != null) {
            ItemStack mainHandItemStack = getPlayer().getMainHandItemStack();
            String displayName = this.unicacityAddon.utilService().text().plain(mainHandItemStack.getDisplayName());
            weapon = Weapon.getWeaponByName(displayName);
        }
        return weapon;
    }

    @Override
    public boolean isShouting() {
        return shouting;
    }

    @Override
    public void setShouting(boolean shouting) {
        this.shouting = shouting;
    }

    @Override
    public boolean isWhispering() {
        return whispering;
    }

    @Override
    public void setWhispering(boolean whispering) {
        this.whispering = whispering;
    }

    @Override
    public boolean isPrioritizedMember() {
        Map<String, Integer> filteredPlayerMap = this.unicacityAddon.api().getPlayerFactionMap().entrySet().stream()
                .filter(e -> e.getValue().equals(getFaction())) // name and faction from faction
                .map(Map.Entry::getKey) // name of players from faction
                .filter(s -> this.unicacityAddon.utilService().getOnlinePlayers().contains(s)) // is online
                .filter(this::hasPlayerLatestAddonVersion) // has supported addon version
                .collect(Collectors.toMap(s -> s, this.unicacityAddon.api().getPlayerRankMap()::get)); // collect name and rank of players from faction

        boolean hasAnyPlayerHigherRank = filteredPlayerMap.entrySet().stream()
                .anyMatch(stringIntegerEntry -> stringIntegerEntry.getValue() > getRank()); // has a higher rank than himself

        List<String> rankPlayerList = filteredPlayerMap.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue().equals(getRank()))
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        boolean hasRankPriority = !rankPlayerList.isEmpty() && rankPlayerList.get(0).equals(getName());

        return !hasAnyPlayerHigherRank && hasRankPriority;
    }

    public boolean hasPlayerLatestAddonVersion(String name) {
        ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();

        Optional<ManagementUser> managementUserOptional = this.unicacityAddon.api().getManagementUserList().stream()
                .filter(managementUser -> clientPacketListener != null)
                .filter(managementUser -> {
                    NetworkPlayerInfo networkPlayerInfo = clientPacketListener.getNetworkPlayerInfo(name);
                    return networkPlayerInfo != null && networkPlayerInfo.profile().getUniqueId().toString().replace("-", "").equals(managementUser.getUuid());
                })
                .findAny();

        return managementUserOptional.isPresent() && (managementUserOptional.get().getVersion().equals(getLatestVersion()) || managementUserOptional.get().getVersion().contains("dev"));
    }

    private String getLatestVersion() {
        if (latestVersion == null) {
            latestVersion = this.unicacityAddon.api().sendManagementRequest().getLatestVersion();
        }
        return latestVersion;
    }
}