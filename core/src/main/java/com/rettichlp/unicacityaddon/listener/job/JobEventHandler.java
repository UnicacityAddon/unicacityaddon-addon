package com.rettichlp.unicacityaddon.listener.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.DropPosition;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

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

    private final UnicacityAddon unicacityAddon;

    public JobEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    //    @Subscribe
//    public void onPlayerInteract(PlayerInteractEvent e) {
//        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || !UnicacityAddon.isUnicacity())
//            return;
//
//        World world = e.getWorld();
//        BlockPos blockPos = e.getPos();
//
//        if (isDropState && System.currentTimeMillis() - lastUse > 1000 && onDump(blockPos)) {
//            lastUse = System.currentTimeMillis();
//            p.sendServerMessage("/dropwaste");
//            return;
//        }
//
//        TileEntity tileEntity = world.getTileEntity(blockPos);
//        if (!(tileEntity instanceof TileEntitySign))
//            return;
//        ITextComponent[] lines = ((TileEntitySign) tileEntity).signText;
//        Matcher matcher = Pattern.compile("^== (\\d+) ==$").matcher(lines[1].getUnformattedText());
//        if (matcher.find())
//            drop();
//    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.WASTE_JOB_START_PATTERN.matcher(msg).find()) {
            isWasteJob = true;
            return;
        }

        if (PatternHandler.WASTE_JOB_COLLECT_END_PATTERN.matcher(msg).find()) {
            isDropState = true;
            return;
        }

        if (PatternHandler.WASTE_JOB_END_PATTERN.matcher(msg).find()) {
            isDropState = false;
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
            p.sendServerMessage("/dropstone");
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

        if (PatternHandler.TRANSPORT_DROP_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/droptransport");
                }
            }, TimeUnit.SECONDS.toMillis(10));
        }

        if (PatternHandler.DRINK_DROP_PATTERN.matcher(msg).find()) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/dropdrink");
                }
            }, TimeUnit.SECONDS.toMillis((long) 2.5));
        }

        if (PatternHandler.PIZZA_START_PATTERN.matcher(msg).find()) {
            p.sendServerMessage("/getpizza");
            return;
        }

        Matcher pizzaPickupMatcher = PatternHandler.PIZZA_PICKUP_PATTERN.matcher(msg);
        if (pizzaPickupMatcher.find() && Integer.parseInt(pizzaPickupMatcher.group("count")) < 15)
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendServerMessage("/getpizza");
                }
            }, TimeUnit.SECONDS.toMillis(3));
    }

    private boolean onDump(FloatVector3 blockPos) {
        return blockPos.distance(new FloatVector3(DropPosition.GLAS.getX(), DropPosition.GLAS.getY(), DropPosition.GLAS.getZ())) < 3 ||
                blockPos.distance(new FloatVector3(DropPosition.WASTE.getX(), DropPosition.WASTE.getY(), DropPosition.WASTE.getZ())) < 3 ||
                blockPos.distance(new FloatVector3(DropPosition.METAL.getX(), DropPosition.METAL.getY(), DropPosition.METAL.getZ())) < 3 ||
                blockPos.distance(new FloatVector3(DropPosition.WOOD.getX(), DropPosition.WOOD.getY(), DropPosition.WOOD.getZ())) < 3;
    }

    private void drop() {
        if (isNewspaperJob)
            UnicacityAddon.PLAYER.sendServerMessage("/dropzeitung");
        else if (isWasteJob)
            UnicacityAddon.PLAYER.sendServerMessage("/getwaste");
    }
}