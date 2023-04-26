package com.example.projecteks.models;

public class Task {
    String name;
    int id;
    //short status; // 1=to do, 2= in progress, 3 = done
    enum status {TODO, IN_PROGRESS, DONE};

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
