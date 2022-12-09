package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.labymod.api.client.chat.command.Command;

public class ExamplePingCommand extends Command {

  @Inject
  private ExamplePingCommand() {
    super("ping", "pong");
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    if (prefix.equalsIgnoreCase("ping")) {
      this.displayMessage(Component.text("Ping!", NamedTextColor.AQUA));
      return false;
    }

    UnicacityAddon.ADDON.displayMessage(); sendMessage();

    Component c = Component.text("", NamedTextColor.AQUA, TextDecoration.BOLD);
    Component ende = c.append(c).append(c).;

    this.displayMessage(Component.text("Pong!", NamedTextColor.GOLD));
    return true;
  }
}
