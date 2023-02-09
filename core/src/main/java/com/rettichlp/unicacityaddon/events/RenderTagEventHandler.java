package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;

import java.util.Objects;

/**
 * @author RettichLP
 */
public class RenderTagEventHandler /*implements RenderEntityEvent*/ {

    private final UnicacityAddon unicacityAddon;

    public RenderTagEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    //    @Override
//    public void onRender(Entity entity, double x, double y, double z, float v3) {
//        if (entity instanceof EntityPlayer) {
//            EntityPlayer entityPlayer = (EntityPlayer) entity;
//            String entityPlayerName = entityPlayer.getName();
//            y = y + entityPlayer.height + getDamageIndicatorValue() + getFriendTagValue(entityPlayer);
//
//            EntityPlayerSP entityPlayerSP = p.getPlayer();
//            if (entityPlayer.isSneaking()
//                    || !entityPlayer.canEntityBeSeen(entityPlayerSP)
//                    || (entityPlayer.equals(entityPlayerSP) && entityPlayer.isRiding())
//                    || !ConfigElements.getRenderAddonGroupTag())
//                return;
//
//            if (AddonGroup.CEO.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.CEO.getDisplayName(), AddonGroup.CEO.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            } else if (AddonGroup.DEV.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.DEV.getDisplayName(), AddonGroup.DEV.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            } else if (AddonGroup.MOD.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.MOD.getDisplayName(), AddonGroup.MOD.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            } else if (AddonGroup.SUP.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.SUP.getDisplayName(), AddonGroup.SUP.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            } else if (AddonGroup.BETA.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.BETA.getDisplayName(), AddonGroup.BETA.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            } else if (AddonGroup.VIP.getMemberList().contains(entityPlayerName)) {
//                RenderUtils.renderTag(AddonGroup.VIP.getDisplayName(), AddonGroup.VIP.getColor(), 0.02F,
//                        true, x, y + 0.7, z);
//            }
//        }
//    }

    private static double getDamageIndicatorValue() {
        return Laby.labyAPI().addonService().getLoadedAddons().stream()
                .filter(Objects::nonNull)
                .filter(loadedAddon -> loadedAddon.info() != null)
                .filter(loadedAddon -> loadedAddon.info().getDisplayName() != null)
                .filter(loadedAddon -> loadedAddon.info().getDisplayName().equals("DamageIndicator"))
                .iterator()
                .hasNext() ? 0.2 : 0;
    }

    private static double getFriendTagValue(ClientPlayer player) {
        return Laby.labyAPI().addonService().getLoadedAddons().stream()
                .filter(Objects::nonNull)
                .filter(loadedAddon -> loadedAddon.info() != null)
                .filter(loadedAddon -> loadedAddon.info().getDisplayName() != null)
                .filter(loadedAddon -> loadedAddon.info().getDisplayName().equals("FriendTags"))
                .iterator()
                .hasNext() && Laby.labyAPI().labyConnect().getSession().getFriends().stream()
                .anyMatch(friend -> friend.getUniqueId().equals(player.getUniqueId())) ? 0.2 : 0;
    }
}