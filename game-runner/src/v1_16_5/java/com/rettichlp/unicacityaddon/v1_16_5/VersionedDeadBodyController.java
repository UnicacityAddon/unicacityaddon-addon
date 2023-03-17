package com.rettichlp.unicacityaddon.v1_16_5;

import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.listener.NameTagListener;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author RettichLP
 */
@Singleton
@Implements(DeadBodyController.class)
public class VersionedDeadBodyController extends DeadBodyController {

    @Inject
    public VersionedDeadBodyController() {
    }

    @Override
    public void updateDisplayName() {
        AABB aabb = Minecraft.getInstance().player.getBoundingBox();
        Minecraft.getInstance().level.getEntitiesOfClass(ItemEntity.class, aabb, itemEntity -> itemEntity != null && itemEntity.hasCustomName() && itemEntity.getItem().getItem().equals(Items.SKELETON_SKULL)).forEach(itemEntity -> {
            String displayName = itemEntity.getCustomName().getString();
            String playerName = displayName.substring(3);

            Faction faction = APIConverter.PLAYERFACTIONMAP.getOrDefault(playerName, Faction.NULL);
            if (!faction.equals(Faction.NULL) && !displayName.contains("◤")) {
                String prefix = NameTagListener.getPrefix(playerName, true);
                String factionInfo = faction.getNameTagSuffix();

                String newDisplayName = (displayName.startsWith(ColorCode.DARK_GRAY.getCode()) /* non-revivable*/ ? ColorCode.DARK_GRAY.getCode() : prefix) + "✟" + playerName + factionInfo;
                itemEntity.setCustomName(Component.nullToEmpty(newDisplayName));
            }
        });
    }
}
