package com.rettichlp.unicacityaddon.base.nametags;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author RettichLP
 */
public class HouseBanTag extends NameTag {

    private final FloatVector3 HOSPITAL = new FloatVector3(265, 69, 215);
    private final FloatVector3 FIRE_DEPARTMENT = new FloatVector3(-120, 69, -260);

    private final UnicacityAddon unicacityAddon;

    private HouseBanTag(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public static HouseBanTag create(UnicacityAddon unicacityAddon) {
        return new HouseBanTag(unicacityAddon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        UnicacityAddonConfiguration unicacityAddonConfiguration = this.unicacityAddon.configuration();
        boolean isEnabled = unicacityAddonConfiguration.enabled().get() && unicacityAddonConfiguration.nameTagSetting().houseBan().get();

        Optional<Player> playerOptional = this.unicacityAddon.player().getWorld().getPlayers().stream()
                .filter(p -> p.gameUser().getUniqueId().equals(this.entity.getUniqueId()))
                .findFirst();

        return isEnabled && playerOptional.isPresent() && (playerOptional.get().position().distance(HOSPITAL) < 80 || playerOptional.get().position().distance(FIRE_DEPARTMENT) < 30)
                ? getComponent(playerOptional.get().getName())
                : null;
    }

    @Override
    public float getScale() {
        return 0.6F;
    }

    private RenderableComponent getComponent(String playerName) {
        Component component = Message.getBuilder()
                .of("Hausverbot").color(ColorCode.RED).bold().advance()
                .createComponent();

        boolean hasHouseBan = this.unicacityAddon.api().getHouseBanList().stream()
                .anyMatch(houseBanEntry -> houseBanEntry.getName().equals(playerName));

        return hasHouseBan ? RenderableComponent.of(component) : null;
    }
}