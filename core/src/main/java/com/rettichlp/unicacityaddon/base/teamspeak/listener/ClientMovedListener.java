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
import com.rettichlp.unicacityaddon.base.events.TeamspeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.DefaultTeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.misc.TeamSpeakController;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultChannel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultServer;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultUser;
import net.labymod.addons.teamspeak.util.ArgumentParser;
import net.labymod.addons.teamspeak.util.Request;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class ClientMovedListener extends DefaultListener {

  private DefaultUser currentUser;
  private DefaultChannel currentChannel;

  private final UnicacityAddon unicacityAddon;

  public ClientMovedListener(UnicacityAddon unicacityAddon) {
    super("notifyclientmoved");
    this.unicacityAddon = unicacityAddon;
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

    Integer clientId = this.get(args, "clid", Integer.class);
    Integer channelId = this.get(args, "ctid", Integer.class);
    if (clientId == null || channelId == null) {
      return;
    }

    if (clientId == teamSpeakAPI.getClientId()) {
      DefaultChannel selectedChannel = selectedServer.getSelectedChannel();
      if (selectedChannel != null && selectedChannel.getId() != channelId) {
        selectedChannel.getUsers().clear();
      }

      this.currentChannel = selectedServer.getChannel(channelId);
      if (this.currentChannel == null) {
        this.currentChannel = selectedServer.addChannel(channelId);
      }

      teamSpeakAPI.request(Request.firstParamStartsWith(
          "channelconnectinfo",
          "path=",
          channelConnectInfoAnswer -> {
            String[] split = channelConnectInfoAnswer.split(" ");
            String path = this.get(split, "path", String.class);
            String name = ArgumentParser.unescape(
                TeamSpeakController.getLastChannel(path.length(), path));
            this.currentChannel.setName(name);
            selectedServer.setSelectedChannel(this.currentChannel);
            teamSpeakAPI.controller().refreshUsers(this.currentChannel);
          }
      ));

      this.unicacityAddon.labyAPI().eventBus().fire(new TeamspeakClientMoveEvent(this.unicacityAddon, this.currentChannel));
      return;
    }

    this.currentChannel = selectedServer.getChannel(channelId);
    if (this.currentChannel == null) {
      this.currentChannel = selectedServer.addChannel(channelId);
    }

    this.currentUser = this.currentChannel.getUser(clientId);
    if (this.currentUser == null) {
      teamSpeakAPI.controller().refreshUsers(this.currentChannel);
    }

    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        ClientMovedListener.this.currentUser = ClientMovedListener.this.currentChannel.getUser(clientId);
        ClientMovedListener.this.unicacityAddon.labyAPI().eventBus().fire(new TeamspeakClientMoveEvent(ClientMovedListener.this.unicacityAddon, ClientMovedListener.this.currentChannel, ClientMovedListener.this.currentUser));
      }
    }, 0);
  }
}
