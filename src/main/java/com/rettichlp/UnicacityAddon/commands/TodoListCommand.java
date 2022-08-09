package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.json.todo.Todolist;
import com.rettichlp.UnicacityAddon.base.json.todo.TodolistEntry;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
public class TodoListCommand extends CommandBase {

    public static Todolist todolist;

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
        if (todolist == null) return;

        if (args.length == 0) {
            todoList();
        } else if (args.length > 1 && args[0].equalsIgnoreCase("add")) {
            String todo = TextUtils.makeStringByArgs(args, " ").replace("add ", "");
            TodolistEntry todolistEntry = new TodolistEntry(todolist.getTodolistEntryList().size(), todo);
            todolist.getTodolistEntryList().add(todolistEntry);
            UnicacityAddon.saveOfflineData();
        } else if (args[0].equalsIgnoreCase("done")) {
            if (!MathUtils.isInteger(args[1])) return;
            int index = Integer.parseInt(args[1]) - 1;
            TodolistEntry todolistEntry = todolist.getTodolistEntryList().get(index);
            todolistEntry.setDone(true);
            todolist.getTodolistEntryList().set(index, todolistEntry);
            UnicacityAddon.saveOfflineData();
        } else if (args[0].equalsIgnoreCase("delete")) {
            if (!MathUtils.isInteger(args[1])) return;
            todolist.getTodolistEntryList().remove(Integer.parseInt(args[1]) - 1);
            UnicacityAddon.saveOfflineData();
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Collections.emptyList();
        if (args.length == 1) {
            tabCompletions = Arrays.asList("add", "done", "delete");
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        }
        return tabCompletions;
    }

    private void todoList() {
        UPlayer p = AbstractionLayer.getPlayer();
        p.sendMessage(Message.getBuilder()
                .of("Todoliste:").color(ColorCode.AQUA).bold().advance()
                .createComponent());
        todolist.getTodolistEntryList().forEach(todolistEntry -> p.sendMessage(Message.getBuilder()
                .of("  - ").color(ColorCode.GRAY).advance()
                .of(todolistEntry.getTodo()).color(ColorCode.GOLD).advance()
                .of("[Erledigt]").color(ColorCode.GREEN)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo done " + todolistEntry.getId())
                        .advance()
                .of("[Löschen]").color(ColorCode.RED)
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/todo delete " + todolistEntry.getId())
                        .advance()
                .createComponent()));
    }
}
