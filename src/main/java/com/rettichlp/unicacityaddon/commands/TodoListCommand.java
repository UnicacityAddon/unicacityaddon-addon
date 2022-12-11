package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.TodolistEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author RettichLP
 */
@UCCommand
public class TodoListCommand implements IClientCommand {

    public static List<TodolistEntry> todolist;

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
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 0) {
            todoList();
        } else if (args.length > 1 && args[0].equalsIgnoreCase("done")) {
            String todo = TextUtils.makeStringByArgs(args, " ").replace("done ", "");
            Optional<TodolistEntry> todolistEntryOptional = todolist.stream().filter(todolistEntry -> todolistEntry.getTodo().equals(todo)).findFirst();
            if (!todolistEntryOptional.isPresent()) {
                p.sendErrorMessage("Keinen Eintrag gefunden.");
                return;
            }
            int index = todolist.indexOf(todolistEntryOptional.get());
            TodolistEntry todolistEntry = todolistEntryOptional.get();
            todolistEntry.setDone(true);
            todolist.set(index, todolistEntry);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe als erledigt markiert.");
        } else if (args.length > 1 && args[0].equalsIgnoreCase("delete")) {
            String todo = TextUtils.makeStringByArgs(args, " ").replace("delete ", "");
            boolean success = todolist.removeIf(todolistEntry -> todolistEntry.getTodo().equals(todo));
            if (!success) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return;
            }
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe aus Todoliste gelöscht.");
        } else if (args.length > 2 && args[0].equalsIgnoreCase("edit") && MathUtils.isInteger(args[1])) {
            int index = Integer.parseInt(args[1]) - 1;
            if (index > todolist.size() - 1) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return;
            }
            String todo = TextUtils.makeStringByArgs(args, " ").replaceAll("(?i)edit " + args[1] + " ", "");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            todolist.set(index, todolistEntry);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe editiert.");
        } else {
            String todo = TextUtils.makeStringByArgs(args, " ");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            if (todolist.stream().anyMatch(te -> te.getTodo().equals(todo))) {
                p.sendErrorMessage("Dieses Todo gibt es bereits!");
                return;
            }
            todolist.add(todolistEntry);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe zur Todoliste hinzugefügt.");
        }
    }

    private void todoList() {
        UPlayer p = AbstractionLayer.getPlayer();
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Todoliste:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        todolist.forEach(todolistEntry -> {
            int id = todolist.indexOf(todolistEntry) + 1;
            if (todolistEntry.isDone())
                p.sendMessage(Message.getBuilder()
                        .of("» " + id + ". ").color(ColorCode.GRAY).advance()
                        .of(todolistEntry.getTodo()).color(ColorCode.AQUA).strikethrough().advance().space()
                        .of("[✐]").color(ColorCode.GOLD)
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/todo edit " + id + " " + todolistEntry.getTodo())
                                .advance().space()
                        .of("[✕]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo delete " + todolistEntry.getTodo())
                                .advance()
                        .createComponent());
            else
                p.sendMessage(Message.getBuilder()
                        .of("» " + id + ". ").color(ColorCode.GRAY).advance()
                        .of(todolistEntry.getTodo()).color(ColorCode.AQUA).advance().space()
                        .of("[✔]").color(ColorCode.GREEN)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo done " + todolistEntry.getTodo())
                                .advance().space()
                        .of("[✐]").color(ColorCode.GOLD)
                                .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/todo edit " + id + " " + todolistEntry.getTodo())
                                .advance().space()
                        .of("[✕]").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo delete " + todolistEntry.getTodo())
                                .advance()
                        .createComponent());
        });
        p.sendEmptyMessage();
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}
