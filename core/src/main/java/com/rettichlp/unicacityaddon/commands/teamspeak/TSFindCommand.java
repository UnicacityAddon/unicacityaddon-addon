package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.teamspeak.TSChannelCategory;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.addons.teamspeak.models.User;
import net.labymod.addons.teamspeak.util.ArgumentParser;
import net.labymod.addons.teamspeak.util.Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand(prefix = "tsfind", onlyOnUnicacity = false, usage = "[Spieler]")
public class TSFindCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TSFindCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        new Thread(() -> {
            AddonPlayer p = this.unicacityAddon.player();

            if (arguments.length < 1) {
                sendUsage();
                return;
            }

            Map<String, Channel> descriptionChannelMap = new HashMap<>();
            try {
                Map<Integer, Integer> clientIdChannelIdMap = clientIdChannelIdMap().get();

                descriptionChannelMap = clientIdChannelIdMap.entrySet().stream()
                        .collect(Collectors.toMap(integerIntegerEntry -> {
                            try {
                                return clientDescription(integerIntegerEntry.getKey()).get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }, integerIntegerEntry -> channel(integerIntegerEntry.getValue())));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            this.unicacityAddon.utils().debug("SERVERS=" + this.unicacityAddon.teamSpeakAPI().getServers());
            this.unicacityAddon.utils().debug("SERVER=" + this.unicacityAddon.teamSpeakAPI().getSelectedServer());
            this.unicacityAddon.utils().debug("CHANNELS=" + this.unicacityAddon.teamSpeakAPI().getSelectedServer().getChannels().stream().map(Channel::getName).toList());
            this.unicacityAddon.utils().debug("CHANNEL=" + this.unicacityAddon.teamSpeakAPI().getSelectedServer().getSelectedChannel().getName());

            this.unicacityAddon.utils().debug("USERS=" + this.unicacityAddon.teamSpeakAPI().getSelectedServer().getSelectedChannel().getUsers().stream().map(User::getNickname).toList());


            // get channels as DefaultChannel
//            List<DefaultChannel> channels = this.unicacityAddon.teamSpeakAPI().getServers().stream()
//                    .flatMap(server -> server.getChannels().stream())
//                    .map(Channel::getId)
//                    .map(cid -> this.unicacityAddon.teamSpeakAPI().getSelectedServer().getChannel(cid))
//                    .toList();

//            this.unicacityAddon.utils().debug(channels.toString());

            // refresh users in DefaultChannels
//            channels.forEach(defaultChannel -> this.unicacityAddon.teamSpeakAPI().controller().refreshUsers(defaultChannel));

            // get users as User from DefaultChannels
//            List<User> users = channels.stream()
//                    .flatMap(defaultChannel -> defaultChannel.getUsers().stream())
//                    .toList();

//            this.unicacityAddon.utils().debug(users.toString());

//            this.unicacityAddon.teamSpeakAPI().getServers().stream()
//                    .flatMap(server -> server.getChannels().stream())
//                    .flatMap(channel -> channel.getUsers().stream())
//                    .map(user -> user.getDescription(this.unicacityAddon.teamSpeakAPI()))
//                    .map(stringCompletableFuture -> {
//                        String description = "Unbekannt";
//                        try {
//                            description = stringCompletableFuture.get();
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        return description;
//                    })
//                    .forEach(System.out::println);

            Optional<Map.Entry<String, Channel>> optionalDescriptionChannelEntry = descriptionChannelMap.entrySet().stream()
                    .filter(stringChannelEntry -> stringChannelEntry.getKey().equalsIgnoreCase(arguments[0]))
                    .findFirst();

            if (optionalDescriptionChannelEntry.isPresent()) {
                Channel channel = optionalDescriptionChannelEntry.get().getValue();
                p.sendMessage(Message.getBuilder()
                        .prefix()
                        .of(arguments[0]).color(ColorCode.AQUA).advance().space()
                        .of("befindet sich im Channel").color(ColorCode.GRAY).advance().space()
                        .of(channel.getName()).color(ColorCode.AQUA).advance()
                        .add(getChannelCategoryString(channel.getId()))
                        .of(".").color(ColorCode.GRAY).advance()
                        .createComponent());
            } else {
                p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }

    private String getChannelCategoryString(int pid) {
        String channelCategory = getChannelCategoryByChannelID(pid);
        return channelCategory == null ? "" : Message.getBuilder().space()
                .of("(").color(ColorCode.GRAY).advance()
                .of(channelCategory).color(ColorCode.AQUA).advance()
                .of(")").color(ColorCode.GRAY).advance()
                .create();
    }

    private String getChannelCategoryByChannelID(int channelPid) {
        return Arrays.stream(TSChannelCategory.values())
                .filter(tsChannelCategory -> tsChannelCategory.getPid() == channelPid)
                .findFirst()
                .map(TSChannelCategory::getCategoryName)
                .orElse(null);
    }


    private CompletableFuture<Map<Integer, Integer>> clientIdChannelIdMap() {
        CompletableFuture<Map<Integer, Integer>> completableFuture = new CompletableFuture<>();

        this.unicacityAddon.teamSpeakAPI().request(Request.unknown(
                "clientlist -voice",
                clientList -> {

                    System.out.println("CLLIST=" + clientList);

//                    CLLIST=clid=10 cid=79 client_database_id=5090 client_nickname=[UCPD]\s[00-31]Albaneraufbaustl client_type=0 client_flag_talking=0 client_input_muted=0 client_output_muted=0 client_input_hardware=1 client_output_hardware=1 client_talk_power=45 client_is_talker=0 client_is_priority_speaker=0 client_is_recording=0 client_is_channel_commander=0 client_is_muted=0
//                        |clid=268 cid=41109 client_database_id=5155 client_nickname=[SWAT]\s[04-16]\szNxcki client_type=0 client_flag_talking=0 client_input_muted=0 client_output_muted=0 client_input_hardware=1 client_output_hardware=1 client_talk_power=15 client_is_talker=0 client_is_priority_speaker=0 client_is_recording=0 client_is_channel_commander=0 client_is_muted=0|



                    String[] clients = clientList.split("\\|");

                    Map<Integer, Integer> clientIdChannelIdMap = new HashMap<>();

                    for (String client : clients) {
                        Matcher clidMatcher = Pattern.compile("clid=(\\d+)").matcher(client);
                        Matcher cidMatcher = Pattern.compile("cid=(\\d+)").matcher(client);
                        if (clidMatcher.find() && cidMatcher.find()) {
                            String clid = clidMatcher.group(1);
                            String cid = cidMatcher.group(1);
                            clientIdChannelIdMap.put(Integer.valueOf(clid), Integer.valueOf(cid));
                        }
                    }

                    completableFuture.complete(clientIdChannelIdMap);

                    return true;
                }));

        return completableFuture;
    }

    private CompletableFuture<String> clientDescription(int clid) {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        this.unicacityAddon.teamSpeakAPI().request(Request.unknown(
                "clientvariable clid=" + clid + " client_description",
                clientProperties -> {
                    String[] arguments = clientProperties.split(" ");

                    String description = ArgumentParser.parse(arguments, "client_description", String.class);
                    completableFuture.complete(description != null ? description : "Unbekannt");

                    return true;
                }));

        return completableFuture;
    }



//    private CompletableFuture<Integer> clientChannelId(int clid) {
//        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
//
//        this.unicacityAddon.teamSpeakAPI().request(Request.unknown(
//                "clientinfo cid=" + clid,
//                channelInfo -> {
//                    System.out.println("CLIENTINFO: " + channelInfo);
////                    String[] arguments = channelInfo.split(" ");
////
////                    String description = ArgumentParser.parse(arguments, "client_description", String.class);
////                    completableFuture.complete(description != null ? description : "Unbekannt");
//
//                    completableFuture.complete(0);
//                    return true;
//                }));
//
//        return completableFuture;
//    }

    private Channel channel(int cid) {
        return this.unicacityAddon.teamSpeakAPI().getSelectedServer().getChannels().stream()
                .filter(channel -> channel.getId() == cid)
                .findFirst()
                .orElse(null);
    }

//    private Client client(int i) {
//        return new Client(i);
//    }
}
