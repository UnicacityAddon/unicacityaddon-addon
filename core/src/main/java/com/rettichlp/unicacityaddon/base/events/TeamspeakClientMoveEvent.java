package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.Getter;
import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.addons.teamspeak.models.User;
import net.labymod.addons.teamspeak.util.Request;
import net.labymod.api.event.Event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author RettichLP
 */
@Getter
public class TeamspeakClientMoveEvent implements Event {

    private final String[] properties = new String[]{"client_unique_identifier", "client_unique_identifier", "client_nickname", "client_input_muted", "client_output_muted", "client_outputonly_muted",
            "client_input_hardware", "client_output_hardware", "client_meta_data", "client_is_recording", "client_database_id", "client_channel_group_id", "client_servergroups",
            "client_away", "client_away_message", "client_type", "client_flag_avatar", "client_talk_power", "client_talk_request", "client_talk_request_msg", "client_description",
            "client_is_talker", "client_is_priority_speaker", "client_unread_messages", "client_nickname_phonetic", "client_needed_serverquery_view_power", "client_icon_id",
            "client_is_channel_commander", "client_country", "client_channel_group_inherited_channel_id", "client_flag_talking", "client_is_muted", "client_volume_modificator",
            "client_version", "client_platform", "client_login_name", "client_created", "client_lastconnected", "client_totalconnections", "client_month_bytes_uploaded",
            "client_month_bytes_downloaded", "client_total_bytes_uploaded", "client_total_bytes_downloaded", "client_input_deactivated"};
    private final Channel channel;
    private String name;

    public TeamspeakClientMoveEvent(UnicacityAddon unicacityAddon, Channel channel) {
        this.channel = channel;
        this.name = unicacityAddon.player().getName();
    }

    public TeamspeakClientMoveEvent(UnicacityAddon unicacityAddon, Channel channel, User user) {
        this.channel = channel;
        try {
            this.name = getDescription(unicacityAddon, user).get();
        } catch (InterruptedException | ExecutionException e) {
            this.name = "null";
            unicacityAddon.logger().warn("Description failed");
        }
    }

    private CompletableFuture<String> getDescription(UnicacityAddon unicacityAddon, User user) {
        CompletableFuture<String> future = new CompletableFuture<>();

        unicacityAddon.teamSpeakAPI().request(Request.unknown(
                "clientvariable clid=" + user.getId() + " " + String.join(" ", this.properties),
                clientVariable -> {
                    String[] clientVariableData = clientVariable.split(" ");
                    for (String clientVariableDataEntry : clientVariableData) {
                        if (clientVariableDataEntry.startsWith("client_description=")) {
                            this.name = clientVariableDataEntry.replace("client_description=", "");
                            future.complete(clientVariableDataEntry.replace("client_description=", ""));
                            return true;
                        }
                    }

                    return true;
                }));

        return future;
    }
}