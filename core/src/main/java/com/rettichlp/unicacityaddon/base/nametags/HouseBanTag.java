package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HouseBanTag extends NameTag {

    private final UnicacityAddon unicacityAddon;

    private HouseBanTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static HouseBanTag create(UnicacityAddon unicacityAddon) {
        return new HouseBanTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (this.unicacityAddon.configuration().nameTagSetting().houseBan().get()) {
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
        Component component = Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("HV").color(ColorCode.RED).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance()
                .createComponent();

        boolean hasHouseBan = APIConverter.HOUSEBANENTRYLIST.stream()
                .anyMatch(houseBanEntry -> houseBanEntry.getName().equals(playerName));

        return hasHouseBan ? RenderableComponent.of(component) : null;
    }
}