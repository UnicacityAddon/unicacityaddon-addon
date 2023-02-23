package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BannerEventHandler {

    private final UnicacityAddon unicacityAddon;

    public BannerEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.chatMessage().getPlainText());
        if (bannerStartMatcher.find()) {
//            TODO: 08.02.2023
//            RayTraceResult lookingAt = unicacityAddon.MINECRAFT.objectMouseOver;
//            if (lookingAt != null && lookingAt.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
//                BlockPos blockPos = lookingAt.getBlockPos();
//                NaviPoint naviPoint = NavigationUtils.getNearestNaviPoint(blockPos).getValue();
//                APIRequest.sendBannerAddRequest(p.getFaction(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), naviPoint.getName());
//            }
        }
    }
}
