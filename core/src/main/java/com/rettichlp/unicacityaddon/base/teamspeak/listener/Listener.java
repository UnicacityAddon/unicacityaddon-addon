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

package com.rettichlp.unicacityaddon.base.teamspeak.listener;

import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.util.ArgumentParser;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public abstract class Listener {

    private final String identifier;
    private boolean register = true;

    protected Listener(String identifier) {
        this.identifier = identifier;
    }

    public abstract void execute(TeamSpeakAPI teamSpeakAPI, String[] args);

    protected <T> T get(String[] arguments, String identifier, Class<T> clazz) {
        return ArgumentParser.parse(arguments, identifier, clazz);
    }

    protected <T> T get(String argument, String identifier, Class<T> clazz) {
        return ArgumentParser.parse(argument, identifier, clazz);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public boolean needsToBeRegistered() {
        return this.register;
    }

    protected void registerNotify(boolean register) {
        this.register = register;
    }
}
