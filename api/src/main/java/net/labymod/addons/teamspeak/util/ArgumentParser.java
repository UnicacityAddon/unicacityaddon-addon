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

package net.labymod.addons.teamspeak.util;

/**
 * The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
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

  public static String unescape(String value) {
    return unescapeChannelName(value).replace("\\/", "/");
  }

  private static String unescapeChannelName(String channelName) {
    StringBuilder result = new StringBuilder();
    boolean escapeNextChar = false;

    for (int i = 0; i < channelName.length(); i++) {
      char currentChar = channelName.charAt(i);

      if (escapeNextChar) {
        // Handle escaped characters
        switch (currentChar) {
          case 's':
            result.append(' ');
            break;
          case '/':
            result.append("/");
            break;
          case '\\':
            result.append('\\');
            break;
          case 'p':
            result.append('|');
            break;
          case 'a':
            result.append('\u0007');
            break;
          case 'b':
            result.append('\u0008');
            break;
          case 'f':
            result.append('\u000C');
            break;
          case 'n':
            result.append('\n');
            break;
          case 'r':
            result.append('\r');
            break;
          case 't':
            result.append('\t');
            break;
          case 'v':
            result.append('\u000B');
            break;
          default:
            result.append('\\');
            result.append(currentChar);
            break;
        }

        escapeNextChar = false;
      } else if (currentChar == '\\') {
        // Escape character detected, set flag to handle next character as escaped
        escapeNextChar = true;
      } else {
        // Regular character, append to result
        result.append(currentChar);
      }
    }

    return result.toString();
  }
}
