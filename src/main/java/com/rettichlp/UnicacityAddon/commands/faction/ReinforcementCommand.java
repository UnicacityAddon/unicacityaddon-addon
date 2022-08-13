package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.text.ChatType;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 */
public class ReinforcementCommand extends CommandBase {

    @Override @Nonnull public String getName() {
        return "reinforcement";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/reinforcement (-d/-r/-rd/-e/-ed/-m/-lb/-da/-ct/-p/-b/-gn/-t)";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Arrays.asList("callreinforcement", "reinf", "verstärkung");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (!MobileEventHandler.hasCommunications) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Du hast keine Kommunikationsmittel!").color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        Type firstType = Type.DEFAULT;
        if (args.length == 1 || args.length == 6) firstType = Type.getByArgument(args[args.length - 1]);

        ChatType chatType = firstType.getChatType();

        if ((args.length >= 5) && args[0].equalsIgnoreCase("ontheway")) {
            String name = args[1];

            if (!MathUtils.isInteger(args[2]) || !MathUtils.isInteger(args[3]) || !MathUtils.isInteger(args[4])) return;
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            int z = Integer.parseInt(args[4]);

            p.sendChatMessage(chatType.getChatCommand() + " " + name + ", ich bin zu deinem Verstärkungsruf unterwegs! (" + (int) p.getPosition().getDistance(x, y, z) + " Meter entfernt)");
            p.setNaviRoute(x, y, z);

            return;
        }

        BlockPos position = p.getPosition();
        int posX = position.getX();
        int posY = position.getY();
        int posZ = position.getZ();

        if (firstType.message != null)
            p.sendChatMessage(chatType.getChatCommand() + " " + firstType.getMessage());

        p.sendChatMessage(chatType.getChatCommand() + " Benötige Verstärkung! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Collections.emptyList();
        if (args.length == 1) {
            tabCompletions = Arrays.stream(Type.values()).map(Type::getArgument).sorted().collect(Collectors.toList());
            tabCompletions.add("ontheway");
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        }
        return tabCompletions;
    }

    public enum Type {
        DEFAULT("-f", ChatType.FCHAT, null),
        D_CHAT("-d", ChatType.DCHAT, null),
        RAM("-r", ChatType.FCHAT, "Rammen!"),
        RAM_D("-rd", ChatType.DCHAT, "Rammen!"),
        EMERGENCY("-e", ChatType.FCHAT, "Dringend!"),
        EMERGENCY_D("-ed", ChatType.DCHAT, "Dringend!"),
        MEDIC("-m", ChatType.DCHAT, "Medic benötigt!"),
        CORPSE_GUARDING("-lb", ChatType.DCHAT, "Leichenbewachung!"),
        DRUG_REMOVAL("-da", ChatType.DCHAT, "Drogenabnahme!"),
        CONTRACT("-ct", ChatType.FCHAT, "Contract!"),
        PLANT("-p", ChatType.DCHAT, "Plant!"),
        BOMB("-b", ChatType.DCHAT, "Bombe!"),
        HOSTAGE_TAKING("-gn", ChatType.DCHAT, "Geiselnahme!"),
        TRAINING("-t", ChatType.FCHAT, "Training!"),
        TRAINING_D("-td", ChatType.DCHAT, "Training!");

        private final String argument;
        private final ChatType chatType;
        private final String message;
        private final Pattern pattern;

        Type(String argument, ChatType chatType, String message) {
            this.argument = argument;
            this.chatType = chatType;
            this.message = message;

            this.pattern = message != null ? Pattern.compile("^.+ ((?:\\[UC])*\\w+): " + message + "$") : null;
        }

        public String getArgument() {
            return argument;
        }

        public ChatType getChatType() {
            Faction alliance1 = ConfigElements.getNameTagAlliance1();
            Faction alliance2 = ConfigElements.getNameTagAlliance2();

            boolean hasAllianceFaction = !alliance1.equals(Faction.NULL) || !alliance2.equals(Faction.NULL);

            return hasAllianceFaction ? chatType : ChatType.FCHAT;
        }

        public static Type getByArgument(String s) {
            for (Type t : Type.values()) {
                if (t.getArgument().equalsIgnoreCase(s)) return t;
            }
            return Type.DEFAULT;
        }

        public String getMessage() {
            return message;
        }

        public Pattern getPattern() {
            return pattern;
        }
    }

    public static class ReinforcementType {
        private final String issuer;
        private final Type type;
        private final long time;

        public ReinforcementType(String issuer, Type type) {
            this.issuer = issuer;
            this.type = type;
            this.time = System.currentTimeMillis();
        }

        public String getIssuer() {
            return issuer;
        }

        public Type getType() {
            return type;
        }

        public long getTime() {
            return time;
        }
    }
}