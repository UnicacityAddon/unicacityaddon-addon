package com.rettichlp.unicacityaddon.events.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.DropPosition;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@UCEvent
public class JobEventHandler {

    public static boolean isTabakJob = false;

    private boolean isNewspaperJob = false;
    private boolean isWasteJob = false;
    private boolean isDropState = false;
    private long lastUse = -1;
    private final Timer timer = new Timer();

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || !UnicacityAddon.isUnicacity()) return;
        UPlayer p = AbstractionLayer.getPlayer();

        World world = e.getWorld();
        BlockPos blockPos = e.getPos();

        if (isDropState && System.currentTimeMillis() - lastUse > 1000 && onDump(blockPos)) {
            lastUse = System.currentTimeMillis();
            p.sendChatMessage("/dropwaste");
            return;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!(tileEntity instanceof TileEntitySign)) return;
        ITextComponent[] lines = ((TileEntitySign) tileEntity).signText;
        Matcher matcher = Pattern.compile("^== (\\d+) ==$").matcher(lines[1].getUnformattedText());
        if (matcher.find()) drop();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.WASTE_JOB_START_PATTERN.matcher(msg).find()) {
            isWasteJob = true;
            return;
        }

        if (PatternHandler.WASTE_JOB_COLLECT_END_PATTERN.matcher(msg).find()) {
            isWasteJob = false;
            return;
        }

        if (PatternHandler.NEWSPAPER_JOB_START_PATTERN.matcher(msg).find()) {
            isNewspaperJob = true;
            return;
        }

        if (PatternHandler.NEWSPAPER_JOB_END_PATTERN.matcher(msg).find()) {
            isNewspaperJob = false;
            return;
        }

        if (PatternHandler.MINERS_JOB_END_PATTERN.matcher(msg).find()) {
            p.sendChatMessage("/dropstone");
            p.stopRoute();
            return;
        }

        if (PatternHandler.TABAK_DROP_PATTERN.matcher(msg).find()) {
            isTabakJob = true;
            return;
        }

        if (PatternHandler.TABAK_FINISH_PATTERN.matcher(msg).find()) {
            isTabakJob = false;
            return;
        }

        if (PatternHandler.WASTE_JOB_COLLECT_END_PATTERN.matcher(msg).find()) {
            isDropState = true;
            return;
        }

        if (PatternHandler.WASTE_JOB_END_PATTERN.matcher(msg).find()) {
            isDropState = false;
            return;
        }

        if (PatternHandler.TRANSPORT_DROP_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/droptransport");
                }
            }, TimeUnit.SECONDS.toMillis(10));
        }

        if (PatternHandler.DRINK_DROP_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/dropdrink");
                }
            }, TimeUnit.SECONDS.toMillis((long) 2.5));
        }

        if (PatternHandler.PIZZA_START_PATTERN.matcher(msg).find()) {
            p.sendChatMessage("/getpizza");
            return;
        }

        Matcher pizzaPickupMatcher = PatternHandler.PIZZA_PICKUP_PATTERN.matcher(msg);
        if (pizzaPickupMatcher.find() && Integer.parseInt(pizzaPickupMatcher.group("count")) < 15)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendChatMessage("/getpizza");
                }
            }, TimeUnit.SECONDS.toMillis(3));
    }

    private boolean onDump(BlockPos blockPos) {
        return blockPos.getDistance(DropPosition.GLAS.getX(), DropPosition.GLAS.getY(), DropPosition.GLAS.getZ()) < 3 ||
                blockPos.getDistance(DropPosition.WASTE.getX(), DropPosition.WASTE.getY(), DropPosition.WASTE.getZ()) < 3 ||
                blockPos.getDistance(DropPosition.METAL.getX(), DropPosition.METAL.getY(), DropPosition.METAL.getZ()) < 3 ||
                blockPos.getDistance(DropPosition.WOOD.getX(), DropPosition.WOOD.getY(), DropPosition.WOOD.getZ()) < 3;
    }

    private void drop() {
        UPlayer p = AbstractionLayer.getPlayer();
        if (isNewspaperJob)
            p.sendChatMessage("/dropzeitung");
        else if (isWasteJob)
            p.sendChatMessage("/getwaste");
    }
}