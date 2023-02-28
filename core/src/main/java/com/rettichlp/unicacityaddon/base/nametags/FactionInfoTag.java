package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
        if (this.unicacityAddon.configuration().nameTagSetting().factionInfo().get()) {
            Optional<Player> playerOptional = UnicacityAddon.PLAYER.getWorld().getPlayers().stream()
                    .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                return getComponent(playerOptional.get().getName());
            }
        }

        return null;
    }

    private RenderableComponent getComponent(String playerName) {
        Faction targetFaction = Faction.NULL;

        if (FactionManager.getInstance().getFactionData().containsKey(playerName))
            targetFaction = FactionManager.getInstance().getFactionData().get(playerName).getKey();

        return !targetFaction.equals(Faction.NULL) ? RenderableComponent.of(Component.text(targetFaction.getNameTagSuffix())) : null;
    }
}