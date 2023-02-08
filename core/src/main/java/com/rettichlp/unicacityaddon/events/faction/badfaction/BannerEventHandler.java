package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class BannerEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.chatMessage().getPlainText());
        if (bannerStartMatcher.find()) {
//            TODO: 08.02.2023
//            RayTraceResult lookingAt = UnicacityAddon.MINECRAFT.objectMouseOver;
//            if (lookingAt != null && lookingAt.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
//                BlockPos blockPos = lookingAt.getBlockPos();
//                NaviPoint naviPoint = NavigationUtils.getNearestNaviPoint(blockPos).getValue();
//                APIRequest.sendBannerAddRequest(AbstractionLayer.getPlayer().getFaction(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), naviPoint.getName());
//            }
        }
    }
}
