package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.FormattingCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Objects;

/**
 * @author RettichLP
 */
@UCEvent
public class NameTagEventHandler {

    /**
     * Quote: "Wenn ich gleich nicht mehr antworte, einfach laut meinen Namen sagen." - Lou, 02.10.2022
     * "Fällst du dann aus dem Bett?" - RettichLP und Ullrich, 02.10.2022
     */
    @SubscribeEvent
    public void onRenderNameTag(PlayerEvent.NameFormat e) {
        if (!UnicacityAddon.isUnicacity()) return;
        EntityPlayer entityPlayer = e.getEntityPlayer();
        String playerUsername = e.getUsername();
        String displayName = ScorePlayerTeam.formatPlayerName(entityPlayer.getTeam(), playerUsername);
        if (displayName.contains(FormattingCode.OBFUSCATED.getCode())) return;

        String houseBan = getHouseBan(playerUsername);
        String outlaw = getOutlaw(playerUsername);
        String prefix = getPrefix(playerUsername, false);
        String factionInfo = getFactionInfo(playerUsername);
        String duty = getDuty(playerUsername);

        e.setDisplayname(houseBan + outlaw + prefix + playerUsername + factionInfo + duty);
    }

    private String getHouseBan(String playerName) {
        StringBuilder houseBan = new StringBuilder();
        houseBan.append(FormattingCode.RESET.getCode());

        if (ConfigElements.getNameTagHouseBan()) {
            if (Syncer.HOUSEBANENTRYLIST.stream().anyMatch(houseBanEntry -> houseBanEntry.getName().equals(playerName)))
                houseBan.append(Message.getBuilder()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("HV").color(ColorCode.RED).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
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

    public static String getPrefix(String playerName, boolean isCorpse) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());
        if (isCorpse) prefix.append(ColorCode.GRAY.getCode());

        if (Syncer.PLAYERFACTIONMAP.containsKey(playerName)) {
            Faction targetPlayerFaction = Syncer.PLAYERFACTIONMAP.get(playerName);

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

        return prefix.toString();
    }

    public static String getFactionInfo(String playerName) {
        StringBuilder suffix = new StringBuilder();
        suffix.append(FormattingCode.RESET.getCode());

        if (Syncer.PLAYERFACTIONMAP.containsKey(playerName)) {
            Faction targetPlayerFaction = Syncer.PLAYERFACTIONMAP.get(playerName);
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