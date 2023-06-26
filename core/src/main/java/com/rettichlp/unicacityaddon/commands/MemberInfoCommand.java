package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand(prefix = "memberinfo", aliases = {"mi"}, onlyOnUnicacity = false, usage = "(Fraktion)")
public class MemberInfoCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public MemberInfoCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        boolean inComplete = arguments.length < 1;

        if (inComplete) {
            this.sendMessage("/memberinfo " + p.getFaction().getFactionKey());
        }

        return inComplete;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, Arrays.stream(Faction.values()).map(Faction::getFactionKey).sorted().collect(Collectors.toList()))
                .addAtIndex(1, this.unicacityAddon.services().util().getOnlinePlayers())
                .build();
    }
}