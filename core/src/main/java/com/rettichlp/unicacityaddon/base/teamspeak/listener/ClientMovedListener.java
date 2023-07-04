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

package com.rettichlp.unicacityaddon.base.teamspeak.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.TeamSpeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Server;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import org.jetbrains.annotations.Nullable;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class ClientMovedListener extends Listener {

    private final UnicacityAddon unicacityAddon;

    public ClientMovedListener(UnicacityAddon unicacityAddon) {
        super("notifyclientmoved");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public void execute(TeamSpeakAPI teamSpeakAPI, String[] args) {
        Integer schandlerId = this.get(args, "schandlerid", Integer.class);
        if (schandlerId == null) {
            return;
        }

        Server server = teamSpeakAPI.getServer();
        if (server == null || server.getId() != schandlerId) {
            return;
        }

        Integer clid = this.get(args, "clid", Integer.class);
        Integer cid = this.get(args, "ctid", Integer.class);
        if (clid == null || cid == null) {
            return;
        }

        Channel oldClientChannel = getOldClientChannel(clid);
        Channel newClientChannel = server.getChannel(cid);

        if (oldClientChannel != null && newClientChannel != null) {
            User user = oldClientChannel.getUser(clid);
            oldClientChannel.getUsers().remove(user);
            newClientChannel.getUsers().add(user);
        } else {
            teamSpeakAPI.controller().refreshCurrentServer(args);
        }

        unicacityAddon.labyAPI().eventBus().fire(new TeamSpeakClientMoveEvent(clid, cid, oldClientChannel));
    }

    @Nullable
    private Channel getOldClientChannel(Integer clid) {
        return this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                .filter(channel -> channel.getUser(clid) != null)
                .findFirst()
                .orElse(null);
    }
}
