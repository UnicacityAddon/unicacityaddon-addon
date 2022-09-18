package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Objects;

/**
 * @author RettichLP
 */
@UCEvent
public class NameTagEventHandler {

    private static int tick;
    private static int syncTick;

    @SubscribeEvent
    public void onRenderNameTag(PlayerEvent.NameFormat e) {
        if (!UnicacityAddon.isUnicacity()) return;
        String playerName = e.getUsername();

        String displayName = ScorePlayerTeam.formatPlayerName(e.getEntityPlayer().getTeam(), playerName);
        if (displayName.contains(FormattingCode.OBFUSCATED.getCode())) return;

        String houseBan = getHouseBan(playerName);
        String outlaw = getOutlaw(playerName);
        String prefix = getPrefix(playerName, false);
        String factionInfo = getFactionInfo(playerName);
        String duty = getDuty(playerName);
        e.setDisplayname(houseBan + outlaw + prefix + playerName + factionInfo + duty);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        if (UnicacityAddon.MINECRAFT.world == null) return;
        if (tick++ != 20) return;

        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        items.forEach(entityItem -> {
            String name = entityItem.getCustomNameTag();
            String playerName = name.substring(3);

            if (!FactionHandler.getPlayerFactionMap().containsKey(name.substring(3))) return;
            if (name.contains("◤")) return; // already edited

            String prefix = getPrefix(playerName, true);
            String factionInfo = getFactionInfo(playerName);

            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
                return;
            }

            entityItem.setCustomNameTag(prefix + "✟" + playerName + factionInfo);
        });

        tick = 0;
    }

    @SubscribeEvent
    public void onSyncDisplayName(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        if (UnicacityAddon.MINECRAFT.world == null) return;

        String intervalString = ConfigElements.getRefreshDisplayNamesInterval();
        int interval = 5 * 20;
        if (MathUtils.isInteger(intervalString)) {
            interval = Integer.parseInt(intervalString) * 20;
        }

        if (syncTick++ < interval) return;
        refreshAllDisplayNames();
        syncTick = 0;
    }

    private String getHouseBan(String playerName) {
        StringBuilder houseBan = new StringBuilder();
        houseBan.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagHouseBan()) {
            if (FactionHandler.checkPlayerHouseBan(playerName)) houseBan.append(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("HV").color(ColorCode.RED).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance()
                    .add(FormattingCode.RESET.getCode())
                    .create());
        }

        return houseBan.toString();
    }

    private String getOutlaw(String playerName) {
        StringBuilder outlaw = new StringBuilder();
        outlaw.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagFactionSpecific()) {
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

    private String getPrefix(String playerName, boolean isCorpse) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());
        if (isCorpse) prefix.append(ColorCode.GRAY.getCode());

        if (ConfigElements.getNameTagFactionSpecific()) {
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

            if (BlacklistEventHandler.BLACKLIST_MAP.get(playerName) != null)
                prefix.append(ConfigElements.getNameTagFactionSpecificColor().getCode());

            if (ContractEventHandler.CONTRACT_LIST.contains(playerName))
                prefix.append(ConfigElements.getNameTagFactionSpecificColor().getCode());
        }

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
            if (ConfigElements.getNameTagFactionSuffix())
                suffix.append(" ").append(targetPlayerFaction.getNameTagSuffix());
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