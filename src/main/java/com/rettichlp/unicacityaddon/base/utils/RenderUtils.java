package com.rettichlp.unicacityaddon.base.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

/**
 * @author RettichLP
 * @see <a href="https://github.com/sudofox/xdolf/blob/master/minecraft/com/darkcart/xdolf/util/RenderUtils.java">xdolf by sudofox</a>
 * @see <a href="https://github.com/LabyMod/addons/commit/3d61f49988ce0e73cf9f45028de7980c04e03659#diff-8b4c9ef23503ec3ff000fe0b45d61dc0cb71e2e0b10b8ed0ff4142bd714d47eb">LabyMod and LabyStudio</a>
 */
public class RenderUtils {

    private static final RenderManager renderManager = UnicacityAddon.MINECRAFT.getRenderManager();

    /**
     * Renders a tag with any size and any color at any position.
     *
     * @param tag                Content of the tag
     * @param color              Color of the tag, can be overwritten with minecraft color codes in tag
     * @param scale              Size of the tag content
     * @param hiddenBehindBlocks Hides the tag behind blocks and entities
     * @param x                  Position x of the tag
     * @param y                  Position y of the tag
     * @param z                  Position z of the tag
     */
    public static void renderTag(String tag, Color color, float scale, boolean hiddenBehindBlocks, double x, double y, double z) {
        float fixedPlayerViewX = renderManager.playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1);
        FontRenderer fontRenderer = renderManager.getFontRenderer();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GL11.glNormal3f(0F, 1F, 0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
        GlStateManager.rotate(fixedPlayerViewX, 1F, 0F, 0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(hiddenBehindBlocks ? "" : tag, -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(tag, -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GlStateManager.popMatrix();
    }
}