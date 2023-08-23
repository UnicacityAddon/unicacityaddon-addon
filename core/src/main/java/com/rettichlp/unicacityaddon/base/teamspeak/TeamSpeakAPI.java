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
import com.rettichlp.unicacityaddon.base.config.teamspeak.TeamSpeakConfiguration;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ChannelEditedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientEnterViewListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientLeftViewListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientMovedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ConnectStatusChangeListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.CurrentServerConnectionChangedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.Listener;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.SelectedListener;
import com.rettichlp.unicacityaddon.base.teamspeak.misc.ReconnectController;
import com.rettichlp.unicacityaddon.base.teamspeak.misc.TeamSpeakController;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Server;
import com.rettichlp.unicacityaddon.base.teamspeak.util.Request;
import lombok.Getter;

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
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class TeamSpeakAPI {

    private final TeamSpeakAuthenticator authenticator;
    private final TeamSpeakController controller;
    private final ReconnectController reconnectController;
    private final List<Listener> listeners;

    private final List<Request> requests;
    private Socket socket;
    private PrintWriter outputStream;

    @Getter
    private boolean connected;

    @Getter
    private int clientId;
    private int channelId;

    private boolean manualStop;

    private final UnicacityAddon unicacityAddon;

    public TeamSpeakAPI(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;

        this.authenticator = new TeamSpeakAuthenticator(unicacityAddon, this);
        this.controller = new TeamSpeakController(unicacityAddon, this);
        this.reconnectController = new ReconnectController(this);
        this.requests = new ArrayList<>();
        this.listeners = new ArrayList<>();

        this.listeners.add(new ChannelEditedListener());
        this.listeners.add(new ClientEnterViewListener());
        this.listeners.add(new ClientLeftViewListener());
        this.listeners.add(new ClientMovedListener(this.unicacityAddon));
        this.listeners.add(new ConnectStatusChangeListener());
        this.listeners.add(new CurrentServerConnectionChangedListener());
        this.listeners.add(new SelectedListener());
    }

    @SuppressWarnings("BusyWait")
    public void initialize() {
        if (this.socket != null && !this.socket.isClosed()) {
            throw new IllegalStateException("Socket is already initialized!");
        }

        this.manualStop = false;
        this.reset();

        BufferedReader inputStream = null;
        try {
            this.unicacityAddon.logger().info("Connecting to TeamSpeak client...");
            this.socket = new Socket("127.0.0.1", 25639);

            this.outputStream = new PrintWriter(
                    new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8),
                    true
            );

            inputStream = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8)
            );
        } catch (ConnectException e) {
            this.unicacityAddon.logger().warn("Could not connect to TeamSpeak client!");
            this.reconnectController.start();
            return;
        } catch (IOException e) {
            this.unicacityAddon.logger().error(e.getMessage());
        }

        new Thread(() -> {
            while (!this.manualStop && this.socket.isConnected() && !this.socket.isClosed()) {
                if (this.connected) {
                    this.outputStream.println("whoami");
                    if (this.outputStream.checkError()) {
                        this.updateConnected(false);

                        try {
                            this.socket.close();
                        } catch (IOException e) {
                            this.unicacityAddon.logger().error(e.getMessage());
                        }
                        this.unicacityAddon.logger().warn("3Connection to TeamSpeak client lost.");
                        this.reconnectController.start();
                        return;
                    }
                }

                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    this.unicacityAddon.logger().error(e.getMessage());
                }
            }

            if (!this.manualStop) {
                this.updateConnected(false);

                try {
                    this.socket.close();
                } catch (IOException e) {
                    this.unicacityAddon.logger().error(e.getMessage());
                }
                this.unicacityAddon.logger().warn("1Connection to TeamSpeak client lost.");
                this.reconnectController.start();
            }
        }).start();

        TeamSpeakConfiguration teamSpeakConfiguration = this.unicacityAddon.configuration().teamspeak();
        if (teamSpeakConfiguration.resolve().get()) {
            this.authenticator.authenticate();
        } else {
            String apiKey = teamSpeakConfiguration.key().get().trim();
            if (apiKey.isEmpty()) {
                throw new IllegalStateException("Cannot authenticate with an empty API key!");
            }

            this.authenticate(apiKey);
        }

        this.updateConnected(true);
        this.clientNotifyRegister(0);
        this.unicacityAddon.logger().info("Successfully connected to the TeamSpeak client.");

        while (!this.manualStop && this.socket.isConnected() && !this.socket.isClosed()) {
            String line;
            try {
                if (this.connected && inputStream != null && inputStream.ready() && !this.socket.isClosed()) {
                    while ((line = inputStream.readLine()) != null) {
                        this.messageReceived(line);
                    }
                }

                Thread.sleep(100L);
            } catch (Exception e) {
                this.unicacityAddon.logger().error(e.getMessage());
            }
        }

        if (!this.manualStop) {
            this.updateConnected(false);

            try {
                this.socket.close();
            } catch (IOException e) {
                this.unicacityAddon.logger().error(e.getMessage());
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

    private void updateConnected(boolean connected) {
        this.connected = connected;
    }

    public void authenticate(String apiKey) {
        if (this.socket == null || this.socket.isClosed()) {
            return;
        }

        this.outputStream.println("auth apikey=" + apiKey);
    }

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

    public void query(String query) {
        this.outputStream.println(query);
    }

    public Server getServer() {
        return this.controller.getServer();
    }

    public List<Server> getServers() {
        return this.controller.getServers();
    }

    public Server getServer(int id) {
        return this.controller.getServer(id);
    }

    public TeamSpeakController controller() {
        return this.controller;
    }

    private void reset() {
        this.controller.getServers().clear();
        this.controller.setServer(null);
        this.clientId = 0;
        this.channelId = 0;
    }
}
