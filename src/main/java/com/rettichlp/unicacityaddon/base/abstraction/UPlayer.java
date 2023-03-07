package com.rettichlp.unicacityaddon.base.abstraction;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/abstraction/UPlayer.java">UCUtils by paulzhng</a>
 */
public interface UPlayer {

    EntityPlayerSP getPlayer();

    void sendMessage(ITextComponent textComponent);

    void sendMessageAsString(String message);

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendAPIMessage(String message, boolean success);

    void sendEmptyMessage();

    void sendChatMessage(String message);

    void playSound(String soundIn);

    void playSound(SoundEvent soundIn, int volume, int pitch);

    String getName();

    UUID getUniqueID();

    BlockPos getPosition();

    Scoreboard getWorldScoreboard();

    World getWorld();

    Container getOpenContainer();

    Container getInventoryContainer();

    Faction getFaction();

    boolean inDuty();

    int getRank();

    void sellMedication(String target, DrugType medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(BlockPos blockPos);

    void copyToClipboard(String string);

    boolean isSuperUser();

    boolean hasGangwar();
}