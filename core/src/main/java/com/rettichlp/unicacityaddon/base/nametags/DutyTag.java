package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author RettichLP
 */
public class DutyTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private DutyTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static DutyTag create(UnicacityAddon unicacityAddon) {
        return new DutyTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (this.unicacityAddon.configuration().nameTagSetting().duty().get()) {
            Optional<Player> playerOptional = this.unicacityAddon.player().getWorld().getPlayers().stream()
                    .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                return getComponent(playerOptional.get().getName());
            }
        }

        return null;
    }

    private RenderableComponent getComponent(String playerName) {
        Component component = Message.getBuilder().space()
                .of("●").color(ColorCode.GREEN).advance().space()
                .createComponent();

        return this.unicacityAddon.factionManager().checkPlayerDuty(playerName) ? RenderableComponent.of(component) : null;
    }
}