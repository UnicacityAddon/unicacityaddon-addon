package com.rettichlp.unicacityaddon.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@UCNameTag(name = "unicacityaddon_outlawtag", positionType = PositionType.LEFT_TO_NAME)
public class OutlawTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    public OutlawTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().specific().enabled().get();

        return isEnabled ? this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst()
                .map(player -> getComponent(player.getName())).orElse(null) : null;
    }

    private RenderableComponent getComponent(String playerName) {
        Component component = Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("V").color(ColorCode.RED).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance()
                .createComponent();

        boolean isOutlaw = this.unicacityAddon.services().nametag().getBlacklistPlayerMap().getOrDefault(playerName, false);
        return isOutlaw ? RenderableComponent.of(component) : null;
    }
}