//package com.rettichlp.unicacityaddon.base.teamspeak.querycommand;
//
//import com.rettichlp.unicacityaddon.UnicacityAddon;
//import lombok.Getter;
//import net.labymod.addons.teamspeak.models.Channel;
//
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
///**
// * @author RettichLP
// */
//@Getter
//public class ClientCommand extends ClientQueryCommand<Client> {
//
//    public ClientCommand(UnicacityAddon unicacityAddon, int clientId) {
//        this(unicacityAddon, targetChannel, unicacityAddon.teamSpeakAPI().getClient());
//    }
//
//    public ClientCommand(UnicacityAddon unicacityAddon, Channel targetChannel, Client... clients) {
//        super("clientmove cid=" + targetChannel.getId() + " " + parseClients(clients));
//        unicacityAddon.logger().info("Execute clientmove");
//    }
//
//    @Override
//    public Client getResponse() throws ExecutionException, InterruptedException {
//        String response = getFuture().get();
//
//        Map<String, String> responsemap = mapResponse();
//
//        return new Client().parse(responsemap);
//    }
//
//
//}