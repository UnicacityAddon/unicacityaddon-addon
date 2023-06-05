package com.rettichlp.unicacityaddon.v1_19_4;

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
import java.util.List;
import java.util.Objects;

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
        FloatVector3 position = unicacityAddon.player().getPosition();

        assert position != null;
        AABB aabb = new AABB(
                position.getX() - 50,
                position.getY() - 50,
                position.getZ() - 50,
                position.getX() + 50,
                position.getY() + 50,
                position.getZ() + 50
        );

        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.getEntitiesOfClass(ItemEntity.class, aabb, itemEntity -> itemEntity != null && itemEntity.hasCustomName() && itemEntity.getItem().getItem().equals(Items.SKELETON_SKULL)).forEach(itemEntity -> {
            Component customName = itemEntity.getCustomName();

            assert customName != null;
            List<Component> siblings = customName.getSiblings();

            if (siblings.size() > 0) { // sibling size only by not formatted corpses greater than 0
                Component originalCorpseName = siblings.get(0);

                boolean nonRevivable = Objects.equals(originalCorpseName.getStyle().getColor(), TextColor.fromLegacyFormat(ChatFormatting.DARK_GRAY));

                String playerName = originalCorpseName.getContents().toString().substring(1);

                String prefix = unicacityAddon.services().nametagService().getPrefix(playerName, true);
                String factionInfo = unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL).getNameTagSuffix();

                String ndn = Message.getBuilder()
                        .of("✟").color(nonRevivable ? ColorCode.DARK_GRAY : ColorCode.GRAY).advance()
                        .of((nonRevivable ? ColorCode.DARK_GRAY.getCode() : prefix) + playerName).advance().space()
                        .of(factionInfo).advance()
                        .create();

                itemEntity.setCustomName(Component.nullToEmpty(ndn));
            }
        });
    }
}
