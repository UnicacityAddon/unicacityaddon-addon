package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class TimerEntry {

    public static Map<Long, TimerEntry> ACTIVE_TIMERS;
    private final String name;
    private final long duration;
    private final TimeUnit timeUnit;
    private final long id;
    private final Timer timer = new Timer();

    public TimerEntry(String name, String durationString) {
        this.name = name;
        this.duration = getDuration(durationString);
        this.timeUnit = getTimeUnit(durationString);
        this.id = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public String getTimeLeftString() {
        long timeLeft = timeUnit.toMillis(duration) - (System.currentTimeMillis() - id); // id = starttime
        long hh = TimeUnit.MILLISECONDS.toHours(timeLeft);
        long mm = TimeUnit.MILLISECONDS.toMinutes(timeLeft) % 60;
        long ss = TimeUnit.MILLISECONDS.toSeconds(timeLeft) % 60;
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }

    public long getId() {
        return id;
    }

    public void start(UnicacityAddon unicacityAddon) {
        ACTIVE_TIMERS.put(id, this);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AddonPlayer p = unicacityAddon.player();
                unicacityAddon.soundController().playTimerExpiredSound();
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Timer").color(ColorCode.AQUA).advance().space()
                        .of(name).color(ColorCode.DARK_AQUA).advance().space()
                        .of("ist abgelaufen!").color(ColorCode.AQUA).advance()
                        .createComponent());
                p.sendEmptyMessage();
                ACTIVE_TIMERS.remove(id);
            }
        }, timeUnit.toMillis(duration));
    }

    public void stop() {
        timer.cancel();
        ACTIVE_TIMERS.remove(id);
    }

    private long getDuration(String durationString) {
        return Long.parseLong(durationString.replaceAll("\\D", ""));
    }

    private TimeUnit getTimeUnit(String durationString) {
        return durationString.toUpperCase().endsWith("H") ? TimeUnit.HOURS :
                durationString.toUpperCase().endsWith("M") ? TimeUnit.MINUTES :
                        TimeUnit.SECONDS;
    }
}
