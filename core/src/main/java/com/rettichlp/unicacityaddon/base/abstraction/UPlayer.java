package com.rettichlp.unicacityaddon.base.abstraction;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.kyori.adventure.text.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.world.ClientWorld;

import java.util.UUID;
import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 * @see <a
 * href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/abstraction/UPlayer.java">UCUtils
 * by paulzhng</a>
 */
public interface UPlayer {

    Session getSession();

    void sendMessage(Component component);

    void sendMessageAsString(String message);

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendAPIMessage(String message, boolean success);

    void sendEmptyMessage();

    void sendChatMessage(String message);

//    void playSound(String soundIn);
//
//    void playSound(SoundEvent soundIn, int volume, int pitch);

    String getName();

    UUID getUniqueID();

    FloatVector3 getPosition();

    Scoreboard getWorldScoreboard();

    ClientWorld getWorld();

    Inventory getInventoryContainer();

    Faction getFaction();
    boolean inDuty();

    int getRank();

    void sellMedication(String target, DrugType medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(FloatVector3 floatVector3);

    void copyToClipboard(String string);

    boolean isSuperUser();
}