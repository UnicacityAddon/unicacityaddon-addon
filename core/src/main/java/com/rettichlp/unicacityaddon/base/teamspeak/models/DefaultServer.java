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

package com.rettichlp.unicacityaddon.base.teamspeak.models;

import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.addons.teamspeak.models.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class DefaultServer implements Server {

  private final int id;
  private final List<DefaultChannel> channels;
  private DefaultChannel selectedChannel;

  public DefaultServer(int id) {
    this.id = id;
    this.channels = new ArrayList<>();
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public @NotNull List<Channel> getChannels() {
    return (List) this.channels;
  }

  @Override
  public @Nullable DefaultChannel getSelectedChannel() {
    return this.selectedChannel;
  }

  public void setSelectedChannel(DefaultChannel channel) {
    this.selectedChannel = channel;
  }

  @Override
  public @Nullable DefaultChannel getChannel(int id) {
    for (DefaultChannel channel : this.channels) {
      if (channel.getId() == id) {
        return channel;
      }
    }

    return null;
  }

  public List<DefaultChannel> getDefaultChannels() {
    return this.channels;
  }

  public DefaultChannel addChannel(int id) {
    DefaultChannel channel = this.getChannel(id);
    if (channel != null) {
      return channel;
    }

    channel = new DefaultChannel(id);
    this.channels.add(channel);
    return channel;
  }
}
