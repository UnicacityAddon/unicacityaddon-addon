package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class BannerListener {

    private FloatVector3 lastClickedBannerLocation;

    private final UnicacityAddon unicacityAddon;

    public BannerListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        if (e.type().equals(ClientPlayerInteractEvent.InteractionType.INTERACT) && this.unicacityAddon.utilService().isUnicacity()) {
            FloatVector3 location = this.unicacityAddon.worldInteractionController().getClickedBlockLocation();

            if (location != null && this.unicacityAddon.worldInteractionController().isBanner(location)) {
                lastClickedBannerLocation = location;
            }
        }
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.chatMessage().getPlainText());
        if (bannerStartMatcher.find() && lastClickedBannerLocation != null) {
            NaviPoint naviPoint = this.unicacityAddon.navigationService().getNearestNaviPoint(lastClickedBannerLocation).getValue();
            this.unicacityAddon.api().sendBannerAddRequest(p.getFaction(), (int) lastClickedBannerLocation.getX(), (int) lastClickedBannerLocation.getY(), (int) lastClickedBannerLocation.getZ(), naviPoint.getName());
        }
    }
}
