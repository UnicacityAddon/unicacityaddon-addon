package com.rettichlp.unicacityaddon.base.teamspeak.querycommand;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.Getter;
import net.labymod.addons.teamspeak.util.Request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author RettichLP
 */
@Getter
public abstract class ClientQueryCommand<T extends Response> {

    private final CompletableFuture<String> future;

    private final UnicacityAddon unicacityAddon;

    public ClientQueryCommand(UnicacityAddon unicacityAddon, String command) {
        this.unicacityAddon = unicacityAddon;
        this.future = getResponse(command);
    }

    public abstract T getResponse() throws ExecutionException, InterruptedException;

    public CompletableFuture<String> getResponse(String command) {
        CompletableFuture<String> future = new CompletableFuture<>();
        this.unicacityAddon.teamSpeakAPI().request(Request.unknown(
                command,
                response -> {
                    future.complete(response);
                    return true;
                }));

        return future;
    }
}