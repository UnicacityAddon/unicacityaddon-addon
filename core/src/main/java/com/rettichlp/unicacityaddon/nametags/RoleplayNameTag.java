package com.rettichlp.unicacityaddon.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.RoleplayName;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCNameTag;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * @author RettichLP
 */
@UCNameTag(name = "unicacityaddon_roleplaynametag", positionType = PositionType.BELOW_NAME, priority = 20)
public class RoleplayNameTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    public RoleplayNameTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nametag().roleplay().get();

        return isEnabled ? getComponent(this.entity.getUniqueId()) : null;
    }

    @Override
    public float getScale() {
        return 0.6F;
    }

    private RenderableComponent getComponent(UUID minecraftUuid) {
        Optional<RoleplayName> optionalRoleplayName = this.unicacityAddon.api().getRoleplayNameList().stream()
                .filter(roleplayName -> roleplayName.getMinecraftUuid().equals(minecraftUuid.toString().replace("-", "")))
                .findFirst();

        return optionalRoleplayName
                .map(RoleplayName::getRoleplayName)
                .filter(s -> !s.startsWith("_"))
                .map(s -> RenderableComponent.of(Message.getBuilder()
                        .of(s).advance()
                        .createComponent()))
                .orElse(null);
    }
}