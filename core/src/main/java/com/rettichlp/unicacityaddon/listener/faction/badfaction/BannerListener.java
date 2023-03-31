package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.Navigation;
import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
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

    private FloatVector3 lastClickedBannerPosition;

    private final UnicacityAddon unicacityAddon;
    private final WorldInteractionController worldInteractionController;

    public BannerListener(UnicacityAddon unicacityAddon, WorldInteractionController worldInteractionController) {
        this.unicacityAddon = unicacityAddon;
        this.worldInteractionController = worldInteractionController;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        if (e.type().equals(ClientPlayerInteractEvent.InteractionType.INTERACT) && this.unicacityAddon.isUnicacity()) {
            FloatVector3 pos = this.worldInteractionController.getClickedBlockLocation();

            if (this.worldInteractionController.isBanner(pos)) {
                lastClickedBannerPosition = pos;
            }
        }
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.chatMessage().getPlainText());
        if (bannerStartMatcher.find() && lastClickedBannerPosition != null) {
            NaviPoint naviPoint = this.unicacityAddon.navigation().getNearestNaviPoint(lastClickedBannerPosition).getValue();
            this.unicacityAddon.api().sendBannerAddRequest(p.getFaction(), (int) lastClickedBannerPosition.getX(), (int) lastClickedBannerPosition.getY(), (int) lastClickedBannerPosition.getZ(), naviPoint.getName());
        }
    }
}
