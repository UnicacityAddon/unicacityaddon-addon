package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.listener.faction.ContractListener;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistListener;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class NameTagListener {

    private final UnicacityAddon unicacityAddon;

    public NameTagListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "Wenn ich gleich nicht mehr antworte, einfach laut meinen Namen sagen." - Lou, 02.10.2022
     * "Fällst du dann aus dem Bett?" - RettichLP und Ullrich, 02.10.2022
     */
    @Subscribe
    public void onPlayerNameTagRender(PlayerNameTagRenderEvent e) {
        if (e.context().equals(PlayerNameTagRenderEvent.Context.PLAYER_RENDER)) {
            NetworkPlayerInfo networkPlayerInfo = e.playerInfo();

            if (e.nameTag().toString().contains("§k") || e.nameTag().toString().contains("&k"))
                UnicacityAddon.debug("NAMETAG: " + e.nameTag() + " " + networkPlayerInfo.profile().getUsername());

            if (networkPlayerInfo != null && !e.nameTag().style().isDecorationSet(TextDecoration.OBFUSCATED)) {
                String playerName = networkPlayerInfo.profile().getUsername();
                String prefix = getPrefix(playerName, false);

                if (!prefix.equals(FormattingCode.RESET.getCode())) {
                    e.setNameTag(Message.getBuilder().add(prefix + playerName).createComponent());
                }
            }
        }
    }

    public static String getPrefix(String playerName, boolean isCorpse) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());
        if (isCorpse)
            prefix.append(ColorCode.GRAY.getCode());

        if (APIConverter.PLAYERFACTIONMAP.containsKey(playerName)) {
            Faction targetPlayerFaction = APIConverter.PLAYERFACTIONMAP.getOrDefault(playerName, Faction.NULL);

            FactionNameTagSetting factionNameTagSetting = UnicacityAddon.ADDON.configuration().nameTagSetting().factionNameTagSetting();
            if (factionNameTagSetting.enabled().get()) {
                if (targetPlayerFaction.equals(UnicacityAddon.PLAYER.getFaction()))
                    prefix.append(factionNameTagSetting.color().getOrDefault(ColorCode.BLUE).getCode());
            }

            AllianceFactionNameTagSetting allianceFactionNameTagSetting = UnicacityAddon.ADDON.configuration().nameTagSetting().allianceFactionNameTagSetting();
            if (allianceFactionNameTagSetting.enabled().get()) {
                ColorCode allianceColor = allianceFactionNameTagSetting.color().getOrDefault(ColorCode.DARK_PURPLE);
                Faction allianceFaction1 = allianceFactionNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction allianceFaction2 = allianceFactionNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(allianceFaction1) || targetPlayerFaction.equals(allianceFaction2))
                    prefix.append(allianceColor.getCode());
            }

            StreetwarNameTagSetting streetwarNameTagSetting = UnicacityAddon.ADDON.configuration().nameTagSetting().streetwarNameTagSetting();
            if (streetwarNameTagSetting.enabled().get()) {
                ColorCode streetwarColor = streetwarNameTagSetting.color().getOrDefault(ColorCode.RED);
                Faction streetwarFaction1 = streetwarNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction streetwarFaction2 = streetwarNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(streetwarFaction1) || targetPlayerFaction.equals(streetwarFaction2))
                    prefix.append(streetwarColor.getCode());
            }
        }

        SpecificNameTagSetting specificNameTagSetting = UnicacityAddon.ADDON.configuration().nameTagSetting().specificNameTagSetting();
        if (specificNameTagSetting.enabled().get()) {
            WantedListener.Wanted wanted = WantedListener.WANTED_MAP.get(playerName);
            if (wanted != null) {
                int amount = wanted.getAmount();
                ColorCode color;

                if (amount == 1)
                    color = ColorCode.DARK_GREEN;
                else if (amount < 15)
                    color = ColorCode.GREEN;
                else if (amount < 25)
                    color = ColorCode.YELLOW;
                else if (amount < 50)
                    color = ColorCode.GOLD;
                else if (amount < 60)
                    color = ColorCode.RED;
                else
                    color = ColorCode.DARK_RED;

                prefix.append(color.getCode());
            }

            if (BlacklistListener.BLACKLIST_MAP.get(playerName) != null)
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());

            if (ContractListener.CONTRACT_LIST.contains(playerName))
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());
        }

        return prefix.toString();
    }
}