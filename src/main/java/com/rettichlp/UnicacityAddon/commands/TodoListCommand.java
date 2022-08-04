package com.rettichlp.UnicacityAddon.commands;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.todo.Todolist;
import com.rettichlp.UnicacityAddon.base.todo.TodolistEntry;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class TodoListCommand extends CommandBase {

    Todolist TODOLIST = null;

    @Override
    @Nonnull
    public String getName() {
        return "todo";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/todo [add|done|delete] [Todo]";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        try {
            File todoDataFile = FileManager.getTodoDataFile();
            if (todoDataFile == null) return;
            BufferedReader reader = Files.newBufferedReader(Paths.get(todoDataFile.getAbsolutePath()));
            Gson g = new Gson();
            TODOLIST = g.fromJson(reader, Todolist.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (args.length == 0) {
            p.sendMessage(Message.getBuilder().of("TODO:").advance().createComponent());

            TODOLIST.getTodoEntryList().forEach(todolistEntry -> p.sendMessage(Message.getBuilder().of(todolistEntry.getTodo())
                    .advance().createComponent()));
        } else {
            if (args[1].equalsIgnoreCase("add")) {
                String todo = TextUtils.makeStringByArgs(args, " ").replace("add ", "");
                TodolistEntry todolistEntry = new TodolistEntry(todo);
                TODOLIST.getTodoEntryList().add(todolistEntry);
                saveTodolist();
            } else if (args[1].equalsIgnoreCase("done")) {
                String todo = TextUtils.makeStringByArgs(args, " ").replace("done ", "");
                TodolistEntry tle = TODOLIST.getTodoEntryList().stream().filter(todolistEntry -> todolistEntry.getTodo().equals(todo)).collect(Collectors.toList()).get(0);
                tle.setDone(true);
                saveTodolist();
            }
        }
    }

    private void saveTodolist() {
        try {
            File todoDataFile = FileManager.getTodoDataFile();
            if (todoDataFile == null) return;
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(todoDataFile.getAbsolutePath()));
            Gson g = new Gson();
            g.toJson(TODOLIST, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Collections.emptyList();
        if (args.length == 1) {
            tabCompletions = Arrays.asList("add", "done", "delete");
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        } else if (args.length == 2) {
            tabCompletions = TODOLIST.getTodoEntryList().stream().map(TodolistEntry::getTodo).sorted().collect(Collectors.toList());
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        }
        return tabCompletions;
    }
}
