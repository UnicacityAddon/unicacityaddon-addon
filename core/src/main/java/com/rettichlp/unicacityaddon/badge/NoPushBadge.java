package com.rettichlp.unicacityaddon.badge;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCBadge;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.entity.player.badge.PositionType;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
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
        Icon icon = this.unicacityAddon.utilService().noPushIcon();
        icon.render(stack, x + 2.0F, y, 8.0F, false, ColorCode.GRAY.getColor().getRGB());
    }

    @Override
    public boolean isVisible(NetworkPlayerInfo player) {
        return this.unicacityAddon.utilService().isUnicacity() && Optional.ofNullable(player.getTeam())
                .map(ScoreboardTeam::getTeamName)
                .map(s -> s.equals("nopush"))
                .orElse(false);
    }
}