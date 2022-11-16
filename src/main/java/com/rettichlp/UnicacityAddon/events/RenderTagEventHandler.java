package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.AddonGroup;
import net.labymod.addon.AddonLoader;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

/**
 * @author RettichLP
 */
public class RenderTagEventHandler implements RenderEntityEvent {

    @Override
    public void onRender(Entity entity, double x, double y, double z, float v3) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (AddonGroup.CEO.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.CEO, x, y, z);
            } else if (AddonGroup.DEV.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.DEV, x, y, z);
            } else if (AddonGroup.MOD.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.MOD, x, y, z);
            } else if (AddonGroup.SUP.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.SUP, x, y, z);
            } else if (AddonGroup.BETA.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.BETA, x, y, z);
            } else if (AddonGroup.VIP.getMemberList().contains(player.getName())) {
                renderTag(player, AddonGroup.VIP, x, y, z);
            }
        }
    }

    public static void renderTag(EntityPlayer player, AddonGroup addonGroup, double x, double y, double z) {
        EntityPlayer p = AbstractionLayer.getPlayer().getPlayer();
        if (player.isInvisibleToPlayer(p) || player.isSneaking() || (player.isRiding() && player.equals(p))) return;

        RenderManager renderManager = UnicacityAddon.MINECRAFT.getRenderManager();
        String tag = addonGroup.getDisplayName();
        Color color = addonGroup.getColor();

        /*
         * Code Snippet to Render NameTags by LabyStudio
         * https://github.com/LabyMod/addons/commit/3d61f49988ce0e73cf9f45028de7980c04e03659#diff-8b4c9ef23503ec3ff000fe0b45d61dc0cb71e2e0b10b8ed0ff4142bd714d47eb
         */
        float fixedPlayerViewX = renderManager.playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1);
        FontRenderer fontRenderer = renderManager.getFontRenderer();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y + player.height + 0.7 + getDamageIndicatorValue() + getFriendTagValue(player), z);
        GL11.glNormal3f(0F, 1F, 0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0F, 1F, 0F);
        GlStateManager.rotate(fixedPlayerViewX, 1F, 0F, 0F);
        float scale = 0.02F;
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        fontRenderer.drawString("", -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(tag, -fontRenderer.getStringWidth(tag) / 2.0F, 0, color.getRGB(), true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GlStateManager.popMatrix();
    }

    private static double getDamageIndicatorValue() {
        return AddonLoader.getAddons().stream()
                .filter(Objects::nonNull)
                .filter(labyModAddon -> labyModAddon.about != null)
                .filter(labyModAddon -> labyModAddon.about.name != null)
                .filter(labyModAddon -> labyModAddon.about.name.equals("DamageIndicator"))
                .iterator()
                .hasNext() ? 0.2 : 0;
    }

    private static double getFriendTagValue(EntityPlayer player) {
        return AddonLoader.getAddons().stream()
                .filter(Objects::nonNull)
                .filter(labyModAddon -> labyModAddon.about != null)
                .filter(labyModAddon -> labyModAddon.about.name != null)
                .filter(labyModAddon -> labyModAddon.about.name.equals("FriendTags"))
                .iterator()
                .hasNext() && LabyMod.getInstance().getLabyConnect().getFriends().stream()
                .anyMatch(chatUser -> chatUser.getGameProfile().getId().equals(player.getGameProfile().getId())) ? 0.2 : 0;
    }
}