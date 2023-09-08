package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.RenderController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.AxisAlignedBB;

import javax.inject.Singleton;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX;
import static org.lwjgl.opengl.GL11.*;

@Singleton
@Implements(RenderController.class)
public class VersionedRenderController extends RenderController {

    @Override
    public void drawFacade(FloatVector3 first, FloatVector3 second, Color c) {
        // length of facade
        double length;

        if (first.getX() == second.getX()) { // modify z
            // lower location
            FloatVector3 lower = first.getZ() <= second.getZ() ? first : second;

            double x = lower.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
            double y = 0 - Minecraft.getMinecraft().getRenderManager().viewerPosY;
            double z = lower.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

            // length
            length = Math.abs(first.getZ() - second.getZ());

            // draw
            glPushMatrix();
            gl(c.withAlpha(0.12f), () -> {
                drawColorBox(new AxisAlignedBB(x, y, z, x, y + 100, z + length), 0F, 0F, 0F, 0F);
                glColor4d(0, 0, 0, 0.5);
                drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x, y + 100, z + length));
                glLineWidth(2.0F);
            });
            glPopMatrix();
        } else if (first.getZ() == second.getZ()) { // modify x
            // lower location
            FloatVector3 lower = first.getX() <= second.getX() ? first : second;

            double x = lower.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
            double y = 0 - Minecraft.getMinecraft().getRenderManager().viewerPosY;
            double z = lower.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

            // length
            length = Math.abs(first.getX() - second.getX());

            // draw
            glPushMatrix();
            gl(c.withAlpha(0.12f), () -> {
                drawColorBox(new AxisAlignedBB(x, y, z, x + length, y + 100, z), 0F, 0F, 0F, 0F);
                glColor4d(0, 0, 0, 0.5);
                drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length, y + 100, z));
                glLineWidth(2.0F);
            });
            glPopMatrix();
        } else {
            throw new IllegalArgumentException("Positions are not in a row: " + first + " and " + second);
        }
    }

    private void gl(Color c, Runnable runnable) {
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glLineWidth(2.0F);
        glDisable(GL_TEXTURE_2D);
//        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);

        runnable.run();

        glEnable(GL_TEXTURE_2D);
//        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    private void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, POSITION_TEX);// Starts X.
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, POSITION_TEX);
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();// Ends X.
        bufferBuilder.begin(7, POSITION_TEX);// Starts Y.
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, POSITION_TEX);
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();// Ends Y.
        bufferBuilder.begin(7, POSITION_TEX);// Starts Z.
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        bufferBuilder.begin(7, POSITION_TEX);
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferBuilder.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();// Ends Z.
    }

    private void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(3, POSITION);
        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(3, POSITION);
        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        bufferBuilder.begin(1, POSITION);
        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        bufferBuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
}