package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;

import java.util.Optional;

import static com.rettichlp.unicacityaddon.base.services.utils.MathUtils.HEART_DECIMAL_FORMAT;

/**
 * @author RettichLP
 */
public class HearthHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;

    private final UnicacityAddon unicacityAddon;

    public HearthHudWidget(UnicacityAddon unicacityAddon) {
        super("hearth");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Herzen", "nicht geladen");
        this.setIcon(this.unicacityAddon.utilService().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        AddonPlayer p = this.unicacityAddon.player();

        boolean itemAllowed = Optional.ofNullable(p.getPlayer())
                .map(LivingEntity::getMainHandItemStack)
                .map(ItemStack::getDisplayName)
                .map(component -> this.unicacityAddon.utilService().text().plain(component)).stream()
                .anyMatch(s -> s.equalsIgnoreCase("Baseballschläger")
                        || s.equalsIgnoreCase("Einsatzschild")
                        || s.equalsIgnoreCase("Messer"));

        return p.getWeaponInMainHand() != null || itemAllowed;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        Float health = this.unicacityAddon.player().getHealth();
        if (e.isIngame() && e.isPhase(UnicacityAddonTickEvent.Phase.TICK) && health != null) {
            this.textLine.updateAndFlush(HEART_DECIMAL_FORMAT.format(health / 2) + ColorCode.RED.getCode() + "❤");
        }
    }
}