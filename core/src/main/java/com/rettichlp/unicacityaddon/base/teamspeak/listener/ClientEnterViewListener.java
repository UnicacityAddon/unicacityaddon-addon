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
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultChannel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultServer;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultUser;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ClientEnterViewListener extends DefaultListener {

  public ClientEnterViewListener() {
    super("notifycliententerview");
  }

  @Override
  public void execute(DefaultTeamSpeakAPI teamSpeakAPI, String[] args) {
    Integer schandlerId = this.get(args, "schandlerid", Integer.class);
    if (schandlerId == null) {
      return;
    }

    DefaultServer selectedServer = teamSpeakAPI.getSelectedServer();
    if (selectedServer == null || selectedServer.getId() != schandlerId) {
      return;
    }

    DefaultChannel selectedChannel = selectedServer.getSelectedChannel();
    Integer channelId = this.get(args, "ctid", Integer.class);
    if (channelId == null || selectedChannel == null || selectedChannel.getId() != channelId) {
      return;
    }

    Integer clientId = this.get(args, "clid", Integer.class);
    DefaultUser user = selectedChannel.getUser(clientId);
    if (user != null) {
      return;
    }

    teamSpeakAPI.controller().refreshUsers(selectedChannel);
  }
}
