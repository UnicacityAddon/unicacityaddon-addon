package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
public class DutyTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private DutyTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().duty().get();

        return isEnabled ? this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst()
                .map(player -> getComponent(player.getName())).orElse(null) : null;
    }

    private RenderableComponent getComponent(String playerName) {
        Component component = Message.getBuilder().space()
                .of("●").color(ColorCode.GREEN).advance().space()
                .createComponent();

        return this.unicacityAddon.services().faction().checkPlayerDuty(playerName) ? RenderableComponent.of(component) : null;
    }

    public static DutyTag create(UnicacityAddon unicacityAddon) {
        return new DutyTag(unicacityAddon);
    }
}