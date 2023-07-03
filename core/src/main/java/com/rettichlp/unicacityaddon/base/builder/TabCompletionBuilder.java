package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.UnicacityAddon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class TabCompletionBuilder {

    private TabCompletionBuilder() {
    }

    public static Builder getBuilder(UnicacityAddon unicacityAddon, String[] arguments) {
        return new Builder(unicacityAddon, arguments);
    }

    public static class Builder {

        private final String[] arguments;
        private final Map<Integer, List<String>> tabCompletionMap;
        private final Map<Integer, List<String>> tabCompletionFromIndexMap;

        private final UnicacityAddon unicacityAddon;

        public Builder(UnicacityAddon unicacityAddon, String[] arguments) {
            this.unicacityAddon = unicacityAddon;
            this.arguments = arguments;
            this.tabCompletionMap = new HashMap<>();
            this.tabCompletionFromIndexMap = new HashMap<>();
        }

        public Builder addAtIndex(int index, String... tabCompletion) {
            return this.addAtIndex(index, Arrays.stream(tabCompletion).collect(Collectors.toList()));
        }

        public Builder addAtIndex(int index, List<String> tabCompletion) {
            List<String> tabCompletionList = this.tabCompletionMap.getOrDefault(index, new ArrayList<>());
            tabCompletionList.addAll(tabCompletion);
            this.tabCompletionMap.put(index, tabCompletionList);
            return this;
        }

        public Builder addToAllFromIndex(int index, String... tabCompletion) {
            return this.addToAllFromIndex(index, Arrays.stream(tabCompletion).collect(Collectors.toList()));
        }

        public Builder addToAllFromIndex(int index, List<String> tabCompletion) {
            List<String> tabCompletionFromIndexMap = this.tabCompletionFromIndexMap.getOrDefault(index, new ArrayList<>());
            tabCompletionFromIndexMap.addAll(tabCompletion);
            this.tabCompletionFromIndexMap.put(index, tabCompletionFromIndexMap);
            return this;
        }

        public List<String> build() {
            int length = this.arguments.length - 1; // -1 is a fix for auto-completion before final implementation by LabyMod
            List<String> tabCompletionList = this.tabCompletionMap.getOrDefault(length, this.unicacityAddon.utilService().getOnlinePlayers());

            // add tabCompletionFromIndexMap entry to tab completion
            this.tabCompletionFromIndexMap.entrySet().stream()
                    .filter(integerListEntry -> integerListEntry.getKey() <= length)
                    .forEach(integerListEntry -> tabCompletionList.addAll(integerListEntry.getValue()));

            String input = arguments[arguments.length - 1].toLowerCase();
            tabCompletionList.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletionList;
        }
    }
}