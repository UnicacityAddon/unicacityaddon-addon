package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.event.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCEvent
public class BroadcastListener {

    private final Timer timer = new Timer();
    private final List<Integer> receivedBroadcasts = new ArrayList<>();

    private final UnicacityAddon unicacityAddon;

    public BroadcastListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_30)) {
            checkForBroadcast();
        }
    }

    private void checkForBroadcast() {
        new Thread(() -> this.unicacityAddon.api().sendBroadcastQueueRequest().stream()
                .filter(broadcast -> broadcast.getSendTime() >= System.currentTimeMillis())
                .filter(broadcast -> !receivedBroadcasts.contains(broadcast.getId()))
                .forEach(broadcast -> {
                    receivedBroadcasts.add(broadcast.getId());

                    AddonPlayer p = this.unicacityAddon.player();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            p.sendEmptyMessage();
                            p.sendEmptyMessage();

                            p.sendMessage(Message.getBuilder()
                                    .of("BROADCAST BY ").color(ColorCode.DARK_AQUA).bold().advance().space()
                                    .of(broadcast.getIssuerName().toUpperCase()).color(ColorCode.DARK_AQUA).bold().advance()
                                    .createComponent());

                            p.sendMessage(Message.getBuilder()
                                    .of(broadcast.getBroadcast()).color(ColorCode.AQUA).advance()
                                    .createComponent());

                            p.sendEmptyMessage();
                            p.sendEmptyMessage();
                        }
                    }, new Date(broadcast.getSendTime()));
                })).start();
    }
}