package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class PlantHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine fertilize;
    private TextLine water;
    private final Icon hudWidgetIcon;

    public PlantHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.fertilize = super.createLine("Düngen", "");
        this.water = super.createLine("Wässern", "");
        this.setIcon(this.hudWidgetIcon);
    }

    @Override
    public boolean isVisibleInGame() {
        boolean showFertilize = System.currentTimeMillis() - FileManager.DATA.getPlantFertilizeTime() < TimeUnit.MINUTES.toMillis(90);
        boolean showWater = System.currentTimeMillis() - FileManager.DATA.getPlantWaterTime() < TimeUnit.MINUTES.toMillis(70);
        return showFertilize || showWater;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            long timeSinceLastFertilizeInteraction = System.currentTimeMillis() - FileManager.DATA.getPlantFertilizeTime();
            long timeSinceLastWaterInteraction = System.currentTimeMillis() - FileManager.DATA.getPlantWaterTime();

            long fertilizeTimeLeft = TimeUnit.MINUTES.toMillis(70) - timeSinceLastFertilizeInteraction;
            long waterTimeLeft = TimeUnit.MINUTES.toMillis(50) - timeSinceLastWaterInteraction;

            this.fertilize.updateAndFlush(fertilizeTimeLeft >= 0 ? TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(fertilizeTimeLeft)) : ColorCode.RED.getCode() + "-" + TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(-fertilizeTimeLeft)));
            this.water.updateAndFlush(waterTimeLeft >= 0 ? TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(waterTimeLeft)) : ColorCode.RED.getCode() + "-" + TextUtils.parseTimer((int) TimeUnit.MILLISECONDS.toSeconds(-waterTimeLeft)));
        }
    }
}