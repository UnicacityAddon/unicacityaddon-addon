package com.rettichlp.unicacityaddon.v1_18_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        FloatVector3 location = unicacityAddon.player().getLocation();

        assert location != null;
        AABB aabb = new AABB(
                location.getX() - 50,
                location.getY() - 50,
                location.getZ() - 50,
                location.getX() + 50,
                location.getY() + 50,
                location.getZ() + 50
        );

        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.getEntitiesOfClass(ItemEntity.class, aabb, itemEntity -> itemEntity != null && itemEntity.hasCustomName() && itemEntity.getItem().getItem().equals(Items.SKELETON_SKULL)).forEach(itemEntity -> {
            Component customName = itemEntity.getCustomName();

            assert customName != null;
            List<Component> siblings = customName.getSiblings();

            // get player name and revivable status
            String playerName;
            boolean nonRevivable;
            if (siblings.size() > 0) { // sibling size only by not formatted corpses greater than 0
                Component originalCorpseName = siblings.get(0);
                playerName = originalCorpseName.getContents().substring(1);
                nonRevivable = Objects.equals(originalCorpseName.getStyle().getColor(), TextColor.fromLegacyFormat(ChatFormatting.DARK_GRAY));
                this.corpseMap.put(itemEntity.getUUID(), new AbstractMap.SimpleEntry<>(playerName, nonRevivable));
            } else {
                Map.Entry<String, Boolean> corpse = this.corpseMap.getOrDefault(itemEntity.getUUID(), new AbstractMap.SimpleEntry<>("Unbekannt", false));
                playerName = corpse.getKey();
                nonRevivable = corpse.getValue();
            }

            // use player name and revivable status
            String prefix = unicacityAddon.nameTagService().getPrefix(playerName, true);
            String factionInfo = unicacityAddon.configuration().nametag().info().get() ? unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL).getNameTagSuffix() : "";

            String ndn = Message.getBuilder()
                    .of("✟").color(nonRevivable ? ColorCode.DARK_GRAY : ColorCode.GRAY).advance()
                    .of((nonRevivable ? ColorCode.DARK_GRAY.getCode() : prefix) + playerName).advance().space()
                    .of(factionInfo).advance()
                    .create();

            itemEntity.setCustomName(Component.nullToEmpty(ndn));
        });
    }
}
