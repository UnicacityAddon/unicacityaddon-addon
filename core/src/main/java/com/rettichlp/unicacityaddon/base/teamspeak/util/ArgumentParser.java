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

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class ArgumentParser {

    public static <T> T parse(String[] arguments, String identifier, Class<T> clazz) {
        for (String argument : arguments) {
            T value = ArgumentParser.parse(argument, identifier, clazz);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    public static <T> T parse(String argument, String identifier, Class<T> clazz) {
        int length = identifier.length();
        if (argument.length() == length || !argument.startsWith(identifier + "=")) {
            return null;
        }

        String rawValue = argument.substring(length + 1);
        Object value = null;
        if (clazz == String.class) {
            value = rawValue.replace("\\s", " ").replace("\\p", "|");
        } else if (clazz == Integer.class) {
            value = Integer.parseInt(rawValue);
        }

        if (value == null) {
            return null;
        }

        return clazz.cast(value);
    }
}
