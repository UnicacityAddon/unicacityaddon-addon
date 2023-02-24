package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AddonTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private AddonTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static AddonTag create(UnicacityAddon unicacityAddon) {
        return new AddonTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (unicacityAddon.configuration().addonTag().get()) {
            Optional<Player> playerOptional = UnicacityAddon.MINECRAFT.clientWorld().getPlayers().stream()
                    .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                return getComponent(playerOptional.get().getName());
            }
        }

        return null;
    }

    @Override
    public float getScale() {
        return 0.65F;
    }

    private RenderableComponent getComponent(String playerName) {
        List<AddonGroup> addonGroupList = AddonGroup.getAddonGroupsOfPlayer(playerName);

        return !addonGroupList.isEmpty() ? RenderableComponent.of(Message.getBuilder()
                .of(addonGroupList.get(0).getDisplayName()).color(addonGroupList.get(0).getColorCode()).advance()
                .createComponent()) : null;
    }
}