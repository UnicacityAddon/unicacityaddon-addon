package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.enums.faction.ReinforcementType;
import com.rettichlp.unicacityaddon.base.events.ReinforcementAcceptedEvent;
import com.rettichlp.unicacityaddon.base.text.ChatType;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand(prefix = "reinforcement", aliases = {"callreinforcement", "reinf", "verstärkung"}, usage = "(-f|-d|-r|-rd|-e|-ed|-m|-lb|-da|-ct|-p|-b|-gn|-gnd|-t|-td|-test)")
public class ReinforcementCommand extends UnicacityCommand {

    public static int activeReinforcement = -1;

    private final UnicacityAddon unicacityAddon;

    public ReinforcementCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (!MobileListener.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            return true;
        }

        if ((p.getFaction() == Faction.FBI || p.getFaction() == Faction.RETTUNGSDIENST || p.getFaction() == Faction.POLIZEI) && !p.inDuty()) {
            p.sendErrorMessage("Du bist nicht im Dienst!");
            return true;
        }

        ReinforcementType firstType = ReinforcementType.DEFAULT;
        if (arguments.length == 1 || arguments.length == 6)
            firstType = ReinforcementType.getByArgument(arguments[arguments.length - 1]);

        ChatType chatType = firstType.getChatType(this.unicacityAddon.configuration().nameTagSetting());

        if ((arguments.length >= 5) && arguments[0].equalsIgnoreCase("ontheway")) {
            String name = arguments[1];

            if (!MathUtils.isInteger(arguments[2]) || !MathUtils.isInteger(arguments[3]) || !MathUtils.isInteger(arguments[4]))
                return true;
            int x = Integer.parseInt(arguments[2]);
            int y = Integer.parseInt(arguments[3]);
            int z = Integer.parseInt(arguments[4]);

            p.sendServerMessage(chatType.getChatCommand() + " " + name + ", ich bin zu deinem Verstärkungsruf unterwegs! (" + (int) p.getPosition().distance(new FloatVector3(x, y, z)) + " Meter entfernt)");
            p.setNaviRoute(x, y, z);

            // activity screenshot
            if (this.unicacityAddon.configuration().reinforcementSetting().screen().get())
                this.unicacityAddon.labyAPI().eventBus().fire(new ReinforcementAcceptedEvent());

            return true;
        }

        FloatVector3 position = p.getPosition();
        int posX = (int) position.getX();
        int posY = (int) position.getY();
        int posZ = (int) position.getZ();

        if (firstType.getMessage() != null)
            p.sendServerMessage(chatType.getChatCommand() + " " + firstType.getMessage());

        p.sendServerMessage(chatType.getChatCommand() + " Benötige Verstärkung! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, Arrays.stream(ReinforcementType.values()).map(ReinforcementType::getArgument).sorted().collect(Collectors.toList()))
                .addAtIndex(1, "ontheway")
                .build();
    }
}