package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;

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

    public static Builder getBuilder(String[] args) {
        return new Builder(args);
    }

    public static class Builder {
        private final String[] args;
        private final Map<Integer, List<String>> tabCompletionMap;
        private final Map<Integer, List<String>> tabCompletionFromIndexMap;

        public Builder(String[] args) {
            this.args = args;
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
            int length = this.args.length;
            List<String> tabCompletionList = this.tabCompletionMap.getOrDefault(length, ForgeUtils.getOnlinePlayers());

            // add tabCompletionFromIndexMap entry to tab completion
            this.tabCompletionFromIndexMap.entrySet().stream()
                    .filter(integerListEntry -> integerListEntry.getKey() <= length)
                    .forEach(integerListEntry -> tabCompletionList.addAll(integerListEntry.getValue()));

            String input = args[args.length - 1].toLowerCase();
            tabCompletionList.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletionList;
        }
    }
}