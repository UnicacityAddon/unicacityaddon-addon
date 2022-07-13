/*
package com.rettichlp.UnicacityAddon.events;

import com.google.common.eventbus.Subscribe;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.event.UCEventLabymod;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.labymod.api.event.events.client.renderer.RenderNameTagEvent;
import net.labymod.api.events.RenderEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@UCEventLabymod(event = "RenderEntityEvent")
public class NameTagEventHandler implements RenderEntityEvent {

    private static int tick;

    @Subscribe
    public void onRenderNameTag(RenderNameTagEvent e) {
        String playerName = e.getDisplayName().getString();
        String houseban = getHouseban(playerName);
        String prefix = getPrefix(playerName);
        String suffix = getSuffix(playerName);
        e.setDisplayName(ITextComponent.getTextComponentOrEmpty(houseban + prefix + playerName + suffix));
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (e.getPhase() != TickEvent.Phase.PRE) return;
        if (AbstractionLayer.getPlayer().getWorld() == null) return;
        if (tick++ != 20) return;

        getCorpsesInRange(30).forEach(itemEntity -> {
            String name = itemEntity.getCustomName().getString();
            String playerName = name.substring(3);

            if (!FactionHandler.getPlayerFactionMap().containsKey(name.substring(3))) return;
            if (name.contains("◤")) return; // already edited

            String prefix = getPrefix(playerName);
            String suffix = getSuffix(playerName);

            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                itemEntity.setCustomName(ITextComponent.getTextComponentOrEmpty(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + suffix));
                return;
            }

            itemEntity.setCustomName(ITextComponent.getTextComponentOrEmpty(ColorCode.GRAY.getCode() + prefix + "✟" + playerName + suffix));
        });

        tick = 0;
    }

    private @NotNull String getHouseban(String playerName) {
        StringBuilder houseban = new StringBuilder();
        houseban.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagHouseban()) {
            if (FactionHandler.checkPlayerHouseBan(playerName)) houseban.append(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("HV").color(ColorCode.RED).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance()
                    .add(FormattingCode.RESET.getCode())
                    .create());
        }

        return houseban.toString();
    }

    private @NotNull String getPrefix(String playerName) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());

        if (FactionHandler.getPlayerFactionMap().containsKey(playerName)) {
            Faction targetPlayerFaction = FactionHandler.getPlayerFactionMap().get(playerName);

            if (ConfigElements.getNameTagFaction()) {
                if (targetPlayerFaction.equals(AbstractionLayer.getPlayer().getFaction()))
                    prefix.append(ConfigElements.getNameTagFactionColor().getCode());
            }

            if (ConfigElements.getNameTagAlliance()) {
                if (targetPlayerFaction.equals(ConfigElements.getNameTagAlliance1()) || targetPlayerFaction.equals(ConfigElements.getNameTagAlliance2()))
                    prefix.append(ConfigElements.getNameTagAllianceColor().getCode());
            }

            if (ConfigElements.getNameTagStreetwar()) {
                if (targetPlayerFaction.equals(ConfigElements.getNameTagStreetwar1()) || targetPlayerFaction.equals(ConfigElements.getNameTagStreetwar2()))
                    prefix.append(ConfigElements.getNameTagStreetwarColor().getCode());
            }
        }

        return prefix.toString();
    }

    private @NotNull String getSuffix(String playerName) {
        StringBuilder suffix = new StringBuilder();
        suffix.append(FormattingCode.RESET.getCode());

        if (FactionHandler.getPlayerFactionMap().containsKey(playerName)) {
            Faction targetPlayerFaction = FactionHandler.getPlayerFactionMap().get(playerName);
            if (ConfigElements.getNameTagFactionSuffix()) suffix.append(" ").append(targetPlayerFaction.getNameTagSuffix());
        }

        return suffix.toString();
    }

    private List<ItemEntity> getCorpsesInRange(int range) {
        BlockPos pos = AbstractionLayer.getPlayer().getPosition();
        return AbstractionLayer.getPlayer().getWorld().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(
                pos.getX() - range,
                pos.getY() - range,
                pos.getZ() - range,
                pos.getX() + range,
                pos.getY() + range,
                pos.getZ() + range),
                itemEntity -> itemEntity != null && itemEntity.hasCustomName() && itemEntity.getItem().getItem().equals(Items.SKELETON_SKULL));
    }
}*/
