package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.tab.TabPrefix;
import com.rettichlp.unicacityaddon.controller.PlayerListController;
import net.labymod.api.Laby;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

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
@Implements(PlayerListController.class)
public class VersionedPlayerListController extends PlayerListController {

    @Inject
    public VersionedPlayerListController() {
    }

    @Override
    public boolean isKeyPlayerListDown() {
        return Minecraft.getMinecraft().gameSettings.keyBindPlayerList.isKeyDown();
    }

    @Override
    public void orderTabList(UnicacityAddon unicacityAddon) {
        ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        Collection<NetworkPlayerInfo> networkPlayerInfos = clientPacketListener != null ? clientPacketListener.getNetworkPlayerInfos() : Collections.emptyList();
        assert Minecraft.getMinecraft().world != null;
        Scoreboard scoreboard = Minecraft.getMinecraft().world.getScoreboard();

        // get teams or create if not present
        Map<TabPrefix, ScorePlayerTeam> tabPrefixScorePlayerTeamMap = getScorePlayerTeamMap(scoreboard);

        // add player to team
        networkPlayerInfos.stream()
                .filter(networkPlayerInfo -> networkPlayerInfo.profile() != null)
                .forEach(networkPlayerInfo -> {
                    String playerName = networkPlayerInfo.profile().getUsername();
                    if (!unicacityAddon.nameTagService().isMasked(playerName) && !unicacityAddon.nameTagService().isNoPush(playerName)) {
                        TabPrefix tabPrefix = TabPrefix.getTypeByDisplayName(unicacityAddon.utilService().text().legacy(networkPlayerInfo.displayName()));
                        ScorePlayerTeam playerTeam = tabPrefixScorePlayerTeamMap.get(tabPrefix);
                        scoreboard.addPlayerToTeam(playerName, playerTeam.getName());
                    }
                });
    }

    private Map<TabPrefix, ScorePlayerTeam> getScorePlayerTeamMap(Scoreboard scoreboard) {
        Map<TabPrefix, ScorePlayerTeam> scorePlayerTeamMap = new HashMap<>();
        scorePlayerTeamMap.put(TabPrefix.FBI_UC, scoreboard.getTeam("a_fbi_uc") != null ? scoreboard.getTeam("a_fbi_uc") : scoreboard.createTeam("a_fbi_uc"));
        scorePlayerTeamMap.put(TabPrefix.FBI, scoreboard.getTeam("b_fbi") != null ? scoreboard.getTeam("b_fbi") : scoreboard.createTeam("b_fbi"));
        scorePlayerTeamMap.put(TabPrefix.POLICE_UC, scoreboard.getTeam("c_police_uc") != null ? scoreboard.getTeam("c_police_uc") : scoreboard.createTeam("c_police_uc"));
        scorePlayerTeamMap.put(TabPrefix.POLICE, scoreboard.getTeam("d_police") != null ? scoreboard.getTeam("d_police") : scoreboard.createTeam("d_police"));
        scorePlayerTeamMap.put(TabPrefix.MEDIC_UC, scoreboard.getTeam("e_medic_uc") != null ? scoreboard.getTeam("e_medic_uc") : scoreboard.createTeam("e_medic_uc"));
        scorePlayerTeamMap.put(TabPrefix.MEDIC, scoreboard.getTeam("f_medic") != null ? scoreboard.getTeam("f_medic") : scoreboard.createTeam("f_medic"));
        scorePlayerTeamMap.put(TabPrefix.NEWS_UC, scoreboard.getTeam("g_news_uc") != null ? scoreboard.getTeam("g_news_uc") : scoreboard.createTeam("g_news_uc"));
        scorePlayerTeamMap.put(TabPrefix.NEWS, scoreboard.getTeam("h_news") != null ? scoreboard.getTeam("h_news") : scoreboard.createTeam("h_news"));
        scorePlayerTeamMap.put(TabPrefix.UC_DUTY, scoreboard.getTeam("i_uc_duty") != null ? scoreboard.getTeam("i_uc_duty") : scoreboard.createTeam("i_uc_duty"));
        scorePlayerTeamMap.put(TabPrefix.BUILDER, scoreboard.getTeam("j_builder") != null ? scoreboard.getTeam("j_builder") : scoreboard.createTeam("j_builder"));
        scorePlayerTeamMap.put(TabPrefix.REPORT, scoreboard.getTeam("k_report") != null ? scoreboard.getTeam("k_report") : scoreboard.createTeam("k_report"));
        scorePlayerTeamMap.put(TabPrefix.UC, scoreboard.getTeam("l_uc") != null ? scoreboard.getTeam("l_uc") : scoreboard.createTeam("l_uc"));
        scorePlayerTeamMap.put(TabPrefix.NONE, scoreboard.getTeam("m_player") != null ? scoreboard.getTeam("m_player") : scoreboard.createTeam("m_player"));
        return scorePlayerTeamMap;
    }
}
