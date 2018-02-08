package com.tamu.jcabelloc.todolist;

/**
 * Created by jcabelloc on 2/7/2018.
 */

public class TodoTask {
    int id;
    int idGroup;
    String name;
    int status;

    public TodoTask(int id, int idGroup, String name, int status) {
        this.id = id;
        this.idGroup = idGroup;
        this.name = name;
        this.status = status;
    }

    public TodoTask(int idGroup, String name, int status) {
        this.idGroup = idGroup;
        this.name = name;
        this.status = status;
    }

    public TodoTask() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
