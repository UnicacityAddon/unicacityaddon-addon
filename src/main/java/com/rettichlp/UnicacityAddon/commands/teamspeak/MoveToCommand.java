package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Client;
import de.fuzzlemann.ucutils.base.command.Command;
import de.fuzzlemann.ucutils.base.text.Message;
import de.fuzzlemann.ucutils.base.text.TextUtils;
import de.fuzzlemann.ucutils.teamspeak.CommandResponse;
import de.fuzzlemann.ucutils.teamspeak.TSUtils;
import de.fuzzlemann.ucutils.teamspeak.commands.ClientMoveCommand;
import de.fuzzlemann.ucutils.teamspeak.objects.Client;
import de.fuzzlemann.ucutils.utils.mcapi.MojangAPI;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * @author Fuzzlemann
 */
@SideOnly(Side.CLIENT)
public class MoveToCommand {

    @Command(value = "moveto", usage = "/%label% [Spieler]", async = true)
    public boolean onCommand(String name) {
        List<String> names = MojangAPI.getEarlierNames(name);
        List<Client> clients = TSUtils.getClientsByName(names);
        if (clients.isEmpty()) {
            TextUtils.error("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return true;
        }

        Client client = clients.get(0);
        CommandResponse response = new ClientMoveCommand(client.getChannelID(), TSUtils.getMyClientID()).getResponse();
        if (!response.succeeded()) {
            TextUtils.error("Das Bewegen ist fehlgeschlagen.");
            return true;
        }

        Message.builder()
                .prefix()
                .of("Du hast dich zu dem Channel von ").color(TextFormatting.GRAY).advance()
                .of(name).color(TextFormatting.BLUE).advance()
                .of(" bewegt.").color(TextFormatting.GRAY).advance()
                .send();
        return true;
    }
}
