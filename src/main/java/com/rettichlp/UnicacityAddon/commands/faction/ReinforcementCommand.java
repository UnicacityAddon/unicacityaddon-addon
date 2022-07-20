package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 **/
public class ReinforcementCommand extends CommandBase {

    @Override @Nonnull public String getName() {
        return "reinforcement";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/reinforcement (-d/-r/-rd/-e/-ed/-m/-lb/-da/-ct/-p)";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Arrays.asList("callreinforcement", "reinf", "verstärkung");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        Type firstType = Type.DEFAULT;
        if (args.length == 1 || args.length == 6) firstType = Type.getByArgument(args[args.length - 1]);

        String chatType = firstType.getChatType();

        if ((args.length >= 5) && args[0].equalsIgnoreCase("ontheway")) {
            String name = args[1];

            if (!MathUtils.isInteger(args[2], 10) || !MathUtils.isInteger(args[3], 10) || !MathUtils.isInteger(args[4], 10)) return;
            int x = Integer.parseInt(args[2]);
            int y = Integer.parseInt(args[3]);
            int z = Integer.parseInt(args[4]);

            p.sendChatMessage("/" + chatType + " " + name + ", ich bin zu deinem Verstärkungsruf unterwegs! (" + (int) p.getPosition().getDistance(x, y, z) + " Meter entfernt)");
            p.sendChatMessage("/stoproute");
            p.sendChatMessage("/navi " + x + "/" + y + "/" + z);

            return;
        }

        BlockPos position = p.getPosition();
        int posX = position.getX();
        int posY = position.getY();
        int posZ = position.getZ();

        if (firstType.message != null)
            p.sendChatMessage("/" + chatType + " " + firstType.getMessage());

        p.sendChatMessage("/" + chatType + " Benötige Verstärkung! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Collections.emptyList();
        if (args.length == 1) {
            tabCompletions = Arrays.stream(Type.values()).map(Type::getArgument).sorted().collect(Collectors.toList());
            tabCompletions.add("ontheway");
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            return tabCompletions;
        }
    }

    public enum Type {
        DEFAULT("-f", "f", null),
        D_CHAT("-d", "d", null),
        RAM("-r", "f", "Rammen!"),
        RAM_D("-rd", "d", "Rammen!"),
        EMERGENCY("-e", "f", "Dringend!"),
        EMERGENCY_D("-ed", "d", "Dringend!"),
        MEDIC("-m", "d", "Medic benötigt!"),
        CORPSE_GUARDING("-lb", "d", "Leichenbewachung!"),
        DRUG_REMOVAL("-da", "d", "Drogenabnahme!"),
        CONTRACT("-ct", "f", "Contract!"),
        PLANT("-p", "d", "Plant!");

        private final String argument;
        private final String chatType;
        private final String message;
        private final Pattern pattern;

        Type(String argument, String chatType, String message) {
            this.argument = argument;
            this.chatType = chatType;
            this.message = message;

            this.pattern = message != null ? Pattern.compile("^.+ ((?:\\[UC])*[a-zA-Z0-9_]+): " + message + "$") : null;
        }

        public String getArgument() {
            return argument;
        }

        public String getChatType() {
            /* TODO: Check if alliance exists, otherwise send CORPSE_GUARDING to f chat
            if (!(alliance) && (this == Type.CORPSE_GUARDING)) return "f"; */
            return chatType;
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

