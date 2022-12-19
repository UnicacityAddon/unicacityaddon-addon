package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandFuture;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;

import java.util.StringJoiner;

/**
 * @author Fuzzlemann
 */
public abstract class BaseCommand<T extends CommandResponse> {

    private final String command;
    private final CommandFuture<T> future;

    protected BaseCommand(String command) {
        this.command = command;
        this.future = new CommandFuture<>();
    }

    public BaseCommand<T> execute() {
        return execute(TSClientQuery.getInstance());
    }

    public BaseCommand<T> execute(TSClientQuery clientQuery) {
        clientQuery.executeCommand(this);
        return this;
    }

    public String getCommand() {
        return command;
    }

    public CommandFuture<T> getResponseFuture() {
        return future;
    }

    public T getResponse() {
        if (!future.isDone())
            execute();

        return future.get();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseCommand.class.getSimpleName() + "[", "]")
                .add("command='" + command + "'")
                .add("future=" + future)
                .toString();
    }
}
