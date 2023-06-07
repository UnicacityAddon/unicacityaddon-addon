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
import net.labymod.addons.teamspeak.models.User;
import net.labymod.addons.teamspeak.util.ArgumentParser;
import net.labymod.addons.teamspeak.util.Request;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ClientMovedListener extends DefaultListener {

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

    System.out.println("trigger event");
    // todo check selected server is uc
    this.unicacityAddon.labyAPI().eventBus().fire(new TeamspeakClientMoveEvent(selectedServer.getChannel(channelId), selectedServer.getChannel(channelId).getUser(clientId)));

    if (clientId == teamSpeakAPI.getClientId()) {
      DefaultChannel selectedChannel = selectedServer.getSelectedChannel();
      if (selectedChannel != null && selectedChannel.getId() != channelId) {
        selectedChannel.getUsers().clear();
      }

      DefaultChannel channel = selectedServer.getChannel(channelId);
      if (channel == null) {
        channel = selectedServer.addChannel(channelId);
      }

      DefaultChannel finalChannel = channel;

      teamSpeakAPI.request(Request.firstParamStartsWith(
          "channelconnectinfo",
          "path=",
          channelConnectInfoAnswer -> {
            String[] split = channelConnectInfoAnswer.split(" ");
            String path = this.get(split, "path", String.class);
            String name = ArgumentParser.unescape(
                TeamSpeakController.getLastChannel(path.length(), path));
            finalChannel.setName(name);
            selectedServer.setSelectedChannel(finalChannel);
            teamSpeakAPI.controller().refreshUsers(finalChannel);
          }
      ));

      return;
    }

    DefaultChannel selectedChannel = selectedServer.getSelectedChannel();
    if (selectedChannel == null) {
      return;
    }

    boolean sameChannel = channelId == selectedChannel.getId();
    User user = selectedChannel.getUser(clientId);
    if (user == null && sameChannel) {
      teamSpeakAPI.controller().refreshUsers(selectedChannel);
      return;
    }

    if (user != null && !sameChannel) {
      selectedChannel.getUsers().remove(user);
    }
  }
}
