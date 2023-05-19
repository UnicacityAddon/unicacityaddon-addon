package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.event.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class PlantHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine fertilize;
    private TextLine water;

    private final UnicacityAddon unicacityAddon;

    public PlantHudWidget(String id, UnicacityAddon unicacityAddon) {
        super(id);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.fertilize = super.createLine("Düngen", "");
        this.water = super.createLine("Wässern", "");
        this.setIcon(this.unicacityAddon.utils().icon());
    }

    @Override
    public boolean isVisibleInGame() {
        boolean showFertilize = System.currentTimeMillis() - this.unicacityAddon.services().fileService().data().getPlantFertilizeTime() < TimeUnit.MINUTES.toMillis(90);
        boolean showWater = System.currentTimeMillis() - this.unicacityAddon.services().fileService().data().getPlantWaterTime() < TimeUnit.MINUTES.toMillis(70);
        return showFertilize || showWater;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            long timeSinceLastFertilizeInteraction = System.currentTimeMillis() - this.unicacityAddon.services().fileService().data().getPlantFertilizeTime();
            long timeSinceLastWaterInteraction = System.currentTimeMillis() - this.unicacityAddon.services().fileService().data().getPlantWaterTime();

            long fertilizeTimeLeft = TimeUnit.MINUTES.toMillis(70) - timeSinceLastFertilizeInteraction;
            long waterTimeLeft = TimeUnit.MINUTES.toMillis(50) - timeSinceLastWaterInteraction;

            this.fertilize.updateAndFlush(fertilizeTimeLeft >= 0 ? this.unicacityAddon.utils().textUtils().parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(fertilizeTimeLeft)) : ColorCode.RED.getCode() + "-" + this.unicacityAddon.utils().textUtils().parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(-fertilizeTimeLeft)));
            this.water.updateAndFlush(waterTimeLeft >= 0 ? this.unicacityAddon.utils().textUtils().parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(waterTimeLeft)) : ColorCode.RED.getCode() + "-" + this.unicacityAddon.utils().textUtils().parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(-waterTimeLeft)));
        }
    }
}