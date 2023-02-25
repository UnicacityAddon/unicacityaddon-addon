package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.listener.NavigationEventHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.FloatVector3;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.AbstractMap;
import java.util.UUID;

public class DefaultAddonPlayer implements AddonPlayer {

    private final UnicacityAddon unicacityAddon;

    public DefaultAddonPlayer(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public ClientPlayer getPlayer() {
        return this.unicacityAddon.labyAPI().minecraft().clientPlayer();
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
        return FactionManager.getInstance().getFactionData().getOrDefault(getName(), new AbstractMap.SimpleEntry<>(Faction.NULL, -1)).getKey();
    }

    @Override
    public int getRank() {
        return FactionManager.getInstance().getFactionData().getOrDefault(getName(), new AbstractMap.SimpleEntry<>(Faction.NULL, -1)).getValue();
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
        NavigationEventHandler.routeMessageClearExecuteTime = System.currentTimeMillis();
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
        StringSelection stringSelection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public boolean isSuperUser() {
        String uuid = getUniqueId().toString().replace("-", "");
        return uuid.equals("25855f4d38744a7fa6ade9e4f3042e19") || uuid.equals("6e49e42eefca4d9389f9f395b887809e");
    }
}