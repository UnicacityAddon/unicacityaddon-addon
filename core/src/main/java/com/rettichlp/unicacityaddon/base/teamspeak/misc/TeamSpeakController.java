/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.rettichlp.unicacityaddon.base.teamspeak.misc;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Server;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.teamspeak.util.ArgumentParser;
import com.rettichlp.unicacityaddon.base.teamspeak.util.Request;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class TeamSpeakController {

    private final TeamSpeakAPI teamSpeakAPI;
    @Getter
    private final List<Server> servers;
    @Getter
    private Server server;

    private final UnicacityAddon unicacityAddon;

    public TeamSpeakController(UnicacityAddon unicacityAddon, TeamSpeakAPI teamSpeakAPI) {
        this.unicacityAddon = unicacityAddon;
        this.teamSpeakAPI = teamSpeakAPI;
        this.servers = new ArrayList<>();
    }

    public void setServer(Server server) {
        if (server == null) {
            this.server = null;
            return;
        }

        if (!this.servers.contains(server)) {
            throw new IllegalArgumentException("Server is not in the list of servers!");
        }

        this.server = server;
        this.teamSpeakAPI.clientNotifyRegister(server.getId());
    }

    public Server getServer(int id) {
        for (Server server : this.servers) {
            if (server.getId() == id) {
                return server;
            }
        }

        return null;
    }

    public void refreshCurrentServer(String[] args) {
        Integer schandlerId = this.get(args, "schandlerid", Integer.class);
        if (schandlerId == null) {
            return;
        }

        Server server = teamSpeakAPI.getServer();
        if (server == null || server.getId() != schandlerId) {
            return;
        }

        refreshCurrentServer(schandlerId);
    }

    public void refreshCurrentServer(int schandlerId) {
        this.teamSpeakAPI.request(Request.firstParamEquals(
                "use " + schandlerId,
                "selected",
                response -> {
                    this.teamSpeakAPI.query("whoami");
                    this.refreshCurrentServer0(schandlerId);
                }
        ));
    }

    public void refreshCurrentServer0(int schandlerId) {
        Server server = this.teamSpeakAPI.getServer(schandlerId);
        if (server == null) {
            return;
        }

        this.teamSpeakAPI.request(Request.unknown("channellist", channelListAnswer -> {
            String[] channels = channelListAnswer.split("\\|");
            for (String rawChannel : channels) {
                if (!rawChannel.startsWith("cid=")) {
                    return false;
                }
            }

            server.getChannels().clear();
            this.teamSpeakAPI.controller().setServer(server);
            for (String rawChannel : channels) {
                String[] channel = rawChannel.split(" ");
                Integer channelId = this.get(channel, "cid", Integer.class);
                String channelName = this.get(channel, "channel_name", String.class);
                Integer channelPid = this.get(channel, "pid", Integer.class);
                if (channelId != null) {
                    Channel defaultChannel = server.addChannel(channelId);
                    if (channelName != null) {
                        defaultChannel.setName(channelName);
                    }
                    if (channelPid != null) {
                        defaultChannel.setPid(channelPid);
                    }
                }
            }

            this.teamSpeakAPI.request(Request.unknown("clientlist -voice", clientListAnswer -> {
                String[] clients = clientListAnswer.split("\\|");
                for (String rawClient : clients) {
                    if (!rawClient.startsWith("clid=")) {
                        return false;
                    }
                }

                for (String rawClient : clients) {
                    String[] client = rawClient.split(" ");
                    Integer clientId = this.get(client, "clid", Integer.class);
                    Integer channelId = this.get(client, "cid", Integer.class);
                    if (clientId != null && channelId != null) {
                        Channel channel = server.getChannel(channelId);
                        if (channel != null) {
                            User user = new User(clientId);

                            this.teamSpeakAPI.request(Request.firstParamStartsWith(
                                    "clientvariable clid=" + clientId + " client_description",
                                    "clid=" + clientId,
                                    clientProperties -> {
                                        String[] arguments = clientProperties.split(" ");
                                        String description = ArgumentParser.parse(arguments, "client_description", String.class);

                                        user.setDescription(description);
                                    }));

                            channel.addUser(user);
                        }
                    }
                }

                return true;
            }));

            return true;
        }));
    }

    private <T> T get(String[] arguments, String identifier, Class<T> clazz) {
        return ArgumentParser.parse(arguments, identifier, clazz);
    }

    public boolean move(int cid) {
        return move(teamSpeakAPI.getClientId(), cid);
    }

    public boolean move(int clid, int cid) {
        boolean success;

        try {
            success = moveFuture(clid, cid).get();
        } catch (InterruptedException | ExecutionException e) {
            success = false;
        }

        return success;
    }

    private CompletableFuture<Boolean> moveFuture(int clid, int cid) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.teamSpeakAPI.request(Request.firstParamEquals(
                "clientmove cid=" + cid + " clid=" + clid,
                "error",
                response -> {
                    boolean result = response.equalsIgnoreCase("error id=0 msg=ok");
                    future.complete(result);
                }));

        return future;
    }

    public boolean isOnline(String description) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .flatMap(channel -> channel.getUsers().stream())
                .map(User::getDescription)
                .filter(Objects::nonNull)
                .anyMatch(s -> s.toLowerCase().contains(description.toLowerCase()));
    }

    @Nullable
    public User getUserByDescription(String description) {
        for (Channel channel : this.unicacityAddon.teamSpeakAPI().getServer().getChannels()) {
            for (User user : channel.getUsers()) {
                if (user != null && user.getDescription() != null) {
                    if (user.getDescription().toLowerCase().contains(description.toLowerCase())) {
                        return user;
                    }
                }
            }
        }
        return null;
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
        Map<String, Channel> stringChannelMap = new HashMap<>();

        for (Channel channel : this.unicacityAddon.teamSpeakAPI().getServer().getChannels()) {
            String channelName = channel.getName();
            if (channelName.startsWith("[cspacer"))
                continue;
            if (channelName.startsWith("[spacer"))
                continue;

            stringChannelMap.put(channelName.replace("»", "").trim().replace(" ", "-"), channel);
        }

        Faction faction = this.unicacityAddon.player().getFaction();
        Channel channel;
        if (name.equalsIgnoreCase("Öffentlich") && !faction.equals(Faction.NULL)) {
            channel = this.unicacityAddon.teamSpeakAPI().getServer().getChannel(faction.getPublicChannelId());
        } else if (name.equalsIgnoreCase("Abwesend") && !faction.equals(Faction.NULL)) {
            channel = this.unicacityAddon.teamSpeakAPI().getServer().getChannel(faction.getAbtractedChannelID());
        } else {
            channel = this.unicacityAddon.utilService().text().getMostMatching(stringChannelMap.values(), name, (chn) -> chn.getName().replace("»", "").trim().replace(" ", "-"));
        }
        return channel;
    }
}
