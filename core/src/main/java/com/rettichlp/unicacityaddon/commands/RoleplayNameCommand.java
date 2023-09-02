package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.Laby;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "roleplayname", aliases = {"rpname"}, onlyOnUnicacity = false, usage = "[Roleplay Name|reset|block|unblock] (Name)")
public class RoleplayNameCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public RoleplayNameCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        new Thread(() -> {
            if (arguments[0].equalsIgnoreCase("reset")) {
                this.unicacityAddon.api().sendRoleplayNameSetRequest("");
            } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("block")) {
                String minecraftUuid = Optional.ofNullable(Laby.labyAPI().minecraft().getClientPacketListener())
                        .map(clientPacketListener -> clientPacketListener.getNetworkPlayerInfo(arguments[1]))
                        .map(networkPlayerInfo -> networkPlayerInfo.profile().getUniqueId())
                        .map(uuid -> uuid.toString().replace("-", ""))
                        .orElse("");
                this.unicacityAddon.api().sendRoleplayNameBlockRequest(minecraftUuid);
            } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("unblock")) {
                String minecraftUuid = Optional.ofNullable(Laby.labyAPI().minecraft().getClientPacketListener())
                        .map(clientPacketListener -> clientPacketListener.getNetworkPlayerInfo(arguments[1]))
                        .map(networkPlayerInfo -> networkPlayerInfo.profile().getUniqueId())
                        .map(uuid -> uuid.toString().replace("-", ""))
                        .orElse("");
                this.unicacityAddon.api().sendRoleplayNameUnblockRequest(minecraftUuid);
            } else {
                String roleplayName = this.unicacityAddon.utilService().text().makeStringByArgs(arguments, " ");
                Pattern pattern = Pattern.compile("^[A-Za-z-äöüÄÖÜßâêîôûáéíóúàèìòù'\\s]{3,30}$");
                if (pattern.matcher(roleplayName).find()) {
                    this.unicacityAddon.api().sendRoleplayNameSetRequest(roleplayName);
                } else {
                    p.sendErrorMessage("Der Name muss zwischen (einschließlich) 3 und 30 Zeichen lang sein und darf nur aus Buchstaben, Bindestrichen und Apostrophen bestehen!");
                }
            }
        }).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset", "block", "unblock")
                .build();
    }
}