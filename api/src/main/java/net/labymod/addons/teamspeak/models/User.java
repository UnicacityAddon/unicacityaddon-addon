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

package net.labymod.addons.teamspeak.models;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface User {

  int getId();

  @Nullable String getNickname();

  @NotNull Component displayName();

  boolean isTalking();

  boolean isMuted();

  boolean isHardwareMuted();

  boolean isDeafened();

  boolean isHardwareDeafened();

  int getTalkPower();

  boolean isAway();

  boolean isQuery();

  @Nullable String getAwayMessage();
}
