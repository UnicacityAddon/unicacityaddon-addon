package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.RenderUtils;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistEventHandler;
import net.labymod.addon.AddonLoader;
import net.labymod.api.events.RenderEntityEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.Color;
import java.util.Objects;

/**
 * @author RettichLP
 */
public class RenderTagEventHandler implements RenderEntityEvent {

    public static boolean showPlayerInfo = false;

    @Override
    public void onRender(Entity entity, double x, double y, double z, float v3) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            String entityPlayerName = entityPlayer.getName();
            y = y + entityPlayer.height + getDamageIndicatorValue() + getFriendTagValue(entityPlayer);

            if (showPlayerInfo) {
                RenderUtils.renderTag(getHealth(entityPlayer), new Color(255, 255, 255, 1), 0.04F,
                        false, x, y + 1.2, z);

                Color c = null;
                Faction targetPlayerFaction = Syncer.PLAYERFACTIONMAP.getOrDefault(entityPlayerName, Faction.NULL);
                if (!targetPlayerFaction.equals(Faction.NULL)
                        && ConfigElements.getNameTagStreetwar()
                        && (targetPlayerFaction.equals(ConfigElements.getNameTagStreetwar1())
                        || targetPlayerFaction.equals(ConfigElements.getNameTagStreetwar2())))
                    c = ConfigElements.getNameTagStreetwarColor().getColor(); // STREETWAR

                if (BlacklistEventHandler.BLACKLIST_MAP.getOrDefault(entityPlayerName, false))
                    c = BlacklistEventHandler.BLACKLIST_MAP.get(entityPlayerName)
                            ? Color.ORANGE // VOGELFREI
                            : Color.YELLOW; // BLACKLIST

                //if (c != null) RenderUtils.drawEntityESP(entity, c);
            }

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

    private String getHealth(EntityPlayer entityPlayer) {
        StringBuilder health = new StringBuilder();
        float baseValue = entityPlayer.getHealth();
        float absorptionValue = entityPlayer.getAbsorptionAmount();
        float healthValue = baseValue + absorptionValue;
        ColorCode colorCode = healthValue > baseValue ? ColorCode.GOLD : ColorCode.GREEN;

        return health.append(FormattingCode.RESET.getCode()).append(Message.getBuilder()
                .of(MathUtils.HEART_DECIMAL_FORMAT.format(healthValue / 2)).color(colorCode).advance()
                .create())
                .toString();
    }
}