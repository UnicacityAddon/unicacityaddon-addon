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

import net.labymod.addons.teamspeak.models.User;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public class DefaultUser implements User {

  private final int id;

  private Component displayName;
  private String nickname;
  private boolean talking;
  private boolean muted;
  private boolean deafened;
  private boolean hardwareMuted;
  private boolean hardwareDeafened;
  private boolean query;
  private int talkPower;

  private boolean away;
  private String awayMessage;

  public DefaultUser(int id) {
    this.id = id;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public @Nullable String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
    this.displayName = null;
  }

  @Override
  public @NotNull Component displayName() {
    if (this.displayName == null) {
      this.displayName = Component.text(this.nickname);
    }

    return this.displayName;
  }

  @Override
  public boolean isTalking() {
    return this.talking;
  }

  public void setTalking(boolean talking) {
    this.talking = talking;
  }

  @Override
  public boolean isMuted() {
    return this.muted;
  }

  public void setMuted(boolean muted) {
    this.muted = muted;
  }

  @Override
  public boolean isHardwareMuted() {
    return this.hardwareMuted;
  }

  public void setHardwareMuted(boolean hardwareMuted) {
    this.hardwareMuted = hardwareMuted;
  }

  @Override
  public boolean isDeafened() {
    return this.deafened;
  }

  public void setDeafened(boolean deafened) {
    this.deafened = deafened;
  }

  @Override
  public boolean isHardwareDeafened() {
    return this.hardwareDeafened;
  }

  public void setHardwareDeafened(boolean hardwareDeafened) {
    this.hardwareDeafened = hardwareDeafened;
  }

  @Override
  public int getTalkPower() {
    return this.talkPower;
  }

  public void setTalkPower(int talkPower) {
    this.talkPower = talkPower;
  }

  @Override
  public boolean isAway() {
    return this.away;
  }

  public void setAway(boolean away) {
    if (!away) {
      this.awayMessage = null;
    }

    this.away = away;
  }

  @Override
  public boolean isQuery() {
    return this.query;
  }

  public void setQuery(boolean query) {
    this.query = query;
  }

  @Override
  public @Nullable String getAwayMessage() {
    return this.awayMessage;
  }

  public void setAwayMessage(String awayMessage) {
    this.awayMessage = awayMessage;
  }
}
