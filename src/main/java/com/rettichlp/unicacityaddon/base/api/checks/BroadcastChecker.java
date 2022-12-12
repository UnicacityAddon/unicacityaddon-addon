package com.rettichlp.unicacityaddon.base.api.checks;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.models.BroadcastEntry;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class BroadcastChecker {

    private static final Timer timer = new Timer();
    private static final List<Integer> receivedBroadcasts = new ArrayList<>();

    public static void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkForBroadcast();
            }
        }, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(30));
    }

    private static void checkForBroadcast() {
        for (BroadcastEntry broadcastEntry : Syncer.getBroadcastEntryList()) {
            if (broadcastEntry.getSendTime() < System.currentTimeMillis())
                continue;
            if (receivedBroadcasts.contains(broadcastEntry.getId()))
                continue;
            receivedBroadcasts.add(broadcastEntry.getId());

            UPlayer p = AbstractionLayer.getPlayer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    p.sendEmptyMessage();
                    p.sendEmptyMessage();

                    p.sendMessage(Message.getBuilder()
                            .of("BROADCAST BY ").color(ColorCode.DARK_AQUA).bold().advance().space()
                            .of(broadcastEntry.getIssuerName().toUpperCase()).color(ColorCode.DARK_AQUA).bold().advance()
                            .createComponent());

                    p.sendMessage(Message.getBuilder()
                            .of(broadcastEntry.getBroadcast()).color(ColorCode.AQUA).advance()
                            .createComponent());

                    p.sendEmptyMessage();
                    p.sendEmptyMessage();
                }
            }, new Date(broadcastEntry.getSendTime()));
        }
    }
}