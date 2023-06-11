package com.rettichlp.unicacityaddon.base.services.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author RettichLP
 */
public class TeamSpeakUtils {

    private final UnicacityAddon unicacityAddon;

    public TeamSpeakUtils(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    // failed
    public boolean isOnline(String description) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .flatMap(channel -> channel.getUsers().stream())
                .map(User::getDescription)
                .filter(Objects::nonNull)
                .anyMatch(s -> s.toLowerCase().contains(description.toLowerCase()));
    }

    @Nullable // failed
    public User getUserByDescription(String description) {
        for (Channel channel : this.unicacityAddon.teamSpeakAPI().getServer().getChannels()) {
            for (User user : channel.getUsers()) {
                System.out.println("USER=" + user);
                if (user != null && user.getDescription() != null) {
                    if (user.getDescription().toLowerCase().contains(description.toLowerCase())) {
                        return user;
                    }
                }
            }
        }
        return null;
//        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
//                .flatMap(channel -> channel.getUsers().stream())
//                .filter(user -> user.getDescription().toLowerCase().contains(description.toLowerCase()))
//                .findAny()
//                .orElse(null);
    }

    @Nullable
    public Channel getOwnChannel() {
        return getChannelByClientId(this.unicacityAddon.teamSpeakAPI().getClientId());
    }

    @Nullable
    public Channel getChannelByUser(User user) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .filter(channel -> channel.getUsers().contains(user))
                .findAny()
                .orElse(null);
    }

    @Nullable
    public Channel getChannelByClientId(int clid) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .filter(channel -> channel.getUsers().stream()
                        .map(User::getId)
                        .map(integer -> integer == clid)
                        .toList()
                        .contains(true))
                .findAny()
                .orElse(null);
    }

    @Nullable
    public Channel getChannelByName(String name) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .filter(channel -> channel.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }
}
