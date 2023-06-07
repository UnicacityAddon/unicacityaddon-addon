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

package net.labymod.addons.teamspeak;

import net.labymod.addons.teamspeak.models.Server;
import net.labymod.addons.teamspeak.util.Request;
import net.labymod.api.reference.annotation.Referenceable;

import java.util.List;

@Referenceable
public interface TeamSpeakAPI {

  boolean isConnected();

  boolean hasInvalidKey();

  void request(Request request);

  void query(String query);

  Server getSelectedServer();

  List<Server> getServers();

  Server getServer(int id);

  int getClientId();
}
