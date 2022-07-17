package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.command.UCCommand;
import com.rettichlp.UnicacityAddon.base.command.UnicacityCommand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CallReinforcementCommand.java">UCUtils by paulzhng</a>
 **/
@SideOnly(Side.CLIENT)
public class ReinforcementCommand implements UnicacityCommand {

    private Type firstType = Type.DEFAULT;

    @Override
    @UCCommand(value = "reinforcement", usage = "/%label%")
    public boolean onCommand(UPlayer p, List<String> args) {

        Arrays.stream(Type.values()).forEach(type -> {
            if (args.size() == 1) {
                if (type.getArgument().equalsIgnoreCase(args.get(0))) firstType = type;
            } else if (args.size() == 6) {
                if (type.getArgument().equalsIgnoreCase(args.get(0))) firstType = type;
            }
        });

        String chatType = firstType.getChatType();
        // /reinforcement -d
        // /reinforcement ontheway name x y z (-d)

        if ((args.size() == 6) && args.get(0).equalsIgnoreCase("ontheway")) {
            String name = args.get(1);
            Integer x = Integer.parseInt(args.get(2));
            Integer y = Integer.parseInt(args.get(3));
            Integer z = Integer.parseInt(args.get(4));

            if (name == null || x == null || y == null || z == null) return false;

            p.sendChatMessage("/" + chatType + " " + name + ", ich bin zu deinem Verstärkungsruf unterwegs! (" + (int) p.getPosition().getDistance(x, y, z) + " Meter entfernt)");
            return true;
        }

        BlockPos position = p.getPosition();
        int posX = position.getX();
        int posY = position.getY();
        int posZ = position.getZ();

        if (firstType.message != null)
            p.sendChatMessage("/" + chatType + " " + firstType.getMessage());

        p.sendChatMessage("/" + chatType + " Benötige Verstärkung! -> X: " + posX + " | Y: " + posY + " | Z: " + posZ);

        return true;
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

