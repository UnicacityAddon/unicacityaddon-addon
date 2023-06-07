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
import net.labymod.addons.teamspeak.util.ArgumentParser;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ClientUpdatedListener extends DefaultListener {

  public ClientUpdatedListener() {
    super("notifyclientupdated");
  }

  @Override
  public void execute(DefaultTeamSpeakAPI teamSpeakAPI, String[] args) {
    Integer schandlerId = this.get(args, "schandlerid", Integer.class);
    DefaultServer server = teamSpeakAPI.getSelectedServer();
    if (server == null || schandlerId == null || server.getId() != schandlerId) {
      return;
    }

    Integer clientId = this.get(args, "clid", Integer.class);
    DefaultChannel selectedChannel = server.getSelectedChannel();
    if (selectedChannel == null || clientId == null) {
      return;
    }

    DefaultUser user = selectedChannel.getUser(clientId);
    if (user == null) {
      return;
    }

    // update nickname
    String clientNickname = this.get(args, "client_nickname", String.class);
    if (clientNickname != null) {
      user.setNickname(ArgumentParser.unescape(clientNickname));
      return;
    }

    // hardware muted
    Integer clientInputHardware = this.get(args, "client_input_hardware", Integer.class);
    if (clientInputHardware != null) {
      user.setHardwareMuted(clientInputHardware == 0);
      return;
    }

    // hardware deafened
    Integer clientOutputHardware = this.get(args, "client_output_hardware", Integer.class);
    if (clientOutputHardware != null) {
      user.setHardwareDeafened(clientOutputHardware == 0);
      return;
    }

    // muted
    Integer clientInputMuted = this.get(args, "client_input_muted", Integer.class);
    if (clientInputMuted != null) {
      user.setMuted(clientInputMuted == 1);
      return;
    }

    // deafened
    Integer clientOutputMuted = this.get(args, "client_output_muted", Integer.class);
    if (clientOutputMuted != null) {
      user.setDeafened(clientOutputMuted == 1);
      return;
    }

    // away
    Integer clientAway = this.get(args, "client_away", Integer.class);
    if (clientAway != null) {
      user.setAway(clientAway == 1);
      String clientAwayMessage = this.get(args, "client_away_message", String.class);
      if (clientAwayMessage != null) {
        user.setAwayMessage(clientAwayMessage);
      }
    }
  }
}
