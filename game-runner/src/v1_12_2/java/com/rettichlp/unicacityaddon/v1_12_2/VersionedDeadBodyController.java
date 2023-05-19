package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

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
    public void updateDisplayName(UnicacityAddon unicacityAddon) {
        List<EntityItem> items = Minecraft.getMinecraft().world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        items.forEach(entityItem -> {
            String name = entityItem.getCustomNameTag();
            String playerName = name.substring(3);

            Faction faction = unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL);
            if (!faction.equals(Faction.NULL) && !name.contains("◤")) {
                String prefix = unicacityAddon.services().nametagService().getPrefix(playerName, true);
                String factionInfo = faction.getNameTagSuffix();

                entityItem.setCustomNameTag((name.startsWith(ColorCode.DARK_GRAY.getCode()) /* non-revivable*/ ? ColorCode.DARK_GRAY.getCode() : prefix) + "✟" + playerName + factionInfo);
            }
        });
    }
}
