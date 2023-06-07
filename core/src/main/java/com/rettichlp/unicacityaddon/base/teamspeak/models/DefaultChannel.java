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
import net.labymod.addons.teamspeak.models.User;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class DefaultChannel implements Channel {

  public static final Pattern PRETTY_PATTERN = Pattern.compile("\\[[*lrc]spacer\\d*](.*)");

  private final int id;
  private final List<DefaultUser> users;
  private String name;
  private String prettyName;

  private Component displayName;
  private Component prettyDisplayName;

  public DefaultChannel(int id) {
    this.id = id;
    this.users = new ArrayList<>();
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public @NotNull List<User> getUsers() {
    return (List) this.users;
  }

  @Override
  public @Nullable String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
    this.displayName = null;
    this.prettyName = null;
    this.prettyDisplayName = null;
  }

  @Override
  public @Nullable String getPrettyName() {
    String name = this.getName();
    if (name == null) {
      return null;
    }

    if (this.prettyName == null) {
      this.prettyName = PRETTY_PATTERN.matcher(name).replaceAll("$1").trim();
    }

    return this.prettyName;
  }

  @Override
  public @NotNull Component displayName() {
    if (this.displayName == null) {
      String name = this.getName();
      this.displayName = name == null ? Component.empty() : Component.text(name);
    }

    return this.displayName;
  }

  @Override
  public @NotNull Component prettyDisplayName() {
    if (this.prettyDisplayName == null) {
      String prettyName = this.getPrettyName();
      this.prettyDisplayName = prettyName == null ? Component.empty() : Component.text(prettyName);
    }

    return this.prettyDisplayName;
  }

  @Override
  public @Nullable DefaultUser getUser(int id) {
    for (DefaultUser user : this.users) {
      if (user.getId() == id) {
        return user;
      }
    }

    return null;
  }

  public DefaultUser addUser(int id) {
    DefaultUser user = this.getUser(id);
    if (user != null) {
      return user;
    }

    user = new DefaultUser(id);
    this.users.add(user);
    return user;
  }
}
