package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BannerEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (bannerStartMatcher.find()) {
            RayTraceResult lookingAt = UnicacityAddon.MINECRAFT.objectMouseOver;
            if (lookingAt != null && lookingAt.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                BlockPos blockPos = lookingAt.getBlockPos();
                NaviPoint naviPoint = NavigationUtils.getNearestNaviPoint(blockPos).getValue();
                APIRequest.sendBannerAddRequest(AbstractionLayer.getPlayer().getFaction(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), naviPoint.getName());
            }
        }
    }
}
