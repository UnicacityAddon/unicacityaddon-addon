//package com.rettichlp.unicacityaddon.base.utils;
//
//import com.rettichlp.unicacityaddon.UnicacityAddon;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.FontRenderer;
//import net.minecraft.client.gui.ScaledResolution;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.entity.RenderManager;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Vec3d;
//import org.lwjgl.opengl.GL11;
//import org.lwjgl.opengl.GL13;
//import org.lwjgl.util.glu.GLU;
//import org.lwjgl.util.glu.Sphere;
//
//import java.awt.Color;
//
//import static org.lwjgl.opengl.GL11.*;
//
///**
// * @author RettichLP
// * @see <a href="https://github.com/sudofox/xdolf/blob/master/minecraft/com/darkcart/xdolf/util/RenderUtils.java">xdolf by sudofox</a>
// * @see <a href="https://github.com/LabyMod/addons/commit/3d61f49988ce0e73cf9f45028de7980c04e03659#diff-8b4c9ef23503ec3ff000fe0b45d61dc0cb71e2e0b10b8ed0ff4142bd714d47eb">LabyMod and LabyStudio</a>
// */
//public class RenderUtils {
//
//    private static final RenderManager renderManager = UnicacityAddon.MINECRAFT.getRenderManager();
//
//    /**
//     * Renders a tag with any size and any color at any position.
//     *
//     * @param tag                Content of the tag
//     * @param color              Color of the tag, can be overwritten with minecraft color codes in tag
//     * @param scale              Size of the tag content
//     * @param hiddenBehindBlocks Hides the tag behind blocks and entities
//     * @param x                  Position x of the tag
//     * @param y                  Position y of the tag
//     * @param z                  Position z of the tag
//     */
//    public static void renderTag(String tag, Color color, float scale, boolean hiddenBehindBlocks, double x, double y, double z) {
//        /*
//         * Code Snippet to Render NameTags by LabyStudio
//         * https://github.com/LabyMod/addons/commit/3d61f49988ce0e73cf9f45028de7980c04e03659#diff-8b4c9ef23503ec3ff000fe0b45d61dc0cb71e2e0b10b8ed0ff4142bd714d47eb
//         */
//        float fixedPlayerViewX = renderManager.playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1);
//        FontRenderer fontRenderer = renderManager.getFontRenderer();
//        GlStateManager.pushMatrix();
//
//        GlStateManager.translate(x, y, z);
//        GL11.glNormal3f(0F, 1F, 0F);
//        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
//        GlStateManager.rotate(fixedPlayerViewX, 1F, 0F, 0F);
//        GlStateManager.scale(-scale, -scale, scale);
//        GlStateManager.disableLighting();
//        GlStateManager.depthMask(false);
//        GlStateManager.disableDepth();
//        GlStateManager.enableBlend();
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//        GlStateManager.enableTexture2D();
//        fontRenderer.drawString(hiddenBehindBlocks ? "" : tag, -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
//        GlStateManager.enableDepth();
//        GlStateManager.depthMask(true);
//        fontRenderer.drawString(tag, -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
//        GlStateManager.enableLighting();
//        GlStateManager.disableBlend();
//        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        GlStateManager.popMatrix();
//    }
//
//    /**
//     * Renders a box with any size and any color.
//     *
//     * @param x     x of the box
//     * @param y     y of the box
//     * @param z     z of the box
//     * @param x2    x2 of the box
//     * @param y2    y2 of the box
//     * @param z2    z2 of the box
//     * @param color Color of the box
//     */
//    public static void box(double x, double y, double z, double x2, double y2, double z2, Color color) {
//        x = x - renderManager.viewerPosX;
//        y = y - renderManager.viewerPosY;
//        z = z - renderManager.viewerPosZ;
//        x2 = x2 - renderManager.viewerPosX;
//        y2 = y2 - renderManager.viewerPosY;
//        z2 = z2 - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glDepthMask(false);
//        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
//        GL11.glColor4d(0, 0, 0, 0.5F);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    /**
//     * Renders a frame with any size and any color.
//     *
//     * @param x     x of the frame
//     * @param y     y of the frame
//     * @param z     z of the frame
//     * @param x2    x2 of the frame
//     * @param y2    y2 of the frame
//     * @param z2    z2 of the frame
//     * @param color Color of the frame
//     */
//    public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
//        x = x - renderManager.viewerPosX;
//        y = y - renderManager.viewerPosY;
//        z = z - renderManager.viewerPosZ;
//        x2 = x2 - renderManager.viewerPosX;
//        y2 = y2 - renderManager.viewerPosY;
//        z2 = z2 - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        setColor(color);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    /**
//     * Renders a green ESP box with the size of a normal block at the specified
//     * BlockPos.
//     */
//    public static void blockESP(BlockPos b, Color c, double length, double length2) {
//        blockEsp(b, c, length, length2);
//    }
//
//    /**
//     * Renders an ESP box with the size of a normal block at the specified
//     * BlockPos.
//     */
//    public static void blockEsp(BlockPos blockPos, Color c, double length, double length2) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glPushMatrix();
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.25);
//        drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length), 0F, 0F, 0F, 0F);
//        GL11.glColor4d(0, 0, 0, 0.5);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length));
//        GL11.glLineWidth(2.0F);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//        GL11.glPopMatrix();
//    }
//
//    public static void blockEspFrame(BlockPos blockPos, double red, double green, double blue) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(1.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(red, green, blue, 0.5);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static void blockEspBox(BlockPos blockPos, double red, double green, double blue) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(red, green, blue, 0.15F);
//        drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 0F, 0F, 0F);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static void emptyBlockESPBox(BlockPos blockPos) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(0, 0, 0, 0.5F);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static int enemy = 0;
//    public static int friend = 1;
//    public static int other = 2;
//    public static int target = 3;
//    public static int team = 4;
//
//    public static void entityESPBox(Entity entity, Color c) {
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(4.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
//        drawSelectionBoundingBox(new AxisAlignedBB(
//                entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - renderManager.viewerPosY),
//                entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ),
//                entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - renderManager.viewerPosY),
//                entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ)));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static void nukerBox(BlockPos blockPos, float damage) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(1.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4f(damage, 1 - damage, 0, 0.15F);
//        drawColorBox(
//                new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
//                        x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2),
//                damage, 1 - damage, 0, 0.15F);
//        GL11.glColor4d(0, 0, 0, 0.5F);
//        drawSelectionBoundingBox(new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
//                x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static void searchBox(BlockPos blockPos) {
//        double x = blockPos.getX() - renderManager.viewerPosX;
//        double y = blockPos.getY() - renderManager.viewerPosY;
//        double z = blockPos.getZ() - renderManager.viewerPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(1.0F);
//        float sinus = 1F - MathHelper
//                .abs(MathHelper.sin(Minecraft.getSystemTime() % 10000L / 10000.0F * (float) Math.PI * 4.0F) * 1F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4f(1F - sinus, sinus, 0F, 0.15F);
//        drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 1F - sinus, sinus, 0F, 0.15F);
//        GL11.glColor4d(0, 0, 0, 0.5);
//        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//    }
//
//    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
//        Tessellator ts = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = ts.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();// Ends X.
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();// Ends Y.
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
//        ts.draw();// Ends Z.
//    }
//
//    public static void tracerLine(Entity entity, Color color) {
//        Vec3d eyes = new Vec3d(0, 0, 1)
//                .rotatePitch(-(float) Math.toRadians(UnicacityAddon.MINECRAFT.player.rotationPitch))
//                .rotateYaw(-(float) Math.toRadians(UnicacityAddon.MINECRAFT.player.rotationYaw));
//        double x = entity.posX - renderManager.viewerPosX;
//        double y = entity.posY + entity.height / 2 - renderManager.viewerPosY;
//        double z = entity.posZ - renderManager.viewerPosZ;
//        glBlendFunc(770, 771);
//        glEnable(GL_BLEND);
//        glLineWidth(2.0F);
//        glDisable(GL11.GL_TEXTURE_2D);
//        glDisable(GL_DEPTH_TEST);
//        glDepthMask(false);
//        setColor(color);
//        glBegin(GL_LINES);
//        {
//            glVertex3d(new ScaledResolution(UnicacityAddon.MINECRAFT).getScaledWidth() / 2.0,
//                    UnicacityAddon.MINECRAFT.player.getEyeHeight(),
//                    new ScaledResolution(UnicacityAddon.MINECRAFT).getScaledHeight() / 2.0);
//            glVertex3d(x, y, z);
//        }
//        glEnd();
//        glEnable(GL11.GL_TEXTURE_2D);
//        glEnable(GL_DEPTH_TEST);
//        glDepthMask(true);
//        glDisable(GL_BLEND);
//    }
//
//    public static void tracerLine(int x, int y, int z, Color color) {
//        x += 0.5 - renderManager.viewerPosX;
//        y += 0.5 - renderManager.viewerPosY;
//        z += 0.5 - renderManager.viewerPosZ;
//        glBlendFunc(770, 771);
//        glEnable(GL_BLEND);
//        glLineWidth(2.0F);
//        glDisable(GL11.GL_TEXTURE_2D);
//        glDisable(GL_DEPTH_TEST);
//        glDepthMask(false);
//        setColor(color);
//        glBegin(GL_LINES);
//        {
//            glVertex3d(0, UnicacityAddon.MINECRAFT.player.getEyeHeight(), 0);
//            glVertex3d(x, y, z);
//        }
//        glEnd();
//        glEnable(GL11.GL_TEXTURE_2D);
//        glEnable(GL_DEPTH_TEST);
//        glDepthMask(true);
//        glDisable(GL_BLEND);
//    }
//
//    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
//        tessellator.draw();
//        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
//        tessellator.draw();
//        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
//        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
//        tessellator.draw();
//    }
//
//    public static void scissorBox(int x, int y, int xend, int yend) {
//        int width = xend - x;
//        int height = yend - y;
//        ScaledResolution sr = new ScaledResolution(UnicacityAddon.MINECRAFT);
//        int factor = sr.getScaleFactor();
//        assert UnicacityAddon.MINECRAFT.currentScreen != null;
//        int bottomY = UnicacityAddon.MINECRAFT.currentScreen.height - yend;
//        glScissor(x * factor, bottomY * factor, width * factor, height * factor);
//    }
//
//    public static void setColor(Color c) {
//        glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
//    }
//
//    public static float[] getColorValues(Color c) {
//        return new float[]{c.getRed() / 255f, c.getGreen() / 255f, c.getGreen() / 255f};
//    }
//
//    /*
//    public static void setWindowCursor(int cursor) {
//	    Adolf.getMinecraft().mcCanvas.setCursor(Cursor.getPredefinedCursor(cursor));
//	}
//	*/
//
//    public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
//        final Sphere s = new Sphere();
//        GL11.glPushMatrix();
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(1.2F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        s.setDrawStyle(GLU.GLU_SILHOUETTE);
//        GL11.glTranslated(x - renderManager.viewerPosX, y - renderManager.viewerPosY, z - renderManager.viewerPosZ);
//        s.draw(size, slices, stacks);
//        GL11.glLineWidth(2.0F);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//        GL11.glPopMatrix();
//    }
//
//    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
//        x *= 2;
//        y *= 2;
//        x1 *= 2;
//        y1 *= 2;
//        GL11.glScalef(0.5F, 0.5F, 0.5F);
//        drawVLine(x, y + 1, y1 - 2, borderC);
//        drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
//        drawHLine(x + 2, x1 - 3, y, borderC);
//        drawHLine(x + 2, x1 - 3, y1 - 1, borderC);
//        drawHLine(x + 1, x + 1, y + 1, borderC);
//        drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
//        drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
//        drawHLine(x + 1, x + 1, y1 - 2, borderC);
//        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
//        GL11.glScalef(2.0F, 2.0F, 2.0F);
//    }
//
//    public static void drawBetterBorderedRect(int x, float y, int x1, int y1, float size, int borderC, int insideC) {
//        drawRect(x, y, x1, y1, insideC); //inside
//        drawRect(x, y, x1, y + size, borderC); //top
//        drawRect(x, y1, x1, y1 + size, borderC); //bottom
//        drawRect(x1, y, x1 + size, y1 + size, borderC); //left
//        drawRect(x, y, x + size, y1 + size, borderC); //right
//    }
//
//    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
//        drawRect((float) x, (float) y, (float) x2, (float) y2, col2);
//
//        float f = (float) (col1 >> 24 & 0xFF) / 255F;
//        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
//        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
//        float f3 = (float) (col1 & 0xFF) / 255F;
//        GL11.glPushMatrix();
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//
//        GL11.glColor4f(f1, f2, f3, f);
//        GL11.glLineWidth(l1);
//        GL11.glBegin(GL11.GL_LINES);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x, y2);
//        GL11.glVertex2d(x2, y2);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y2);
//        GL11.glVertex2d(x2, y2);
//        GL11.glEnd();
//
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glPopMatrix();
//    }
//
//
//    public static void drawHLine(float par1, float par2, float par3, int par4) {
//        if (par2 < par1) {
//            float var5 = par1;
//            par1 = par2;
//            par2 = var5;
//        }
//
//        drawRect(par1, par3, par2 + 1, par3 + 1, par4);
//    }
//
//    public static void drawVLine(float par1, float par2, float par3, int par4) {
//        if (par3 < par2) {
//            float var5 = par2;
//            par2 = par3;
//            par3 = var5;
//        }
//
//        drawRect(par1, par2 + 1, par1 + 1, par3, par4);
//    }
//
//    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
//        float alpha = (float) (paramColor >> 24 & 0xFF) / 255F;
//        float red = (float) (paramColor >> 16 & 0xFF) / 255F;
//        float green = (float) (paramColor >> 8 & 0xFF) / 255F;
//        float blue = (float) (paramColor & 0xFF) / 255F;
//        GL11.glPushMatrix();
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//
//        GL11.glColor4f(red, green, blue, alpha);
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glVertex2d(paramXEnd, paramYStart);
//        GL11.glVertex2d(paramXStart, paramYStart);
//        GL11.glVertex2d(paramXStart, paramYEnd);
//        GL11.glVertex2d(paramXEnd, paramYEnd);
//        GL11.glEnd();
//
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glPopMatrix();
//    }
//
//    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
//        float f = (float) (col1 >> 24 & 0xFF) / 255F;
//        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
//        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
//        float f3 = (float) (col1 & 0xFF) / 255F;
//
//        float f4 = (float) (col2 >> 24 & 0xFF) / 255F;
//        float f5 = (float) (col2 >> 16 & 0xFF) / 255F;
//        float f6 = (float) (col2 >> 8 & 0xFF) / 255F;
//        float f7 = (float) (col2 & 0xFF) / 255F;
//
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glShadeModel(GL11.GL_SMOOTH);
//
//        GL11.glPushMatrix();
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glColor4f(f1, f2, f3, f);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y);
//
//        GL11.glColor4f(f5, f6, f7, f4);
//        GL11.glVertex2d(x, y2);
//        GL11.glVertex2d(x2, y2);
//        GL11.glEnd();
//        GL11.glPopMatrix();
//
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glShadeModel(GL11.GL_FLAT);
//    }
//
//    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
//        float f = (float) (col1 >> 24 & 0xFF) / 255F;
//        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
//        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
//        float f3 = (float) (col1 & 0xFF) / 255F;
//
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(GL11.GL_BLEND);
//
//        GL11.glPushMatrix();
//        GL11.glColor4f(f1, f2, f3, f);
//        GL11.glLineWidth(1F);
//        GL11.glBegin(GL11.GL_LINES);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x, y2);
//        GL11.glVertex2d(x2, y2);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y2);
//        GL11.glVertex2d(x2, y2);
//        GL11.glEnd();
//        GL11.glPopMatrix();
//
//        drawGradientRect(x, y, x2, y2, col2, col3);
//
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//    }
//
//    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
//        GL11.glPushMatrix();
//        float f1 = (float) (color >> 24 & 255) / 255.0F;
//        float f2 = (float) (color >> 16 & 255) / 255.0F;
//        float f3 = (float) (color >> 8 & 255) / 255.0F;
//        float f4 = (float) (color & 255) / 255.0F;
//        GL11.glTranslatef(x, y, 0);
//        GL11.glColor4f(f2, f3, f4, f1);
//        GL11.glLineWidth(width);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_ALPHA_TEST);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
//        GL11.glEnable(GL13.GL_MULTISAMPLE);
//
//        if (angle > 0) {
//            GL11.glBegin(GL11.GL_LINE_STRIP);
//
//            for (int i = 0; i < angle; i++) {
//                float a = (float) (i * (angle * Math.PI / points));
//                float xc = (float) (Math.cos(a) * radius);
//                float yc = (float) (Math.sin(a) * radius);
//                GL11.glVertex2f(xc, yc);
//            }
//
//            GL11.glEnd();
//        }
//
//        if (angle < 0) {
//            GL11.glBegin(GL11.GL_LINE_STRIP);
//
//            for (int i = 0; i > angle; i--) {
//                float a = (float) (i * (angle * Math.PI / points));
//                float xc = (float) (Math.cos(a) * -radius);
//                float yc = (float) (Math.sin(a) * -radius);
//                GL11.glVertex2f(xc, yc);
//            }
//
//            GL11.glEnd();
//        }
//
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(GL11.GL_ALPHA_TEST);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDisable(GL13.GL_MULTISAMPLE);
//        GL11.glDisable(GL11.GL_MAP1_VERTEX_3);
//        GL11.glPopMatrix();
//    }
//
//
//    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
//        GL11.glScalef(0.5F, 0.5F, 0.5F);
//        r *= 2;
//        cx *= 2;
//        cy *= 2;
//        float f = (float) (c >> 24 & 0xff) / 255F;
//        float f1 = (float) (c >> 16 & 0xff) / 255F;
//        float f2 = (float) (c >> 8 & 0xff) / 255F;
//        float f3 = (float) (c & 0xff) / 255F;
//        float theta = (float) (2 * 3.1415926 / (num_segments));
//        float p = (float) Math.cos(theta);//calculate the sine and cosine
//        float s = (float) Math.sin(theta);
//        float t;
//        GL11.glColor4f(f1, f2, f3, f);
//        float x = r;
//        float y = 0;//start at angle = 0
//        GL11.glEnable(3042);
//        GL11.glDisable(3553);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glBlendFunc(770, 771);
//        GL11.glBegin(GL11.GL_LINE_LOOP);
//        for (int ii = 0; ii < num_segments; ii++) {
//            GL11.glVertex2f(x + cx, y + cy);//final vertex vertex
//
//            //rotate the stuff
//            t = x;
//            x = p * x - s * y;
//            y = s * t + p * y;
//        }
//        GL11.glEnd();
//        GL11.glEnable(3553);
//        GL11.glDisable(3042);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glScalef(2F, 2F, 2F);
//    }
//
//    public static void drawFullCircle(int cx, int cy, double r, int c) {
//        GL11.glScalef(0.5F, 0.5F, 0.5F);
//        r *= 2;
//        cx *= 2;
//        cy *= 2;
//        float f = (float) (c >> 24 & 0xff) / 255F;
//        float f1 = (float) (c >> 16 & 0xff) / 255F;
//        float f2 = (float) (c >> 8 & 0xff) / 255F;
//        float f3 = (float) (c & 0xff) / 255F;
//        GL11.glEnable(3042);
//        GL11.glDisable(3553);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glBlendFunc(770, 771);
//        GL11.glColor4f(f1, f2, f3, f);
//        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
//        for (int i = 0; i <= 360; i++) {
//            double x = Math.sin((i * Math.PI / 180)) * r;
//            double y = Math.cos((i * Math.PI / 180)) * r;
//            GL11.glVertex2d(cx + x, cy + y);
//        }
//        GL11.glEnd();
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(3553);
//        GL11.glDisable(3042);
//        GL11.glScalef(2F, 2F, 2F);
//    }
//
//    public static void drawOutlinedBoundingBox(net.minecraft.util.math.AxisAlignedBB par1AxisAlignedBB) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
//        GlStateManager.disableTexture2D();
//        GlStateManager.enableColorLogic();
//        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
//        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
//        tessellator.draw();
//        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        tessellator.draw();
//        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ).endVertex();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ).endVertex();
//        tessellator.draw();
//        GlStateManager.disableColorLogic();
//        GlStateManager.enableTexture2D();
//    }
//
//    public static void drawBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//
//        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
//        GlStateManager.disableTexture2D();
//        GlStateManager.enableColorLogic();
//        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
//        bufferBuilder.begin(3, DefaultVertexFormats.POSITION);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        tessellator.draw();
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        tessellator.draw();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        tessellator.draw();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        tessellator.draw();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        tessellator.draw();
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
//        bufferBuilder.pos(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
//        tessellator.draw();
//        GlStateManager.disableColorLogic();
//        GlStateManager.enableTexture2D();
//    }
//
//    public static void drawESP(double d, double d1, double d2, double r, double b, double g) {
//        GL11.glPushMatrix();
//        GL11.glEnable(3042);
//        GL11.glBlendFunc(770, 771);
//        GL11.glLineWidth(1.5F);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glLineWidth(1.0F);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(2929);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(r, g, b, 0.1825F);
//        drawColorBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0), 0F, 0F, 0F, 0F);
//        GL11.glColor4d(0, 0, 0, 0.5);
//        drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(2929);
//        GL11.glDepthMask(true);
//        GL11.glDisable(3042);
//        GL11.glPopMatrix();
//    }
//
//    public static void drawChestESP(double d, double d1, double d2, double r, double b, double g, double length, double length2) {
//        GL11.glPushMatrix();
//        GL11.glEnable(3042);
//        GL11.glBlendFunc(770, 771);
//        GL11.glLineWidth(1.5F);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glLineWidth(1.0F);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(2929);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(r, g, b, 0.15);
//        drawColorBox(new AxisAlignedBB(d, d1, d2, d + length2, d1 + 1.0, d2 + length), 0F, 0F, 0F, 0F);
//        GL11.glColor4d(0, 0, 0, 0.5);
//        drawSelectionBoundingBox(new AxisAlignedBB(d, d1, d2, d + length2, d1 + 1.0, d2 + length));
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(2929);
//        GL11.glDepthMask(true);
//        GL11.glDisable(3042);
//        GL11.glPopMatrix();
//    }
//
//    public static void drawLogoutESP(double d, double d1, double d2, double r, double b, double g) {
//        GL11.glPushMatrix();
//        GL11.glEnable(3042);
//        GL11.glBlendFunc(770, 771);
//        GL11.glLineWidth(1.5F);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glLineWidth(1.0F);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(2929);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(r, g, b, 0.1825F);
//        drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
//        GL11.glColor4d(r, g, b, 1.0F);
//        drawOutlinedBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
//        GL11.glLineWidth(2.0F);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(2929);
//        GL11.glDepthMask(true);
//        GL11.glDisable(3042);
//        GL11.glPopMatrix();
//    }
//
//    /*
//    public static void drawLogoutSpotTracer(LogoutSpot l) {
//        try {
//            GL11.glPushMatrix();
//            GL11.glEnable(GL11.GL_LINE_SMOOTH);
//            GL11.glDisable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_DEPTH_TEST);
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glDepthMask(false);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glLineWidth(1.5F);
//
//            GL11.glColor3d(l.red, l.green, l.blue);
//            GL11.glBegin(GL11.GL_LINE_LOOP);
//            GL11.glVertex3d(0, 0, 0);
//            GL11.glVertex3d(l.dX + 0.5, l.dY + 0.5, l.dZ + 0.5);
//            GL11.glEnd();
//
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glDepthMask(true);
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glEnable(GL11.GL_DEPTH_TEST);
//            GL11.glEnable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_LINE_SMOOTH);
//            GL11.glPopMatrix();
//        } catch (Exception ignored) {
//        }
//    }
//    */
//
//    /*
//    public static void drawWayPointTracer(Waypoint w) {
//        try {
//            GL11.glPushMatrix();
//            GL11.glEnable(GL11.GL_LINE_SMOOTH);
//            GL11.glDisable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_DEPTH_TEST);
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glDepthMask(false);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glLineWidth(1.5F);
//            GL11.glColor3d(w.red, w.green, w.blue);
//            GL11.glBegin(GL11.GL_LINE_LOOP);
//            Vec3d eyes = new Vec3d(0, 0, 1).rotatePitch(-(float) Math.toRadians(UnicacityAddon.MINECRAFT.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(UnicacityAddon.MINECRAFT.player.rotationYaw));
//
//            GL11.glVertex3d(eyes.x, UnicacityAddon.MINECRAFT.player.getEyeHeight() + eyes.y, eyes.z);
//            GL11.glVertex3d(w.dX + 0.5, w.dY + 0.5, w.dZ + 0.5);
//            GL11.glEnd();
//
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glDepthMask(true);
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glEnable(GL11.GL_DEPTH_TEST);
//            GL11.glEnable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_LINE_SMOOTH);
//            GL11.glPopMatrix();
//        } catch (Exception ignored) {
//        }
//    }
//    */
//
//    public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal) {
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y, z);
//        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate((float) (isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
//        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
//        GlStateManager.disableLighting();
//        GlStateManager.depthMask(false);
//
//        GlStateManager.enableBlend();
//        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        int i = fontRendererIn.getStringWidth(str) / 2;
//        GlStateManager.disableTexture2D();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
//        bufferBuilder.pos(-i - 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferBuilder.pos(-i - 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferBuilder.pos(i + 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        bufferBuilder.pos(i + 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
//        tessellator.draw();
//        GlStateManager.enableTexture2D();
//
//        GlStateManager.depthMask(true);
//        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, -1);
//        GlStateManager.enableLighting();
//        GlStateManager.disableBlend();
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.popMatrix();
//    }
//
//    public static void drawEntityESP(Entity entity, Color c) {
//        GL11.glPushMatrix();
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL_BLEND);
//        GL11.glLineWidth(1.0F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15F);
//        drawColorBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ),
//                entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - renderManager.viewerPosY),
//                entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ)), 0F, 0F, 0F, 0F);
//        GL11.glColor4d(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
//        drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - renderManager.viewerPosY),
//                entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ),
//                entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - renderManager.viewerPosX),
//                entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - renderManager.viewerPosY),
//                entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - renderManager.viewerPosZ)));
//        GL11.glLineWidth(2.0F);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL_BLEND);
//        GL11.glPopMatrix();
//    }
//
//    /*
//    public static void drawPlayerESP(double d, double d1, double d2, EntityPlayer ep, double e, double f) {
//        if (!(ep instanceof EntityPlayerSP)) {
//            GL11.glPushMatrix();
//            GL11.glEnable(3042);
//            if (Wrapper.getFriends().isFriend(ep.getName())) {
//                GL11.glColor4f(0.0F, 1F, 0.0F, 0.15F);
//            } else {
//                GL11.glColor4f(0.7F, 0.0F, 0.0F, 0.15F);
//            }
//            GL11.glPushMatrix();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glDisable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_DEPTH_TEST);
//            GL11.glDepthMask(false);
//            GL11.glLineWidth(1.0F);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            GL11.glEnable(GL11.GL_LINE_SMOOTH);
//            drawBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
//            if (Wrapper.getFriends().isFriend(ep.getName())) {
//                GL11.glColor4f(0.0F, 1F, 0.0F, 1F);
//            } else {
//                GL11.glColor4f(0.7F, 0.0F, 0.0F, 1F);
//            }
//            drawOutlinedBoundingBox(new AxisAlignedBB(d - f, d1 + 0.1, d2 - f, d + f, d1 + e + 0.25, d2 + f));
//            GL11.glDepthMask(true);
//            GL11.glEnable(GL11.GL_DEPTH_TEST);
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glEnable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_LINE_SMOOTH);
//            GL11.glDisable(3042);
//            GL11.glPopMatrix();
//        }
//    }
//    */
//}