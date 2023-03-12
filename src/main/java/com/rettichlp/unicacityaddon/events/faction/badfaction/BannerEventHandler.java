package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.stream.StreamSupport;

/**
 * @author RettichLP
 */
@UCEvent
public class BannerEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher bannerStartMatcher = PatternHandler.BANNER_SPRAYED_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (bannerStartMatcher.find()) {
            BlockPos pos = p.getPosition();
            BlockPos blockPos1 = new BlockPos(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3);
            BlockPos blockPos2 = new BlockPos(pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);

            BlockPos bannerBlockPos = StreamSupport.stream(BlockPos.getAllInBox(blockPos1, blockPos2).spliterator(), false)
                    .filter(blockPos -> p.getWorld().getBlockState(blockPos).getBlock().equals(Block.getBlockById(177)))
                    .findFirst()
                    .orElse(null);

            if (bannerBlockPos != null) {
                NaviPoint naviPoint = NavigationUtils.getNearestNaviPoint(bannerBlockPos).getValue();
                APIRequest.sendBannerAddRequest(AbstractionLayer.getPlayer().getFaction(), bannerBlockPos.getX(), bannerBlockPos.getY(), bannerBlockPos.getZ(), naviPoint.getName());
            }
        }
    }
}
