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

import com.rettichlp.unicacityaddon.base.teamspeak.DefaultTeamSpeakAPI;
import net.labymod.api.Laby;
import net.labymod.api.util.concurrent.task.Task;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class ReconnectController {

  private final DefaultTeamSpeakAPI teamSpeakAPI;
  private Task task;

  public ReconnectController(DefaultTeamSpeakAPI teamSpeakAPI) {
    this.teamSpeakAPI = teamSpeakAPI;
  }

  public void start() {
    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
      if (this.task != null) {
        return;
      }

      this.task = Task.builder(() -> {
        if (this.teamSpeakAPI.isConnected()) {
          return;
        }

        this.task = null;
        new Thread(() -> {
          try {
            this.teamSpeakAPI.initialize();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      }).delay(10, TimeUnit.SECONDS).build();

      this.task.execute();
    });
  }
}
