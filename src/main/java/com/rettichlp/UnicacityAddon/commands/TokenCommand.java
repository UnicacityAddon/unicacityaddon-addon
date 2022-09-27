package com.rettichlp.UnicacityAddon.commands;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author RettichLP
 */
@UCCommand
public class TokenCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "token";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/token (renew|revoke)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("renew"))) {
            String authToken = UnicacityAddon.MINECRAFT.getSession().getToken();
            createToken(p.getUniqueID(), authToken);
            JsonObject response = APIRequest.sendTokenCreateRequest(authToken);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("revoke")) {
            JsonObject response = APIRequest.sendTokenRevokeRequest();
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions.add("renew");
            tabCompletions.add("revoke");
        }
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

    private void createToken(UUID uniqueID, String authToken) {
        String SECRET_SALT = "423WhKRMTfRv4mn6u8hLcPj7bYesKh4Ex4yRErYuW4KsgYjpo35nSU11QYj3OINAJwcd0TPDD6AkqhSq";
        String hash = hash(uniqueID.toString().replace("-", "") + SECRET_SALT + authToken);
        ConfigElements.setAPIToken(hash);
    }

    public static String hash(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            // return the HashText
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}