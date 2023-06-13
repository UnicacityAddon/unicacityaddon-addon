package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
public class FactionInfoTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private FactionInfoTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static FactionInfoTag create(UnicacityAddon unicacityAddon) {
        return new FactionInfoTag(unicacityAddon);
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
        String nameTagSuffix = this.unicacityAddon.services().faction().getNameTagSuffix(faction);
        return !nameTagSuffix.isEmpty() ? RenderableComponent.of(Component.text(nameTagSuffix)) : null;
    }
}