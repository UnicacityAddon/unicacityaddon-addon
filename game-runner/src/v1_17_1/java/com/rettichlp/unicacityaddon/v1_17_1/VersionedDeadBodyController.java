package com.rettichlp.unicacityaddon.v1_17_1;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.listener.NameTagListener;
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
    public void updateDisplayName() {
        FloatVector3 position = UnicacityAddon.PLAYER.getPosition();

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
            if (!customName.getString().contains("§")) { // not already formatted
                String playerName = customName.getString().substring(1);

                String prefix = NameTagListener.getPrefix(playerName, true);
                String factionInfo = APIConverter.PLAYERFACTIONMAP.getOrDefault(playerName, Faction.NULL).getNameTagSuffix();

                boolean nonRevivable = Objects.equals(customName.getStyle().getColor(), TextColor.fromLegacyFormat(ChatFormatting.DARK_GRAY));

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
