package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rettichlp.UnicacityAddon.base.utils.DebugUtils.Debug;

/**
 * @author RettichLP
 */
@UCEvent
public class HouseSignEventHandler {

    private boolean isActiveNewspaperJob = false;
    private boolean isActiveWasteJob = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || !UnicacityAddon.isUnicacity()) return;

        World world = e.getWorld();
        BlockPos blockPos = e.getPos();

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!(tileEntity instanceof TileEntitySign)) return;

        ITextComponent[] lines = ((TileEntitySign) tileEntity).signText;
        Matcher matcher = Pattern.compile("^== (\\d+) ==$").matcher(lines[1].getUnformattedText());
        if (!matcher.find()) return;

        dropNewspaper();
        dropWaste();

        Debug(HouseSignEventHandler.class, "Clicked house number: " + Integer.parseInt(matcher.group(1)));
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher wasteJobStartMatcher = PatternHandler.WASTE_JOB_START_PATTERN.matcher(msg);
        if (wasteJobStartMatcher.find()) {
            isActiveWasteJob = true;
            return;
        }

        Matcher wasteJobEndMatcher = PatternHandler.WASTE_JOB_END_PATTERN.matcher(msg);
        if (wasteJobEndMatcher.find()) {
            isActiveWasteJob = false;
            return;
        }

        Matcher newspaperJobStartMatcher = PatternHandler.NEWSPAPER_JOB_START_PATTERN.matcher(msg);
        if (newspaperJobStartMatcher.find()) {
            isActiveNewspaperJob = true;
            return;
        }

        Matcher newspaperJobEndMatcher = PatternHandler.NEWSPAPER_JOB_END_PATTERN.matcher(msg);
        if (newspaperJobEndMatcher.find()) {
            isActiveNewspaperJob = false;
        }
    }

    private void dropNewspaper() {
        if (isActiveNewspaperJob) AbstractionLayer.getPlayer().sendChatMessage("/dropzeitung");
    }

    private void dropWaste() {
        if (isActiveWasteJob) AbstractionLayer.getPlayer().sendChatMessage("/getwaste");
    }
}