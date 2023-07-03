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

import com.rettichlp.unicacityaddon.base.enums.teamspeak.TSChannelCategory;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
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
public class Channel {

    private final int id;
    private final List<User> users = new ArrayList<>();

    @Setter
    private String name;
    @Setter
    private Integer pid;

    public void addUser(User user) {
        this.users.add(user);
    }

    @Nullable
    public User getUser(Integer clid) {
        return this.users.stream()
                .filter(user -> user.getId() == clid)
                .findAny()
                .orElse(null);
    }

    @Nullable
    public String getChannelCategory() {
        return this.pid != null ? Arrays.stream(TSChannelCategory.values())
                .filter(tsChannelCategory -> tsChannelCategory.getPid() == this.pid)
                .findFirst()
                .map(TSChannelCategory::getCategoryName)
                .orElse(null) : null;
    }

    public String getChannelCategoryMessage() {
        String channelCategory = getChannelCategory();

        return channelCategory != null ? Message.getBuilder().space()
                .of("(").color(ColorCode.GRAY).advance()
                .of(channelCategory).color(ColorCode.AQUA).advance()
                .of(")").color(ColorCode.GRAY).advance()
                .create() : "";
    }
}
