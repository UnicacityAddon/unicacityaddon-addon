package com.rettichlp.unicacityaddon.listener.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

/**
 * @author RettichLP
 */
@UCEvent
public class WinemakerListener {

    private Point lastMousePos;

    private final UnicacityAddon unicacityAddon;

    public WinemakerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onItemStackTooltip(ItemStackTooltipEvent e) {
        ItemStack itemStack = e.itemStack();
        String plainDisplayName = this.unicacityAddon.utilService().text().plain(itemStack.getDisplayName());

        if (plainDisplayName.equals("Traube")) {
            this.lastMousePos = MouseInfo.getPointerInfo().getLocation();
        }
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent e) {
        String containerLegacyName = this.unicacityAddon.guiController().getContainerLegacyName();
        boolean isWinemakerContainer = containerLegacyName != null && containerLegacyName.equals(ColorCode.GOLD.getCode() + "Winzer");

        if (isWinemakerContainer && this.lastMousePos != null) {
            try {
                Robot robot = new Robot();
                robot.mouseMove(this.lastMousePos.x, this.lastMousePos.y);
                this.lastMousePos = null;
            } catch (AWTException ex) {
                this.unicacityAddon.logger().warn(ex.getMessage());
            }
        }
    }
}