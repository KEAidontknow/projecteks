package com.example.projecteks.models;

public class Task {
    private String name;
    private int id;
    private int state; // 1=to do, 2= in progress, 3 = done

    private enum status {TODO, IN_PROGRESS, DONE}

    ;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Task() {

    }


    public String getName () {
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public int getId () {
        return id;
    }

    public void setId ( int id){
        this.id = id;
    }
}