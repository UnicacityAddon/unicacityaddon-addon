package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class NameTagEventHandler {

    private static int tick;

    @SubscribeEvent
    public void onRenderNameTag(PlayerEvent.NameFormat e) {
        String playerName = e.getUsername();
        String houseban = getHouseban(playerName);
        String prefix = getPrefix(playerName);
        String suffix = getSuffix(playerName);
        e.setDisplayname(houseban + prefix + playerName + suffix);
        System.out.println("NameFormat EVENT: " + playerName + " -> " + e.getEntityPlayer().getCustomNameTag());
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (e.phase != TickEvent.Phase.START) return;
        if (UnicacityAddon.MINECRAFT.world == null) return;
        if (tick++ != 20) return;

        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);

        items.forEach(entityItem -> {
            String name = entityItem.getCustomNameTag();
            String playerName = name.substring(3);

            if (!FactionHandler.getPlayerFactionMap().containsKey(name.substring(3))) return;
            if (name.contains("◤")) return; // already edited

            String prefix = getPrefix(playerName);
            String suffix = getSuffix(playerName);

            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + suffix);
                return;
            }

            entityItem.setCustomNameTag(ColorCode.GRAY.getCode() + prefix + "✟" + playerName + suffix);
        });

        tick = 0;
    }

    private String getHouseban(String playerName) {
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

    private String getPrefix(String playerName) {
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

    private String getSuffix(String playerName) {
        StringBuilder suffix = new StringBuilder();
        suffix.append(FormattingCode.RESET.getCode());

        if (FactionHandler.getPlayerFactionMap().containsKey(playerName)) {
            Faction targetPlayerFaction = FactionHandler.getPlayerFactionMap().get(playerName);
            if (ConfigElements.getNameTagFactionSuffix()) suffix.append(" ").append(targetPlayerFaction.getNameTagSuffix());
        }

        return suffix.toString();
    }
}