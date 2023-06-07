//package com.rettichlp.unicacityaddon.base.teamspeak.querycommand;
//
//import com.rettichlp.unicacityaddon.UnicacityAddon;
//import lombok.Getter;
//import net.labymod.addons.teamspeak.models.Channel;
//
//import java.util.StringJoiner;
//
///**
// * @author RettichLP
// */
//@Getter
//public class ClientMoveCommand extends ClientQueryCommand {
//
//    public ClientMoveCommand(UnicacityAddon unicacityAddon, Channel targetChannel) {
//        this(unicacityAddon, targetChannel, unicacityAddon.teamSpeakAPI().getClient());
//    }
//
//    public ClientMoveCommand(UnicacityAddon unicacityAddon, Channel targetChannel, Client... clients) {
//        super(unicacityAddon, "clientmove cid=" + targetChannel.getId() + " " + parseClients(clients));
//        unicacityAddon.logger().info("Execute clientmove");
//    }
//
//    private static String parseClients(Client[] clients) {
//        StringJoiner stringJoiner = new StringJoiner("|");
//        for (Client client : clients) {
//            stringJoiner.add("clid=" + client.getId());
//        }
//
//        return stringJoiner.toString();
//    }
//
//    @Override
//    public Response getResponse() {
//        return null;
//    }
//}