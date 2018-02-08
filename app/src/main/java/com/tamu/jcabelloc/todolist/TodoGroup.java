package com.tamu.jcabelloc.todolist;

/**
 * Created by jcabelloc on 2/7/2018.
 */

public class TodoGroup {
    int id;
    String name;

    public TodoGroup() {
    }

    public TodoGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public TodoGroup(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
