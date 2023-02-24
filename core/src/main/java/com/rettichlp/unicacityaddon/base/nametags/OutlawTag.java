package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.blacklist.BlacklistEventHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OutlawTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private OutlawTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static OutlawTag create(UnicacityAddon unicacityAddon) {
        return new OutlawTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (unicacityAddon.configuration().nameTagSetting().specificNameTagSetting().enabled().get()) {
            Optional<Player> playerOptional = UnicacityAddon.MINECRAFT.clientWorld().getPlayers().stream()
                    .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                    .findFirst();

            if (playerOptional.isPresent()) {
                return getComponent(playerOptional.get().getName());
            }
        }

        return null;
    }

    private RenderableComponent getComponent(String playerName) {
        Component component = Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("V").color(ColorCode.RED).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance()
                .createComponent();

        boolean isOutlaw = BlacklistEventHandler.BLACKLIST_MAP.containsKey(playerName) && BlacklistEventHandler.BLACKLIST_MAP.get(playerName);

        return isOutlaw ? RenderableComponent.of(component) : null;
    }
}