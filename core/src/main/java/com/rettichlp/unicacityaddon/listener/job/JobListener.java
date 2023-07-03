package com.rettichlp.unicacityaddon.listener.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.DropLocation;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class JobListener {

    public static boolean isTabakJob = false;
    private final Timer timer = new Timer();
    private boolean isNewspaperJob = false;
    private boolean isWasteJob = false;
    private boolean isDropState = false;
    private long lastUse = -1;

    private final UnicacityAddon unicacityAddon;

    public JobListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        ClientPlayerInteractEvent.InteractionType interactionType = e.type();

        if (interactionType.equals(ClientPlayerInteractEvent.InteractionType.INTERACT) && this.unicacityAddon.utilService().isUnicacity()) {
            AddonPlayer p = this.unicacityAddon.player();

            FloatVector3 location = this.unicacityAddon.worldInteractionController().getClickedBlockLocation();

            if (isDropState && System.currentTimeMillis() - lastUse > 1000 && location != null && onDump(location)) {
                lastUse = System.currentTimeMillis();
                p.sendServerMessage("/dropwaste");
                return;
            }

            boolean isHouseNumberSign = location != null && this.unicacityAddon.worldInteractionController().isHouseNumberSign(location);

            if (isHouseNumberSign) {
                drop();
            }
        }
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
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

    private boolean onDump(FloatVector3 location) {
        return location.distance(new FloatVector3(DropLocation.GLAS.getX(), DropLocation.GLAS.getY(), DropLocation.GLAS.getZ())) < 3 ||
                location.distance(new FloatVector3(DropLocation.WASTE.getX(), DropLocation.WASTE.getY(), DropLocation.WASTE.getZ())) < 3 ||
                location.distance(new FloatVector3(DropLocation.METAL.getX(), DropLocation.METAL.getY(), DropLocation.METAL.getZ())) < 3 ||
                location.distance(new FloatVector3(DropLocation.WOOD.getX(), DropLocation.WOOD.getY(), DropLocation.WOOD.getZ())) < 3;
    }

    private void drop() {
        if (isNewspaperJob)
            this.unicacityAddon.player().sendServerMessage("/dropzeitung");
        else if (isWasteJob)
            this.unicacityAddon.player().sendServerMessage("/getwaste");
    }
}