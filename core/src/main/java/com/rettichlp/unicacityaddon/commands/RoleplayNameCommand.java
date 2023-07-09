package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "roleplayname", aliases = {"rpname"}, usage = "[Name]", onlyOnUnicacity = false)
public class RoleplayNameCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public RoleplayNameCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            sendUsage();
            return true;
        }

        new Thread(() -> {
            String roleplayName = this.unicacityAddon.utilService().text().makeStringByArgs(arguments, " ");

            Pattern pattern = Pattern.compile("^[A-Za-z-äöüÄÖÜßâêîôûáéíóúàèìòù'\\s]{3,30}$");
            if (pattern.matcher(roleplayName).matches()) {
                String info = this.unicacityAddon.api().sendRoleplayNameSetRequest(roleplayName).getInfo();
                p.sendAPIMessage(info, true);
            } else {
                p.sendErrorMessage("Der Name muss zwischen (einschließlich) 3 und 30 Zeichen lang sein und darf nur aus Buchstaben, Bindestrichen und Apostrophen bestehen!");
            }

        }).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}
