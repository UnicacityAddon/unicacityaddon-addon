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

import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Server;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class ConnectStatusChangeListener extends Listener {

    public ConnectStatusChangeListener() {
        super("notifyconnectstatuschange");
    }

    @Override
    public void execute(TeamSpeakAPI teamSpeakAPI, String[] args) {
        Integer schandlerId = this.get(args, "schandlerid", Integer.class);
        if (schandlerId == null) {
            return;
        }

        String status = this.get(args, "status", String.class);
        if (status == null) {
            return;
        }

        Server server = teamSpeakAPI.controller().getServer(schandlerId);
        if (status.equals("disconnected")) {
            if (server != null) {
                teamSpeakAPI.controller().getServers().remove(server);

                Server selectedServer = teamSpeakAPI.getServer();
                if (selectedServer != null && selectedServer.getId() == schandlerId) {
                    teamSpeakAPI.controller().setServer(null);
                }
            }

            return;
        }

        if (status.equals("connection_established")) {
            if (server == null) {
                server = new Server(schandlerId);
                teamSpeakAPI.controller().getServers().add(server);
            }

            teamSpeakAPI.controller().refreshCurrentServer(schandlerId);
        }
    }
}
