package com.example.projecteks.models;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String name;
    private int id;
    private int state; // 1=to do, 2= in progress, 3 = done
    private LocalDate deadline;
    private Date creationDate; //skal ´Date´ være JAVA.UTIL eller JAVA.SQL?


    private enum status {TODO, IN_PROGRESS, DONE}

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Task() {
        this.creationDate = new Date();

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

    public LocalDate getDeadline () {
        return deadline;
    }

    public void setDealine ( LocalDate Deadline){
        this.deadline = Deadline;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}