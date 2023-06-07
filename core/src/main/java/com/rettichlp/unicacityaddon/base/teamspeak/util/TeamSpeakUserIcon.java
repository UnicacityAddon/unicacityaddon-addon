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

package com.rettichlp.unicacityaddon.base.teamspeak.util;

import net.labymod.addons.teamspeak.models.User;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
public enum TeamSpeakUserIcon {
  TALKING(2, 0),
  IDLE(3, 0),
  MUTED(0, 0),
  DEAFENED(1, 0),
  AWAY(0, 3),
  HARDWARE_MUTED(0, 1),
  HARDWARE_DEAFENED(1, 1);

  private static final ResourceLocation RESOURCE_LOCATION = ResourceLocation.create(
      "teamspeak", "textures/teamspeak.png"
  );

  private final int spriteX;
  private final int spriteY;
  private Icon icon;

  TeamSpeakUserIcon(int x, int y) {
    this.spriteX = x;
    this.spriteY = y;
  }

  public static TeamSpeakUserIcon of(User user) {
    if (user.isAway()) {
      return AWAY;
    }

    if (user.isHardwareDeafened()) {
      return HARDWARE_DEAFENED;
    }

    if (user.isDeafened()) {
      return DEAFENED;
    }

    if (user.isHardwareMuted()) {
      return HARDWARE_MUTED;
    }

    if (user.isMuted()) {
      return MUTED;
    }

    if (user.isTalking()) {
      return TALKING;
    }

    return IDLE;
  }

  public Icon icon() {
    if (this.icon == null) {
      this.icon = Icon.sprite16(RESOURCE_LOCATION, this.spriteX, this.spriteY);
    }

    return this.icon;
  }
}
