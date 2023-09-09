package com.rettichlp.unicacityaddon.badge;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCBadge;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;

import java.util.Optional;

/**
 * @author RettichLP
 */
@UCBadge(name = "VipBadge")
public class VipBadge extends BadgeRenderer {

    private final UnicacityAddon unicacityAddon;

    public VipBadge(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void render(Stack stack, float x, float y, NetworkPlayerInfo player) {
        Icon icon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/badges.png"), 1, 0);
        icon.render(stack, x + 3, y + 1, 6);
    }

    @Override
    public boolean isVisible(NetworkPlayerInfo player) {
        return Optional.ofNullable(player.profile().getUsername())
                .map(s -> AddonGroup.VIP.getMemberList().contains(s))
                .orElse(false);
    }
}