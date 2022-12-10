package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.labymod.api.client.chat.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    this.displayMessage(Component.text("Pong!", NamedTextColor.GOLD));
    return true;
  }

  @Override
  public List<String> complete(String[] arguments) {
    if (arguments.length == 1) {
      List<String> tabCompletions = new ArrayList<>();
      tabCompletions.add(this.prefix);
      tabCompletions.addAll(Arrays.asList(this.aliases));
      return tabCompletions;
    }

    return Collections.emptyList();
  }
}
