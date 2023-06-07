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

package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ChannelEditedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientEnterViewListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientLeftViewListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientMovedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientUpdatedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ConnectStatusChange;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.CurrentServerConnectionChangedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.SelectedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.TalkStatusChangeListener;
import com.rettichlp.unicacityaddon.base.teamspeak.misc.ReconnectController;
import com.rettichlp.unicacityaddon.base.teamspeak.misc.TeamSpeakController;
import com.rettichlp.unicacityaddon.base.teamspeak.models.DefaultServer;
import net.labymod.addons.teamspeak.TeamSpeakAPI;
import net.labymod.addons.teamspeak.listener.Listener;
import net.labymod.addons.teamspeak.models.Server;
import net.labymod.addons.teamspeak.util.Request;
import net.labymod.api.models.Implements;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 */
@Singleton
@Implements(TeamSpeakAPI.class)
public class DefaultTeamSpeakAPI implements TeamSpeakAPI {

  private final TeamSpeakAuthenticator authenticator;
  private final TeamSpeakController controller;
  private final ReconnectController reconnectController;
  private final List<Listener> listeners;

  private final List<Request> requests;
  private Socket socket;
  private PrintWriter outputStream;
  private BufferedReader inputStream;

  private boolean connected;

  private int clientId;
  private int channelId;

  private boolean manualStop;
  private boolean invalidKey;

  private final UnicacityAddon unicacityAddon;

  public DefaultTeamSpeakAPI(UnicacityAddon unicacityAddon) {
    this.unicacityAddon = unicacityAddon;
    this.authenticator = new TeamSpeakAuthenticator(unicacityAddon, this);
    this.controller = new TeamSpeakController(this);
    this.reconnectController = new ReconnectController(this);
    this.requests = new ArrayList<>();
    this.listeners = new ArrayList<>();

    this.listeners.add(new ChannelEditedListener());
    this.listeners.add(new ClientEnterViewListener());
    this.listeners.add(new ClientLeftViewListener());
    this.listeners.add(new ClientMovedListener(unicacityAddon));
    this.listeners.add(new ClientUpdatedListener());
    this.listeners.add(new ConnectStatusChange());
    this.listeners.add(new CurrentServerConnectionChangedListener());
    this.listeners.add(new SelectedListener());
    this.listeners.add(new TalkStatusChangeListener());
  }

  public void initialize() throws IOException {
    if (this.socket != null && !this.socket.isClosed()) {
      throw new IllegalStateException("Socket is already initialized!");
    }

    this.manualStop = false;
    this.invalidKey = false;
    this.reset();

    try {
      this.unicacityAddon.logger().info("Connecting to TeamSpeak client...");
      this.socket = new Socket("127.0.0.1", 25639);
    } catch (ConnectException e) {
      this.unicacityAddon.logger().warn("Could not connect to TeamSpeak client!");
      this.reconnectController.start();
      return;
    }

    this.outputStream = new PrintWriter(
        new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8),
        true
    );

    this.inputStream = new BufferedReader(
        new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8)
    );

    new Thread(() -> {
      while (!this.manualStop && this.socket.isConnected() && !this.socket.isClosed()) {
        if (this.connected) {
          this.outputStream.println("whoami");
          if (this.outputStream.checkError()) {
            this.updateConnected(false);

            try {
              this.socket.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
            this.unicacityAddon.logger().warn("3Connection to TeamSpeak client lost.");
            this.reconnectController.start();
            return;
          }
        }

        try {
          Thread.sleep(5000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      if (!this.manualStop) {
        this.updateConnected(false);

        try {
          this.socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        this.unicacityAddon.logger().warn("1Connection to TeamSpeak client lost.");
        this.reconnectController.start();
      }
    }).start();

    UnicacityAddonConfiguration configuration = this.unicacityAddon.configuration();
    if (configuration.resolveAPIKey().get()) {
      if (!this.authenticator.authenticate()) {
        this.invalidKey = true;
      }
    } else {
      String apiKey = configuration.tsApiKey().get().trim();
      if (apiKey.isEmpty()) {
        throw new IllegalStateException("Cannot authenticate with an empty API key!");
      }

      this.authenticate(apiKey);
    }

    this.updateConnected(true);
    this.clientNotifyRegister(0);
    this.unicacityAddon.logger().info("Successfully connected to the TeamSpeak client.");

    while (!this.manualStop && this.socket.isConnected() && !this.socket.isClosed()) {
      String line = null;
      try {
        if (this.connected && this.inputStream.ready() && !this.socket.isClosed()) {
          while ((line = this.inputStream.readLine()) != null) {
            this.messageReceived(line);
          }
        }

        Thread.sleep(100L);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (!this.manualStop) {
      this.updateConnected(false);

      try {
        this.socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      this.unicacityAddon.logger().warn("2Connection to TeamSpeak client lost.");
      this.reconnectController.start();
    }
  }

  private void messageReceived(String line) {
    //System.out.println("Received: " + line);
    if (line.isEmpty()) {
      return;
    }

    String[] s = line.split(" ");
    if (s.length == 2 && s[0].startsWith("clid=") && s[1].startsWith("cid=")) {
      int clientId = Integer.parseInt(s[0].substring(5));
      if (this.clientId != clientId) {
        this.clientId = clientId;
      }

      int channelId = Integer.parseInt(s[1].substring(4));
      if (this.channelId != channelId) {
        this.channelId = channelId;
      }

      return;
    }

    boolean ok = line.equals("error id=0 msg=ok");
    Request[] requests = this.requests.toArray(new Request[0]);
    boolean handledRequest = false;
    for (Request request : requests) {
      if (request.isFinished()) {
        this.requests.remove(request);
        continue;
      }

      if (request.handle(s[0], line)) {
        handledRequest = true;
      }
    }

    if (ok || handledRequest) {
      return;
    }

    for (Listener listener : this.listeners) {
      if (listener.getIdentifier().equals(s[0])) {
        listener.execute(this, s);
      }
    }
  }

  @Override
  public boolean isConnected() {
    return this.connected;
  }

  public void clientNotifyRegister(int id) {
    //this.outputStream.println("clientnotifyregister schandlerid=" + id + " event=any");
    for (Listener listener : this.listeners) {
      if (!listener.needsToBeRegistered()) {
        continue;
      }

      this.outputStream.println(
          "clientnotifyregister schandlerid=" + id + " event=" + listener.getIdentifier());
    }
  }

  public boolean isRunning() {
    return this.socket != null;
  }

  public PrintWriter getOutputStream() {
    return this.outputStream;
  }

  public BufferedReader getInputStream() {
    return this.inputStream;
  }

  private void updateConnected(boolean connected) {
    this.connected = connected;
  }

  public boolean authenticate(String apiKey) {
    if (this.socket == null || this.socket.isClosed()) {
      return false;
    }

    this.outputStream.println("auth apikey=" + apiKey);
    return true;
  }

  @Override
  public boolean hasInvalidKey() {
    return this.invalidKey;
  }

  public void setInvalidKey(boolean invalidKey) {
    this.invalidKey = invalidKey;
  }

  @Override
  public void request(Request request) {
    String query = request.getQuery();
    Request[] pendingRequests = this.requests.toArray(new Request[0]);
    for (Request pendingRequest : pendingRequests) {
      if (pendingRequest.getQuery().equals(query)) {
        this.requests.remove(pendingRequest);
      }
    }

    this.requests.add(request);
    this.query(query);
  }

  @Override
  public void query(String query) {
    this.outputStream.println(query);
  }

  @Override
  public DefaultServer getSelectedServer() {
    return this.controller.getSelectedServer();
  }

  @Override
  public List<Server> getServers() {
    return (List) this.controller.getServers();
  }

  @Override
  public DefaultServer getServer(int id) {
    return this.controller.getServer(id);
  }

  @Override
  public int getClientId() {
    return this.clientId;
  }

  public List<Request> getRequests() {
    return this.requests;
  }

  public TeamSpeakController controller() {
    return this.controller;
  }

  public void stop() throws IOException {
    if (this.socket == null) {
      return;
    }

    this.manualStop = true;
    this.socket.close();
    this.connected = false;
    this.reset();
  }

  private void reset() {
    this.controller.getServers().clear();
    this.controller.setSelectedServer(null);
    this.clientId = 0;
    this.channelId = 0;
  }
}
