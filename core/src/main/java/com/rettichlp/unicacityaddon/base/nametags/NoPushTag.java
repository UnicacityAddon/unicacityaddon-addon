package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author RettichLP
 */
public class NoPushTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private NoPushTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static NoPushTag create(UnicacityAddon unicacityAddon) {
        return new NoPushTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (this.unicacityAddon.isUnicacity() && this.unicacityAddon.configuration().nameTagSetting().noPushInfo().get()) {
            Optional<Player> playerOptional = this.unicacityAddon.player().getWorld().getPlayers().stream()
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
        return 0.5F;
    }

    private RenderableComponent getComponent(String playerName) {
        boolean isNoPush = this.unicacityAddon.nametagService().getNoPushPlayerList().contains(playerName);
        boolean isADuty = this.unicacityAddon.nametagService().isAdminDuty(playerName);

        return isNoPush ? RenderableComponent.of(Message.getBuilder()
                .of(isADuty ? "ADMIN DIENST" : "AFK").color(ColorCode.GOLD).bold().advance()
                .createComponent()) : null;
    }
}