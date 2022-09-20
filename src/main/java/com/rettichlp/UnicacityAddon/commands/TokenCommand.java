package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIHandler;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        return "/token [create|renew|revoke]";
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

        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        String response;
        if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("renew")) {
            response = APIHandler.getInfo(generateToken(), "/register?uuid=" + p.getUniqueID().toString());
        } else if (args[0].equalsIgnoreCase("revoke")) {
            response = APIHandler.getInfo(ConfigElements.getAPIToken(), "/revoke");
        } else {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        p.sendAPIMessage(response);
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>(Arrays.asList("create", "renew", "revoke"));
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    private static String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(strBytes);
            return (new BigInteger(digest)).toString(16);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException(var2);
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    private String generateToken() {
        String username = AbstractionLayer.getPlayer().getName();
        long currentTime = System.currentTimeMillis();
        SecureRandom randomLong = new SecureRandom();

        String token = hash(username + currentTime + randomLong).replace("-", "");
        System.out.println("[DEBUG] Token: " + token); // TODO: 20.09.2022 Remove loggin of token 

        ConfigElements.setAPIToken(token);
        return token;
    }
}