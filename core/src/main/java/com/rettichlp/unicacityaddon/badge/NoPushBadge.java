package com.rettichlp.unicacityaddon.badge;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.tablist.TabListConfiguration;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCBadge;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.scoreboard.ScoreboardTeam;

import java.util.Optional;

/**
 * @author RettichLP
 */
@UCBadge(name = "NoPushBadge")
public class NoPushBadge implements BadgeRenderer {

    private final UnicacityAddon unicacityAddon;

    public NoPushBadge(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void render(Stack stack, float x, float y, NetworkPlayerInfo player) {
        Icon icon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/badges.png"), 0, 0);
        icon.render(stack, x + 3, y + 1, 6);
    }

    @Override
    public boolean isVisible(NetworkPlayerInfo player) {
        TabListConfiguration tabListConfiguration = this.unicacityAddon.configuration().tablist();
        return this.unicacityAddon.utilService().isUnicacity() && tabListConfiguration.enabled().get() && tabListConfiguration.afk().get() && Optional.ofNullable(player.getTeam())
                .map(ScoreboardTeam::getTeamName)
                .map(s -> s.equals("nopush"))
                .orElse(false);
    }
}