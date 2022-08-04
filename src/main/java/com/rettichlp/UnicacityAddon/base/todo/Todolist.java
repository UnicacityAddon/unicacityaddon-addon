package com.rettichlp.UnicacityAddon.base.todo;

import java.util.List;

public class Todolist {

    List<TodolistEntry> todoEntryList;

    public List<TodolistEntry> getTodoEntryList() {
        return todoEntryList;
    }

    public void setTodoEntryList(List<TodolistEntry> todoEntryList) {
        this.todoEntryList = todoEntryList;
    }
}
