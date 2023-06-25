package com.rettichlp.unicacityaddon.v1_19_4;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.tab.TabPrefix;
import com.rettichlp.unicacityaddon.controller.TabListController;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RettichLP
 */
@Singleton
@Implements(TabListController.class)
public class VersionedTabListController extends TabListController {

    @Inject
    public VersionedTabListController() {
    }

    @Override
    public void orderTabList(UnicacityAddon unicacityAddon) {
        ClientPacketListener clientPacketListener = unicacityAddon.labyAPI().minecraft().getClientPacketListener();
        Collection<NetworkPlayerInfo> networkPlayerInfos = clientPacketListener != null ? clientPacketListener.getNetworkPlayerInfos() : Collections.emptyList();
        assert Minecraft.getInstance().level != null;
        Scoreboard scoreboard = Minecraft.getInstance().level.getScoreboard();

        // get teams or create if not present
        Map<TabPrefix, PlayerTeam> tabPrefixScorePlayerTeamMap = getScorePlayerTeamMap(scoreboard);

        // add default player team (m_player)
        networkPlayerInfos.stream()
                .filter(networkPlayerInfo -> networkPlayerInfo.profile() != null)
                .filter(networkPlayerInfo -> networkPlayerInfo.getTeam() == null || (!networkPlayerInfo.getTeam().getTeamName().equals("nopush") && !networkPlayerInfo.getTeam().getTeamName().equals("masked")))
                .map(networkPlayerInfo -> networkPlayerInfo.profile().getUsername())
                .forEach(s -> scoreboard.addPlayerToTeam(s, tabPrefixScorePlayerTeamMap.get(TabPrefix.NONE)));

        // add formatted player teams
        networkPlayerInfos.stream()
                .filter(networkPlayerInfo -> networkPlayerInfo.displayName() != null)
                .filter(networkPlayerInfo -> networkPlayerInfo.getTeam() == null || (!networkPlayerInfo.getTeam().getTeamName().equals("nopush") && !networkPlayerInfo.getTeam().getTeamName().equals("masked")))
                .forEach(networkPlayerInfo -> {
                    String displayName = unicacityAddon.services().util().text().legacy(networkPlayerInfo.displayName());
                    TabPrefix tabPrefix = TabPrefix.getTypeByDisplayName(displayName);
                    PlayerTeam playerTeam = tabPrefixScorePlayerTeamMap.get(tabPrefix);
                    scoreboard.addPlayerToTeam(networkPlayerInfo.profile().getUsername(), playerTeam);
                });
    }

    private Map<TabPrefix, PlayerTeam> getScorePlayerTeamMap(Scoreboard scoreboard) {
        Map<TabPrefix, PlayerTeam> scorePlayerTeamMap = new HashMap<>();
        scorePlayerTeamMap.put(TabPrefix.FBI_UC, scoreboard.getPlayerTeam("a_fbi_uc") != null ? scoreboard.getPlayerTeam("a_fbi_uc") : scoreboard.addPlayerTeam("a_fbi_uc"));
        scorePlayerTeamMap.put(TabPrefix.FBI, scoreboard.getPlayerTeam("b_fbi") != null ? scoreboard.getPlayerTeam("b_fbi") : scoreboard.addPlayerTeam("b_fbi"));
        scorePlayerTeamMap.put(TabPrefix.POLICE_UC, scoreboard.getPlayerTeam("c_police_uc") != null ? scoreboard.getPlayerTeam("c_police_uc") : scoreboard.addPlayerTeam("c_police_uc"));
        scorePlayerTeamMap.put(TabPrefix.POLICE, scoreboard.getPlayerTeam("d_police") != null ? scoreboard.getPlayerTeam("d_police") : scoreboard.addPlayerTeam("d_police"));
        scorePlayerTeamMap.put(TabPrefix.MEDIC_UC, scoreboard.getPlayerTeam("e_medic_uc") != null ? scoreboard.getPlayerTeam("e_medic_uc") : scoreboard.addPlayerTeam("e_medic_uc"));
        scorePlayerTeamMap.put(TabPrefix.MEDIC, scoreboard.getPlayerTeam("f_medic") != null ? scoreboard.getPlayerTeam("f_medic") : scoreboard.addPlayerTeam("f_medic"));
        scorePlayerTeamMap.put(TabPrefix.NEWS_UC, scoreboard.getPlayerTeam("g_news_uc") != null ? scoreboard.getPlayerTeam("g_news_uc") : scoreboard.addPlayerTeam("g_news_uc"));
        scorePlayerTeamMap.put(TabPrefix.NEWS, scoreboard.getPlayerTeam("h_news") != null ? scoreboard.getPlayerTeam("h_news") : scoreboard.addPlayerTeam("h_news"));
        scorePlayerTeamMap.put(TabPrefix.UC_DUTY, scoreboard.getPlayerTeam("i_uc_duty") != null ? scoreboard.getPlayerTeam("i_uc_duty") : scoreboard.addPlayerTeam("i_uc_duty"));
        scorePlayerTeamMap.put(TabPrefix.BUILDER, scoreboard.getPlayerTeam("j_builder") != null ? scoreboard.getPlayerTeam("j_builder") : scoreboard.addPlayerTeam("j_builder"));
        scorePlayerTeamMap.put(TabPrefix.REPORT, scoreboard.getPlayerTeam("k_report") != null ? scoreboard.getPlayerTeam("k_report") : scoreboard.addPlayerTeam("k_report"));
        scorePlayerTeamMap.put(TabPrefix.UC, scoreboard.getPlayerTeam("l_uc") != null ? scoreboard.getPlayerTeam("l_uc") : scoreboard.addPlayerTeam("l_uc"));
        scorePlayerTeamMap.put(TabPrefix.NONE, scoreboard.getPlayerTeam("m_player") != null ? scoreboard.getPlayerTeam("m_player") : scoreboard.addPlayerTeam("m_player"));
        return scorePlayerTeamMap;
    }
}
