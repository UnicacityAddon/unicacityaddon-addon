package com.rettichlp.unicacityaddon.base.services.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class CommandUtils {

    public static boolean cocaineCheck = true;
    public static boolean marihuanaCheck = true;
    public static boolean methCheck = true;
    public static boolean active = false;
    public static int lastWindowId = 0;

    private final UnicacityAddon unicacityAddon;

    public CommandUtils(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void sendQueuedCommands(List<String> commandQueue) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (commandQueue.isEmpty()) {
                    timer.cancel();
                } else {
                    CommandUtils.this.unicacityAddon.player().sendServerMessage(commandQueue.get(0));
                    commandQueue.remove(0);
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));
    }

    public Component locationHoverMessage(int x, int y, int z) {
        return Message.getBuilder().of(String.valueOf(x)).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of(String.valueOf(y)).color(ColorCode.AQUA).advance()
                .of(" | ").color(ColorCode.GRAY).advance()
                .of(String.valueOf(z)).color(ColorCode.AQUA).advance()
                .createComponent();
    }

    public void loadDrugInventory(Runnable runnable) {
        this.unicacityAddon.fileService().data().setDrugInventoryMap(new HashMap<>());

        active = cocaineCheck = marihuanaCheck = methCheck = true;
        this.unicacityAddon.player().sendServerMessage("/inv");

        runnable.run();
    }
}
