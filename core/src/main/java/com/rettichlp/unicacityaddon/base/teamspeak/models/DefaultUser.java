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

import net.labymod.addons.teamspeak.TeamSpeakAPI;
import net.labymod.addons.teamspeak.models.User;
import net.labymod.addons.teamspeak.util.ArgumentParser;
import net.labymod.addons.teamspeak.util.Request;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

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

  /**
   * Possible other properties:
   * <ul>
   *     <li>client_unique_identifier</li>
   *     <li>client_nickname</li>
   *     <li>client_input_muted</li>
   *     <li>client_output_muted</li>
   *     <li>client_outputonly_muted</li>
   *     <li>client_input_hardware</li>
   *     <li>client_output_hardware</li>
   *     <li>client_meta_data</li>
   *     <li>client_is_recording</li>
   *     <li>client_database_id</li>
   *     <li>client_channel_group_id</li>
   *     <li>client_servergroups</li>
   *     <li>client_away</li>
   *     <li>client_away_message</li>
   *     <li>client_type</li>
   *     <li>client_flag_avatar</li>
   *     <li>client_talk_power</li>
   *     <li>client_talk_request</li>
   *     <li>client_talk_request_msg</li>
   *     <li>client_description</li>
   *     <li>client_is_talker</li>
   *     <li>client_is_priority_speaker</li>
   *     <li>client_unread_messages</li>
   *     <li>client_nickname_phonetic</li>
   *     <li>client_needed_serverquery_view_power</li>
   *     <li>client_icon_id</li>
   *     <li>client_is_channel_commander</li>
   *     <li>client_country</li>
   *     <li>client_channel_group_inherited_channel_id</li>
   *     <li>client_flag_talking</li>
   *     <li>client_is_muted</li>
   *     <li>client_volume_modificator</li>
   *     <li>client_version</li>
   *     <li>client_platform</li>
   *     <li>client_login_name</li>
   *     <li>client_created</li>
   *     <li>client_lastconnected</li>
   *     <li>client_totalconnections</li>
   *     <li>client_month_bytes_uploaded</li>
   *     <li>client_month_bytes_downloaded</li>
   *     <li>client_total_bytes_uploaded</li>
   *     <li>client_total_bytes_downloaded</li>
   *     <li>client_input_deactivated</li>
   * </ul>
   *
   * <code>
   *     private final String[] properties = new String[]{"client_unique_identifier", "client_unique_identifier", "client_nickname", "client_input_muted", "client_output_muted", "client_outputonly_muted",
   *             "client_input_hardware", "client_output_hardware", "client_meta_data", "client_is_recording", "client_database_id", "client_channel_group_id", "client_servergroups",
   *             "client_away", "client_away_message", "client_type", "client_flag_avatar", "client_talk_power", "client_talk_request", "client_talk_request_msg", "client_description",
   *             "client_is_talker", "client_is_priority_speaker", "client_unread_messages", "client_nickname_phonetic", "client_needed_serverquery_view_power", "client_icon_id",
   *             "client_is_channel_commander", "client_country", "client_channel_group_inherited_channel_id", "client_flag_talking", "client_is_muted", "client_volume_modificator",
   *             "client_version", "client_platform", "client_login_name", "client_created", "client_lastconnected", "client_totalconnections", "client_month_bytes_uploaded",
   *             "client_month_bytes_downloaded", "client_total_bytes_uploaded", "client_total_bytes_downloaded", "client_input_deactivated"};
   * </code>
   */
  @Override
  public CompletableFuture<String> getDescription(TeamSpeakAPI teamSpeakAPI) {
    CompletableFuture<String> completableFuture = new CompletableFuture<>();

    teamSpeakAPI.request(Request.unknown(
            "clientvariable clid=" + this.id + " client_description",
            clientProperties -> {
              String[] arguments = clientProperties.split(" ");

              String description = ArgumentParser.parse(arguments, "client_description", String.class);
              completableFuture.complete(description != null ? description : "Unbekannt");

              return true;
            }));

    return completableFuture;
  }
}
