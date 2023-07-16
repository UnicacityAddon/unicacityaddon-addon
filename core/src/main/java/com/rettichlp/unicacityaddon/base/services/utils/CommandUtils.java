package com.rettichlp.unicacityaddon.base.services.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import lombok.Setter;
import net.labymod.api.client.component.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@Getter
@Setter
public class CommandUtils {

    private boolean cocaineCheck = true;
    private boolean marihuanaCheck = true;
    private boolean methCheck = true;
    private boolean active = false;
    private int lastWindowId = 0;

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

        this.active = this.cocaineCheck = this.marihuanaCheck = this.methCheck = true;
        this.unicacityAddon.player().sendServerMessage("/inv");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, TimeUnit.SECONDS.toMillis(2));
    }
}
