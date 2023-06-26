package com.rettichlp.unicacityaddon.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
import com.rettichlp.unicacityaddon.base.services.NameTagService;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@UCNameTag(name = "unicacityaddon_nopushtag", positionType = PositionType.ABOVE_NAME, priority = 20)
public class NoPushTag extends NameTag {

    private final RenderableComponent ADMIN_DUTY_COMPONENT = RenderableComponent.of(Message.getBuilder()
            .of("ADMIN DIENST").color(ColorCode.GOLD).bold().advance()
            .createComponent());

    private final RenderableComponent AFK_COMPONENT = RenderableComponent.of(Message.getBuilder()
            .of("AFK").color(ColorCode.GOLD).bold().advance()
            .createComponent());

    private final UnicacityAddon unicacityAddon;

    public NoPushTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().afk().get();

        return isEnabled ? this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst()
                .map(player -> getComponent(player.getName())).orElse(null) : null;
    }

    @Override
    public float getScale() {
        return 0.5F;
    }

    private RenderableComponent getComponent(String playerName) {
        NameTagService nameTagService = this.unicacityAddon.services().nametag();
        if (nameTagService.isAdminDuty(playerName)) {
            return ADMIN_DUTY_COMPONENT;
        } else if (nameTagService.getNoPushPlayerList().contains(playerName)) {
            return AFK_COMPONENT;
        } else {
            return null;
        }
    }
}