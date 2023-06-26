package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author RettichLP
 */
@UCEvent
public class TabCompletionListener {

    private int lastSuggestedCommandPrefix = 0;
    private String lastSuggestedCommandPrefixStartString = null;
    private int lastSuggestedCommandParameter = 0;
    private String lastSuggestedCommandParameterStartString = null;

    private final UnicacityAddon unicacityAddon;

    public TabCompletionListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        if (Laby.references().chatAccessor().isChatOpen() && e.state().equals(KeyEvent.State.PRESS)) {
            if (e.key().equals(Key.TAB)) {
                autoComplete();
            } else {
                this.lastSuggestedCommandPrefix = 0;
                this.lastSuggestedCommandPrefixStartString = null;
                this.lastSuggestedCommandParameter = 0;
                this.lastSuggestedCommandParameterStartString = null;
            }
        }
    }

    public void autoComplete() {
        String chatInputMessage = Laby.references().chatExecutor().getChatInputMessage();
        if (chatInputMessage != null && chatInputMessage.startsWith("/")) { // it's a command

            String withoutSlash = chatInputMessage.substring(1);
            String[] arguments = withoutSlash.split(" ");

            if (arguments.length == 1 && !withoutSlash.endsWith(" ")) { // prefix

                if (lastSuggestedCommandPrefixStartString == null) {
                    lastSuggestedCommandPrefixStartString = arguments[0];
                }

                List<String> commandPrefixAndAliasesList = new ArrayList<>();
                this.unicacityAddon.registry().commands().forEach(command -> {
                    commandPrefixAndAliasesList.add(command.getPrefix());
                    commandPrefixAndAliasesList.addAll(Arrays.asList(command.getAliases()));
                });
                commandPrefixAndAliasesList.removeIf(s -> !s.startsWith(lastSuggestedCommandPrefixStartString));

                if (commandPrefixAndAliasesList.isEmpty()) {
                    return;
                }

                String suggestedString;
                if (commandPrefixAndAliasesList.size() <= lastSuggestedCommandPrefix) {
                    lastSuggestedCommandPrefix = 0;
                }
                suggestedString = commandPrefixAndAliasesList.get(lastSuggestedCommandPrefix);
                lastSuggestedCommandPrefix++;

                Laby.references().chatExecutor().suggestCommand("/" + suggestedString);

            } else { // command parameter

                if (lastSuggestedCommandParameterStartString == null) {
                    lastSuggestedCommandParameterStartString = arguments[arguments.length - 1];
                }

                Command command = this.unicacityAddon.registry().commands().stream()
                        .filter(c -> c.getPrefix().equalsIgnoreCase(arguments[0]) || Arrays.asList(c.getAliases()).contains(arguments[0].toLowerCase()))
                        .findFirst()
                        .orElse(null);

                if (command == null) {
                    return;
                }

                // arguments without last element
                String[] argumentsWithoutLast = Arrays.copyOfRange(arguments, 0, arguments.length - 1);
                // arguments with last element (but only beginning)
                List<String> argumentsWithModifiedLast = new ArrayList<>(List.of(argumentsWithoutLast));
                argumentsWithModifiedLast.add(lastSuggestedCommandParameterStartString);

                List<String> commandParameterList = command.complete(argumentsWithModifiedLast.toArray(new String[0]));
                commandParameterList.removeIf(s -> !s.toLowerCase().startsWith(lastSuggestedCommandParameterStartString.toLowerCase()));

                if (commandParameterList.isEmpty()) {
                    return;
                }

                String suggestedString;
                if (commandParameterList.size() <= lastSuggestedCommandParameter) {
                    lastSuggestedCommandParameter = 0;
                }
                suggestedString = commandParameterList.get(lastSuggestedCommandParameter);
                lastSuggestedCommandParameter++;

                Laby.references().chatExecutor().suggestCommand("/" + this.unicacityAddon.utilService().text().makeStringByArgs(argumentsWithoutLast, " ") + " " + suggestedString);
            }
        }
    }
}