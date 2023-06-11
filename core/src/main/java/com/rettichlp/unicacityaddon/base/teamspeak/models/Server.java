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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
@Getter
@RequiredArgsConstructor
@ToString
public class Server {

    private final int id;
    private final List<Channel> channels = new ArrayList<>();

    @Setter
    private Channel selectedChannel;

    public @Nullable Channel getChannel(int id) {
        return this.channels.stream()
                .filter(defaultChannel -> defaultChannel.getId() == id)
                .findAny().orElse(null);
    }

    public Channel addChannel(int id) {
        Channel channel = new Channel(id);
        this.channels.add(channel);
        return channel;
    }
}
