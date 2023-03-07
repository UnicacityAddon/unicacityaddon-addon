package com.rettichlp.unicacityaddon.base.abstraction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/abstraction/UPlayerImpl.java">UCUtils by paulzhng</a>
 */
public class UPlayerImpl implements UPlayer {

    @Override
    public EntityPlayerSP getPlayer() {
        return UnicacityAddon.MINECRAFT.player;
    }

    @Override
    public void sendMessage(ITextComponent textComponent) {
        if (!UnicacityAddon.ADDON.getApi().isIngame()) {
            UnicacityAddon.LOGGER.warn("UPlayer not in game! Aborting message: " + textComponent.getUnformattedText());
            return;
        }
        getPlayer().sendMessage(textComponent);
    }

    @Override
    public void sendMessageAsString(String message) {
        sendMessage(new TextComponentString(message));
    }

    @Override
    public void sendErrorMessage(String message) {
        sendMessage(Message.getBuilder()
                .error().space()
                .of(message).color(ColorCode.GRAY).advance()
                .createComponent());
    }

    @Override
    public void sendInfoMessage(String message) {
        sendMessage(Message.getBuilder()
                .info().space()
                .of(message).color(ColorCode.WHITE).advance()
                .createComponent());
    }

    @Override
    public void sendSyntaxMessage(String message) {
        sendErrorMessage("Syntax: " + message);
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
        sendMessage(Message.getBuilder().createComponent());
    }

    @Override
    public void sendChatMessage(String message) {
        getPlayer().sendChatMessage(message);
        UnicacityAddon.LOGGER.info("UPlayer sent chat message: " + message);
    }

    @Override
    public void playSound(String soundIn) {
        ResourceLocation resourcelocation = new ResourceLocation(soundIn);
        playSound(SoundEvent.REGISTRY.getObject(resourcelocation), 1, 1);
    }

    @Override
    public void playSound(SoundEvent soundIn, int volume, int pitch) {
        boolean execute = !soundIn.getSoundName().getResourceDomain().contains("unicacityaddon");

        if (soundIn.equals(SoundRegistry.BOMB_SOUND)) {
            execute = ConfigElements.getBombSound();
        } else if (soundIn.equals(SoundRegistry.CONTRACT_SET_SOUND)) {
            execute = ConfigElements.getContractSetSound();
        } else if (soundIn.equals(SoundRegistry.CONTRACT_FULFILLED_SOUND)) {
            execute = ConfigElements.getContractFulfilledSound();
        } else if (soundIn.equals(SoundRegistry.REPORT_SOUND)) {
            execute = ConfigElements.getReportSound();
        } else if (soundIn.equals(SoundRegistry.SERVICE_SOUND)) {
            execute = ConfigElements.getServiceSound();
        }

        if (execute)
            getPlayer().playSound(soundIn, volume, pitch);
    }

    @Override
    public String getName() {
        return UnicacityAddon.MINECRAFT.getSession().getUsername();
    }

    @Override
    public UUID getUniqueID() {
        return UnicacityAddon.MINECRAFT.getSession().getProfile().getId();
    }

    @Override
    public BlockPos getPosition() {
        return getPlayer().getPosition();
    }

    @Override
    public Scoreboard getWorldScoreboard() {
        return getPlayer().getWorldScoreboard();
    }

    @Override
    public World getWorld() {
        return getPlayer().getEntityWorld();
    }

    @Override
    public Container getOpenContainer() {
        return getPlayer().openContainer;
    }

    @Override
    public Container getInventoryContainer() {
        return getPlayer().inventoryContainer;
    }

    @Override
    public Faction getFaction() {
        return Syncer.PLAYERFACTIONMAP.getOrDefault(getName(), Faction.NULL);
    }

    @Override
    public boolean inDuty() {return FactionManager.checkPlayerDuty(getName());}

    @Override
    public int getRank() {
        return Syncer.PLAYERRANKMAP.getOrDefault(getName(), -1);
    }

    @Override
    public void sellMedication(String target, DrugType medication) {
        sendChatMessage("/rezept " + target + " " + medication.name());
    }

    @Override
    public void acceptOffer() {
        sendChatMessage("/annehmen");
    }

    @Override
    public void stopRoute() {
        sendChatMessage("/stoproute");
    }

    @Override
    public void setNaviRoute(int x, int y, int z) {
        setNaviRoute(new BlockPos(x, y, z));
    }

    @Override
    public void setNaviRoute(BlockPos blockPos) {
        NavigationUtils.stopRoute();
        sendChatMessage("/navi " + blockPos.getX() + "/" + blockPos.getY() + "/" + blockPos.getZ());
    }

    @Override
    public void copyToClipboard(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public boolean isSuperUser() {
        String uuid = getUniqueID().toString().replace("-", "");
        return uuid.equals("25855f4d38744a7fa6ade9e4f3042e19") || uuid.equals("6e49e42eefca4d9389f9f395b887809e");
    }

    @Override
    public boolean hasGangwar() {
        return getWorldScoreboard().getObjectiveNames().containsAll(Arrays.asList("Angreifer", "Verteidiger"));
    }
}