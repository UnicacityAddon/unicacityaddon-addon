package com.rettichlp.unicacityaddon.hudwidgets;

import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;

/**
 * Timer for:
 * <ul>
 *     <li>FBI burglary (time until wps are cleared)</li>
 *     <li>Explosive belt timer (time until explosive belt explodes)</li>
 * </ul>
 *
 * @author Dimiikou
 * @author RettichLP
 */
public class TimerHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine textLine;
    private final Icon hudWidgetIcon;

    public TimerHudWidget(String id, Icon icon) {
        super(id);
        this.hudWidgetIcon = icon;
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Timer", TextUtils.parseTimer(FileManager.DATA.getTimer()));
        this.setIcon(this.hudWidgetIcon);
    }

    @Override
    public boolean isVisibleInGame() {
        return FileManager.DATA.getTimer() > 0;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            this.textLine.updateAndFlush(TextUtils.parseTimer(FileManager.DATA.getTimer()));
        }
    }
}