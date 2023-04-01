package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.house.HouseDataListener;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCCommand
public class HouseBankDropGetAllCommand extends Command {

    private static final String usage = "/hauskasse";

    private UnicacityAddon unicacityAddon;

    public HouseBankDropGetAllCommand(UnicacityAddon unicacityAddon) {
        super("hauskasse", "hkasse");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length > 1 && arguments[1].equalsIgnoreCase("all")) {
            p.sendServerMessage("/hauskasse");
            HouseDataListener.lastCheck = System.currentTimeMillis();
            if (arguments[0].equalsIgnoreCase("get")) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int houseBankBalance = HouseBankDropGetAllCommand.this.unicacityAddon.fileManager().data().getHouseData(HouseDataListener.lastCheckedHouseNumber).getHouseBank();
                        if (houseBankBalance > 0) {
                            p.sendServerMessage("/hauskasse get " + houseBankBalance);
                        } else {
                            p.sendErrorMessage("Deine Hauskasse ist leer.");
                        }
                    }
                }, 1000);
                return true;
            } else if (arguments[0].equalsIgnoreCase("drop")) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int houseBankBalance = HouseBankDropGetAllCommand.this.unicacityAddon.fileManager().data().getHouseData(HouseDataListener.lastCheckedHouseNumber).getHouseBank();
                        int toTransfer = Math.min(15000 - houseBankBalance, HouseBankDropGetAllCommand.this.unicacityAddon.fileManager().data().getCashBalance());
                        if (toTransfer > 0) {
                            p.sendServerMessage("/hauskasse drop " + toTransfer);
                        } else {
                            p.sendErrorMessage("Deine Hauskasse ist voll oder du hast kein Geld auf der Hand.");
                        }
                    }
                }, 1000);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "get", "drop", "info")
                .addAtIndex(2, "all")
                .build();
    }
}