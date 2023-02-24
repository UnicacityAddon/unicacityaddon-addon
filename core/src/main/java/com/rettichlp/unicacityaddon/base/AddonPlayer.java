package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.UUID;

/**
 * @author RettichLP
 */
public interface AddonPlayer {

    ClientPlayer getPlayer();

    String getName();

    UUID getUniqueId();

    void sendMessage(String message);

    void sendMessage(Component component);

    void sendAPIMessage(String message, boolean success);

    void sendEmptyMessage();

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendServerMessage(String message);

    ClientWorld getWorld();

    float getHealth();

    FloatVector3 getPosition();

    Scoreboard getScoreboard();

    Inventory getInventory();

//    void playSound(String soundIn);

//    void playSound(SoundEvent soundIn, int volume, int pitch);

    Faction getFaction();

    int getRank();

    boolean inDuty();

    void sellMedication(String target, DrugType medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(FloatVector3 floatVector3);

    void copyToClipboard(String string);

    boolean isSuperUser();
}