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

import com.rettichlp.unicacityaddon.base.teamspeak.DefaultTeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultServer;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ConnectStatusChange extends DefaultListener {

  public ConnectStatusChange() {
    super("notifyconnectstatuschange");
  }

  @Override
  public void execute(DefaultTeamSpeakAPI teamSpeakAPI, String[] args) {
    Integer schandlerId = this.get(args, "schandlerid", Integer.class);
    if (schandlerId == null) {
      return;
    }

    String status = this.get(args, "status", String.class);
    if (status == null) {
      return;
    }

    DefaultServer server = teamSpeakAPI.controller().getServer(schandlerId);
    if (status.equals("disconnected")) {
      if (server != null) {
        teamSpeakAPI.controller().getServers().remove(server);

        DefaultServer selectedServer = teamSpeakAPI.getSelectedServer();
        if (selectedServer != null && selectedServer.getId() == schandlerId) {
          teamSpeakAPI.controller().setSelectedServer(null);
        }
      }

      return;
    }

    if (status.equals("connection_established")) {
      if (server == null) {
        server = new DefaultServer(schandlerId);
        teamSpeakAPI.controller().getServers().add(server);
      }

      teamSpeakAPI.controller().refreshCurrentServer(schandlerId);
    }
  }
}
