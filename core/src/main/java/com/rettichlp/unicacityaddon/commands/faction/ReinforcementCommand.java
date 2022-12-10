package com.rettichlp.unicacityaddon.commands.faction;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.ReinforcementType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ChatType;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ReinforcementCommand extends Command {

    private static final String usage = "/reinforcement (-d/-r/-rd/-e/-ed/-m/-lb/-da/-ct/-p/-b/-gn/-t)";

    @Inject
    private ReinforcementCommand() {
        super("reinforcement", "callreinforcement", "reinf", "verstärkung");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (!MobileEventHandler.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            return true;
        }

        ReinforcementType firstType = ReinforcementType.DEFAULT;
        if (arguments.length == 1 || arguments.length == 6)
            firstType = ReinforcementType.getByArgument(arguments[arguments.length - 1]);

        ChatType chatType = firstType.getChatType();

        if ((arguments.length >= 5) && arguments[0].equalsIgnoreCase("ontheway")) {
            String name = arguments[1];

            if (!MathUtils.isInteger(arguments[2]) || !MathUtils.isInteger(arguments[3]) || !MathUtils.isInteger(arguments[4]))
                return true;
            int x = Integer.parseInt(arguments[2]);
            int y = Integer.parseInt(arguments[3]);
            int z = Integer.parseInt(arguments[4]);

            p.sendChatMessage(chatType.getChatCommand() + " " + name + ", ich bin zu deinem Verstärkungsruf unterwegs! (" + (int) p.getPosition().distance(new FloatVector3(x, y, z)) + " Meter entfernt)");
            p.setNaviRoute(x, y, z);
            return true;
        }

        FloatVector3 position = p.getPosition();
        float posX = position.getX();
        float posY = position.getY();
        float posZ = position.getZ();

        if (firstType.getMessage() != null)
            p.sendChatMessage(chatType.getChatCommand() + " " + firstType.getMessage());

        p.sendChatMessage(chatType.getChatCommand() + " Benötige Verstärkung! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, Arrays.stream(ReinforcementType.values()).map(ReinforcementType::getArgument).sorted().collect(Collectors.toList()))
                .addAtIndex(1, "ontheway")
                .build();
    }
}