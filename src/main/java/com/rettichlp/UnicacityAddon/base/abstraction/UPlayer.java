package com.rettichlp.UnicacityAddon.base.abstraction;

import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.rettungsdienst.Medication;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * @author Fuzzlemann
 */
public interface UPlayer {

    EntityPlayerSP getPlayer();

    boolean isConnected();

    void sendMessage(ITextComponent textComponent);

    void sendMessageAsString(String message);

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendEmptyMessage();

    void sendChatMessage(String message);

    void playSound(SoundEvent soundIn, float volume, float pitch);

    String getName();

    NetworkPlayerInfo getNetworkPlayerInfo();

    UUID getUniqueID();

    BlockPos getPosition();

    Team getTeam();

    Scoreboard getWorldScoreboard();

    World getWorld();

    Container getOpenContainer();

    Container getInventoryContainer();

    AxisAlignedBB getEntityBoundingBox();

    NetHandlerPlayClient getConnection();

    Faction getFaction();

    void sellMedication(String target, Medication medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(BlockPos blockPos);

    void copyToClipboard(String string);
}
