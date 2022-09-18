package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.json.TodolistEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class TodoListCommand extends CommandBase {

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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 0) {
            todoList();
        } else if (args.length > 1 && args[0].equalsIgnoreCase("add")) {
            String todo = TextUtils.makeStringByArgs(args, " ").replaceAll("(?i)add ", "");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            todolist.add(todolistEntry);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe zur Todoliste hinzugefügt.");
        } else if (args[0].equalsIgnoreCase("done") && MathUtils.isInteger(args[1])) {
            int index = Integer.parseInt(args[1]) - 1;
            if (index > todolist.size() - 1) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return;
            }
            TodolistEntry todolistEntry = todolist.get(index);
            todolistEntry.setDone(true);
            todolist.set(index, todolistEntry);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe als erledigt markiert.");
            p.sendChatMessage("/todo");
        } else if (args[0].equalsIgnoreCase("delete") && MathUtils.isInteger(args[1])) {
            int index = Integer.parseInt(args[1]) - 1;
            if (index > todolist.size() - 1) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return;
            }
            todolist.remove(index);
            FileManager.saveData();
            p.sendInfoMessage("Aufgabe aus Todoliste gelöscht.");
            p.sendChatMessage("/todo");
        } else if (args[0].equalsIgnoreCase("edit") && MathUtils.isInteger(args[1])) {
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
            p.sendChatMessage("/todo");
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
            if (todolistEntry.isDone()) p.sendMessage(Message.getBuilder()
                    .of("» " + id + ". ").color(ColorCode.GRAY).advance()
                    .of(todolistEntry.getTodo()).color(ColorCode.AQUA).strikethrough().advance()
                    .space()
                    .of("[✐]").color(ColorCode.GOLD)
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/todo edit " + id + " ")
                            .advance().space()
                    .of("[✕]").color(ColorCode.RED)
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo delete " + id)
                            .advance()
                    .createComponent());
            else p.sendMessage(Message.getBuilder()
                    .of("» " + id + ". ").color(ColorCode.GRAY).advance()
                    .of(todolistEntry.getTodo()).color(ColorCode.AQUA).advance()
                    .space()
                    .of("[✔]").color(ColorCode.GREEN)
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo done " + id)
                            .advance()
                    .space()
                    .of("[✐]").color(ColorCode.GOLD)
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/todo edit " + id)
                            .advance().space()
                    .of("[✕]").color(ColorCode.RED)
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo delete " + id)
                            .advance()
                    .createComponent());
        });
        p.sendEmptyMessage();
    }
}
