package com.rettichlp.unicacityaddon.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@UCNameTag(name = "unicacityaddon_factioninfotag", positionType = PositionType.RIGHT_TO_NAME, priority = 10)
public class FactionInfoTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    public FactionInfoTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().info().get();

        return isEnabled ? this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst()
                .map(player -> getComponent(player.getName())).orElse(null) : null;
    }

    private RenderableComponent getComponent(String playerName) {
        Faction faction = this.unicacityAddon.api().getPlayerFactionMap().getOrDefault(playerName, Faction.NULL);
        String nameTagSuffix = this.unicacityAddon.factionService().getNameTagSuffix(faction);
        return !nameTagSuffix.isEmpty() ? RenderableComponent.of(Component.text(nameTagSuffix)) : null;
    }
}