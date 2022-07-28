package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import java.util.List;
import java.util.Objects;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NameTagEventHandler {

    private static int tick;

    @SubscribeEvent
    public void onRenderNameTag(PlayerEvent.NameFormat e) {
        String playerName = e.getUsername();
        String houseban = getHouseban(playerName);
        String outlaw = getOutlaw(playerName);
        String prefix = getPrefix(playerName);
        String factionInfo = getFactionInfo(playerName);
        String duty = getDuty(playerName);
        e.setDisplayname(houseban + outlaw + prefix + playerName + factionInfo + duty);
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
            String factionInfo = getFactionInfo(playerName);

            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
                return;
            }

            entityItem.setCustomNameTag(ColorCode.GRAY.getCode() + prefix + "✟" + playerName + factionInfo);
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

    private String getOutlaw(String playerName) {
        StringBuilder outlaw = new StringBuilder();
        outlaw.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagBlacklist()) {
            if (BlacklistEventHandler.BLACKLIST_MAP.containsKey(playerName)) {
                if (BlacklistEventHandler.BLACKLIST_MAP.get(playerName)) outlaw.append(Message.getBuilder()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("V").color(ColorCode.RED).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .add(FormattingCode.RESET.getCode())
                        .create());
            }
        }

        return outlaw.toString();
    }

    private String getPrefix(String playerName) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagWPS()) {
            WantedEventHandler.Wanted wanted = WantedEventHandler.WANTED_MAP.get(playerName);
            if (wanted != null) {
                int amount = wanted.getAmount();
                ColorCode color;

                if (amount == 1) color = ColorCode.DARK_GREEN;
                else if (amount < 15) color = ColorCode.GREEN;
                else if (amount < 25) color = ColorCode.YELLOW;
                else if (amount < 50) color = ColorCode.GOLD;
                else if (amount < 60) color = ColorCode.RED;
                else color = ColorCode.DARK_RED;

                prefix.append(color.getCode());
            }
        }

        if (ConfigElements.getNameTagBlacklist())
            if (BlacklistEventHandler.BLACKLIST_MAP.get(playerName) != null) prefix.append(ColorCode.DARK_RED.getCode());

        if (ConfigElements.getNameTagContract())
            if (ContractEventHandler.CONTRACT_LIST.contains(playerName)) prefix.append(ColorCode.DARK_RED.getCode());

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

    private String getFactionInfo(String playerName) {
        StringBuilder suffix = new StringBuilder();
        suffix.append(FormattingCode.RESET.getCode());

        if (FactionHandler.getPlayerFactionMap().containsKey(playerName)) {
            Faction targetPlayerFaction = FactionHandler.getPlayerFactionMap().get(playerName);
            if (ConfigElements.getNameTagFactionSuffix()) suffix.append(" ").append(targetPlayerFaction.getNameTagSuffix());
        }

        return suffix.toString();
    }

    private String getDuty(String playerName) {
        StringBuilder duty = new StringBuilder();
        duty.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagDuty()) {
            if (FactionHandler.checkPlayerDuty(playerName)) duty.append(Message.getBuilder()
                    .of(" ● ").color(ColorCode.GREEN).advance()
                    .add(FormattingCode.RESET.getCode())
                    .create());
        }

        return duty.toString();
    }

    public static void refreshAllDisplayNames() {
        if (UnicacityAddon.MINECRAFT.world == null) return;
        List<EntityPlayer> items = UnicacityAddon.MINECRAFT.world.getPlayers(EntityPlayer.class, Objects::nonNull);
        items.forEach(EntityPlayer::refreshDisplayName);
    }
}