package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.AddonGroup;
import com.rettichlp.UnicacityAddon.base.utils.RenderUtils;
import net.labymod.addon.AddonLoader;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Objects;

/**
 * @author RettichLP
 */
public class RenderTagEventHandler implements RenderEntityEvent {

    @Override
    public void onRender(Entity entity, double x, double y, double z, float v3) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            String entityPlayerName = entityPlayer.getName();
            y = y + entityPlayer.height + getDamageIndicatorValue() + getFriendTagValue(entityPlayer);

            EntityPlayerSP entityPlayerSP = AbstractionLayer.getPlayer().getPlayer();
            if (entityPlayer.isSneaking()
                    || !entityPlayer.canEntityBeSeen(entityPlayerSP)
                    || (entityPlayer.equals(entityPlayerSP) && entityPlayer.isRiding()))
                return;

            if (AddonGroup.CEO.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.CEO.getDisplayName(), AddonGroup.CEO.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            } else if (AddonGroup.DEV.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.DEV.getDisplayName(), AddonGroup.DEV.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            } else if (AddonGroup.MOD.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.MOD.getDisplayName(), AddonGroup.MOD.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            } else if (AddonGroup.SUP.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.SUP.getDisplayName(), AddonGroup.SUP.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            } else if (AddonGroup.BETA.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.BETA.getDisplayName(), AddonGroup.BETA.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            } else if (AddonGroup.VIP.getMemberList().contains(entityPlayerName)) {
                RenderUtils.renderTag(AddonGroup.VIP.getDisplayName(), AddonGroup.VIP.getColor(), 0.02F,
                        true, x, y + 0.7, z);
            }
        }
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