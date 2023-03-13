package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BannerListener {

    private final UnicacityAddon unicacityAddon;

    public BannerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.chatMessage().getPlainText());
        if (bannerStartMatcher.find()) {
            FloatVector3 pos = p.getPosition();
            FloatVector3 blockPos1 = new FloatVector3(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3);
            FloatVector3 blockPos2 = new FloatVector3(pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);

            FloatVector3 bannerBlockPos/* = StreamSupport.stream(BlockPos.getAllInBox(blockPos1, blockPos2).spliterator(), false)
                    .filter(blockPos -> p.getWorld().getBlockState(blockPos).getBlock().equals(Block.getBlockById(177)))
                    .findFirst()
                    .orElse(null)*/ = null; // TODO: 13.03.2023

            if (bannerBlockPos != null) {
                NaviPoint naviPoint = NavigationUtils.getNearestNaviPoint(bannerBlockPos).getValue();
                APIRequest.sendBannerAddRequest(p.getFaction(), (int) bannerBlockPos.getX(), (int) bannerBlockPos.getY(), (int) bannerBlockPos.getZ(), naviPoint.getName());
            }
        }
    }
}
