package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kifo
 */
@UCCommand(prefix = "adropammo", usage = "[weapon] [amount]")
public class ADropAmmoCommand extends UnicacityCommand {

    public static final AtomicBoolean RUNNING = new AtomicBoolean();

    private final UnicacityAddon unicacityAddon;

    public ADropAmmoCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(@NotNull String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if(arguments.length != 2 || !MathUtils.isInteger(arguments[1])) {
            sendUsage();
            return true;
        }

        Weapon weapon = Weapon.getWeaponByName(arguments[0]);
        int ammunition = Integer.parseInt(arguments[1]);

        if(weapon == null) {
            p.sendErrorMessage("Diese Waffe konnte nicht gefunden werden.");
            return true;
        }
        if(ammunition <= 0) {
            p.sendErrorMessage("Die Anzahl der Munition ist ungültig.");
            return true;
        }
        if(RUNNING.get()) {
            p.sendErrorMessage("Der Befehl wird bereits ausgeführt.");
            return true;
        }

        RUNNING.set(true);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            private int remainingAmmunition = ammunition;

            @Override
            public void run() {
                if(!RUNNING.get()) {
                    cancel();
                    return;
                }

                if(remainingAmmunition > 350) {
                    p.sendServerMessage("/dropammo " + weapon.getName() + " 350");
                    remainingAmmunition -= 350;
                } else {
                    p.sendServerMessage("/dropammo " + weapon.getName() + " " + remainingAmmunition);
                    cancel();
                    RUNNING.set(false);
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return super.complete(arguments);
    }
}
