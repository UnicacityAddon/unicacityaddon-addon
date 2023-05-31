package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.file.TodolistEntry;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.component.event.ClickEvent;

import java.util.List;
import java.util.Optional;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "todo")
public class TodoListCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TodoListCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            todoList();
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("done")) {
            String todo = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ").replace("done ", "");
            Optional<TodolistEntry> todolistEntryOptional = this.unicacityAddon.services().fileService().data().getTodolist().stream().filter(todolistEntry -> todolistEntry.getTodo().equals(todo)).findFirst();
            if (!todolistEntryOptional.isPresent()) {
                p.sendErrorMessage("Keinen Eintrag gefunden.");
                return true;
            }
            int index = this.unicacityAddon.services().fileService().data().getTodolist().indexOf(todolistEntryOptional.get());
            TodolistEntry todolistEntry = todolistEntryOptional.get();
            todolistEntry.setDone(true);
            this.unicacityAddon.services().fileService().data().getTodolist().set(index, todolistEntry);
            p.sendInfoMessage("Aufgabe als erledigt markiert.");
        } else if (arguments.length > 1 && arguments[0].equalsIgnoreCase("delete")) {
            String todo = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ").replace("delete ", "");
            boolean success = this.unicacityAddon.services().fileService().data().getTodolist().removeIf(todolistEntry -> todolistEntry.getTodo().equals(todo));
            if (!success) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return true;
            }
            p.sendInfoMessage("Aufgabe aus Todoliste gelöscht.");
        } else if (arguments.length > 2 && arguments[0].equalsIgnoreCase("edit") && MathUtils.isInteger(arguments[1])) {
            int index = Integer.parseInt(arguments[1]) - 1;
            if (index > this.unicacityAddon.services().fileService().data().getTodolist().size() - 1) {
                p.sendErrorMessage("Keinen Eintrag mit dieser ID gefunden.");
                return true;
            }
            String todo = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ").replaceAll("(?i)edit " + arguments[1] + " ", "");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            this.unicacityAddon.services().fileService().data().getTodolist().set(index, todolistEntry);
            p.sendInfoMessage("Aufgabe editiert.");
        } else {
            String todo = this.unicacityAddon.utils().textUtils().makeStringByArgs(arguments, " ");
            TodolistEntry todolistEntry = new TodolistEntry(todo);
            if (this.unicacityAddon.services().fileService().data().getTodolist().stream().anyMatch(te -> te.getTodo().equals(todo))) {
                p.sendErrorMessage("Dieses Todo gibt es bereits!");
                return true;
            }
            this.unicacityAddon.services().fileService().data().getTodolist().add(todolistEntry);
            p.sendInfoMessage("Aufgabe zur Todoliste hinzugefügt.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }

    private void todoList() {
        AddonPlayer p = this.unicacityAddon.player();
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Todoliste:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        this.unicacityAddon.services().fileService().data().getTodolist().forEach(todolistEntry -> {
            int id = this.unicacityAddon.services().fileService().data().getTodolist().indexOf(todolistEntry) + 1;
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