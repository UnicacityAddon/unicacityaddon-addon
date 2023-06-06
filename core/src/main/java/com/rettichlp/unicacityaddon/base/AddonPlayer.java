package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.FloatVector3;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author RettichLP
 */
public interface AddonPlayer {

    @Nullable ClientPlayer getPlayer();

    @Nullable String getName();

    @Nullable UUID getUniqueId();

    @Nullable Float getHealth();

    @Nullable FloatVector3 getLocation();

    @Nullable Inventory getInventory();

    void sendMessage(String message);

    void sendMessage(Component component);

    void sendAPIMessage(String message, boolean success);

    void sendEmptyMessage();

    void sendErrorMessage(String message);

    void sendInfoMessage(String message);

    void sendSyntaxMessage(String message);

    void sendServerMessage(String message);

    ClientWorld getWorld();

    Scoreboard getScoreboard();

    Faction getFaction();

    int getRank();

    boolean inDuty();

    void sellMedication(String target, DrugType medication);

    void acceptOffer();

    void stopRoute();

    void setNaviRoute(int x, int y, int z);

    void setNaviRoute(FloatVector3 location);

    void copyToClipboard(String string);

    boolean isSuperUser();

    boolean hasGangwar();

    @Nullable Weapon getWeaponInMainHand();

    boolean isGagged();

    void setGagged(boolean gagged);

    boolean isPrioritizedMember();
}