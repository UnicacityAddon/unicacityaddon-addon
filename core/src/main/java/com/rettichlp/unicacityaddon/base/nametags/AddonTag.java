package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author RettichLP
 */
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
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().team().get();

        return isEnabled ? this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst()
                .map(player -> getComponent(player.getName())).orElse(null) : null;
    }

    @Override
    public float getScale() {
        return 0.6F;
    }

    private RenderableComponent getComponent(String playerName) {
        List<AddonGroup> addonGroupList = AddonGroup.getAddonGroupsOfPlayer(playerName);

        return !addonGroupList.isEmpty() && addonGroupList.get(0).getColorCode() != null ? RenderableComponent.of(Message.getBuilder()
                .of("UCAddon").color(ColorCode.WHITE).bold().advance().space()
                .of(addonGroupList.get(0).getDisplayName()).color(addonGroupList.get(0).getColorCode()).advance()
                .createComponent()) : null;
    }
}