package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author RettichLP
 */
@Singleton
@Implements(DeadBodyController.class)
public class VersionedDeadBodyController extends DeadBodyController {

    /**
     * When a corpse is rendered, the display name is automatically changed. The name can reflect the current wanted
     * level. However, if this state changes while the corpse has already been rendered, the color will not change. In
     * order to ensure this change anyway, the name must be available in every render process. In order not to waste
     * performance by calculating the name in every render process, the name that was calculated when the corpse was
     * first rendered is used. Likewise, the status of whether the corpse can be revived.
     */
    private final Map<UUID, Map.Entry<String, Boolean>> corpseMap = new HashMap<>();

    @Inject
    public VersionedDeadBodyController() {
    }

    @Override
    public void updateDisplayName(UnicacityAddon unicacityAddon) {
        List<EntityItem> items = Minecraft.getMinecraft().world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        items.forEach(entityItem -> {
            String customNameTag = entityItem.getCustomNameTag();

            // get player name and revivable status
            String playerName;
            boolean nonRevivable;
            if (!customNameTag.contains("◤")) { // ◤ only in formatted corpses
                playerName = customNameTag.substring(3);
                nonRevivable = customNameTag.contains(ColorCode.DARK_GRAY.getCode());
                this.corpseMap.put(entityItem.getUniqueID(), new AbstractMap.SimpleEntry<>(playerName, nonRevivable));
            } else {
                Map.Entry<String, Boolean> corpse = this.corpseMap.getOrDefault(entityItem.getUniqueID(), new AbstractMap.SimpleEntry<>("Unbekannt", false));
                playerName = corpse.getKey();
                nonRevivable = corpse.getValue();
            }

            // use player name and revivable status
            String prefix = unicacityAddon.nameTagService().getPrefix(playerName, true);
            String factionInfo = unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL).getNameTagSuffix();

            String ndn = Message.getBuilder()
                    .of("✟").color(nonRevivable ? ColorCode.DARK_GRAY : ColorCode.GRAY).advance()
                    .of((nonRevivable ? ColorCode.DARK_GRAY.getCode() : prefix) + playerName).advance().space()
                    .of(factionInfo).advance()
                    .create();

            entityItem.setCustomNameTag(ndn);
        });
    }
}
