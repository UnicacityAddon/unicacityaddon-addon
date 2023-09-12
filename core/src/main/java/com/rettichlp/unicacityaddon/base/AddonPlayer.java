package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author RettichLP
 */
public interface AddonPlayer {

    @Nullable
    ClientPlayer getPlayer();

    String getName();

    UUID getUniqueId();

    String getShortUniqueId();

    @Nullable
    Float getHealth();

    @Nullable
    FloatVector3 getLocation();

    void sendMessage(String message);

    void sendMessage(Component component);

    void sendEmptyMessage();

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendServerMessage(String message);

    ClientWorld getWorld();

    Scoreboard getScoreboard();

    Faction getFaction();

    int getRank();

    boolean isDuty();

    void setTempDuty(boolean tempDuty);

    void sellMedication(String target, DrugType medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(FloatVector3 location);

    void copyToClipboard(String string);

    boolean isSuperUser();

    boolean hasGangwar();

    @Nullable
    Weapon getWeaponInMainHand();

    boolean isShouting();

    void setShouting(boolean shouting);

    boolean isWhispering();

    void setWhispering(boolean whispering);

    boolean isPrioritizedMember();
}