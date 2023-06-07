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
import net.labymod.addons.teamspeak.util.Request;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class SelectedListener extends DefaultListener {

  public SelectedListener() {
    super("selected");
    this.registerNotify(false);
  }

  @Override
  public void execute(DefaultTeamSpeakAPI teamSpeakAPI, String[] args) {
    Integer selectedSchandlerId = this.get(args, "schandlerid", Integer.class);
    teamSpeakAPI.request(Request.unknown("serverconnectionhandlerlist", schandlerListAnswer -> {
      if (schandlerListAnswer.equals("error id=1538 msg=invalid\\sparameter")) {
        teamSpeakAPI.setInvalidKey(true);
        return true;
      }

      teamSpeakAPI.setInvalidKey(false);
      String[] schandlers = schandlerListAnswer.split("\\|");
      for (String schandler : schandlers) {
        if (!schandler.startsWith("schandlerid=")) {
          return false;
        }
      }

      for (String schandler : schandlers) {
        Integer schandlerId = this.get(schandler, "schandlerid", Integer.class);
        DefaultServer server = teamSpeakAPI.getServer(schandlerId);
        if (server == null) {
          server = new DefaultServer(schandlerId);
          teamSpeakAPI.getServers().add(server);
        }
      }

      if (selectedSchandlerId != null) {
        teamSpeakAPI.controller().refreshCurrentServer0(selectedSchandlerId);
      }

      return true;
    }));
  }
}
