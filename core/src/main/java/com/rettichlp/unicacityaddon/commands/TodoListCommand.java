package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.TodolistEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;

import java.util.List;
import java.util.Optional;

/**
 * @author RettichLP
 */
@UCCommand
public class TodoListCommand extends Command {

    public TodoListCommand() {
        super("todo");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length == 0) {
            todoList();
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("done")) {
            String todo = TextUtils.makeStringByArgs(arguments, " ").replace("done ", "");
            Optional<TodolistEntry> todolistEntryOptional = FileManager.DATA.getTodolist().stream().filter(todolistEntry -> todolistEntry.getTodo().equals(todo)).findFirst();
            if (!todolistEntryOptional.isPresent()) {
                p.sendErrorMessage("Keinen Eintrag gefunden.");
                return true;
            }
            int index = FileManager.DATA.getTodolist().indexOf(todolistEntryOptional.get());
            TodolistEntry todolistEntry = todolistEntryOptional.get();
            todolistEntry.setDone(true);
            FileManager.DATA.getTodolist().set(index, todolistEntry);
            p.sendInfoMessage("Aufgabe als erledigt markiert.");
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("delete")) {
            String todo = TextUtils.makeStringByArgs(arguments, " ").replace("delete ", "");
            boolean success = FileManager.DATA.getTodolist().removeIf(todolistEntry -> todolistEntry.getTodo().equals(todo));
            if (!success) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return true;
            }
            p.sendInfoMessage("Aufgabe aus Todoliste gelöscht.");
        } else if (arguments.length > 2 && arguments[0].equalsIgnoreCase("edit") && MathUtils.isInteger(arguments[1])) {
            int index = Integer.parseInt(arguments[1]) - 1;
            if (index > FileManager.DATA.getTodolist().size() - 1) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return true;
            }
            String todo = TextUtils.makeStringByArgs(arguments, " ").replaceAll("(?i)edit " + arguments[1] + " ", "");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            FileManager.DATA.getTodolist().set(index, todolistEntry);
            p.sendInfoMessage("Aufgabe editiert.");
        } else {
            String todo = TextUtils.makeStringByArgs(arguments, " ");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            if (FileManager.DATA.getTodolist().stream().anyMatch(te -> te.getTodo().equals(todo))) {
                p.sendErrorMessage("Dieses Todo gibt es bereits!");
                return true;
            }
            FileManager.DATA.getTodolist().add(todolistEntry);
            p.sendInfoMessage("Aufgabe zur Todoliste hinzugefügt.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }

    private void todoList() {
        AddonPlayer p = UnicacityAddon.PLAYER;
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Todoliste:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        FileManager.DATA.getTodolist().forEach(todolistEntry -> {
            int id = FileManager.DATA.getTodolist().indexOf(todolistEntry) + 1;
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
}