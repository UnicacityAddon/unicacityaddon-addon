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
import net.labymod.addons.teamspeak.util.ArgumentParser;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ChannelEditedListener extends DefaultListener {

  public ChannelEditedListener() {
    super("notifychanneledited");
  }

  @Override
  public void execute(DefaultTeamSpeakAPI teamSpeakAPI, String[] args) {
    Integer schandlerId = this.get(args, "schandlerid", Integer.class);
    DefaultServer server = teamSpeakAPI.getSelectedServer();
    if (server == null || schandlerId == null || server.getId() != schandlerId) {
      return;
    }

    Integer channelId = this.get(args, "cid", Integer.class);
    if (channelId == null) {
      return;
    }

    DefaultChannel channel = server.getChannel(channelId);
    if (channel == null) {
      return;
    }

    String channelName = this.get(args, "channel_name", String.class);
    if (channelName != null) {
      channel.setName(ArgumentParser.unescape(channelName));
    }
  }
}