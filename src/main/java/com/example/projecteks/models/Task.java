package com.example.projecteks.models;

import com.example.projecteks.service.TimeCalc;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String name;
    private int id;
    private int state; // 1=to do, 2= in progress, 3 = done
    private LocalDate startDate;
    private LocalDate deadline;
    private String creationDate; //skal ´Date´ være JAVA.UTIL eller JAVA.SQL?
    private int timeEstimate;
    private double hoursOfPeriod;



    public Task() {
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public LocalDate getDeadline () {
        return deadline;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }



    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getHoursOfPeriod() {
        return hoursOfPeriod;
    }

    public void setHoursOfPeriod() {
        TimeCalc timeCalc = new TimeCalc();
        Double h = timeCalc.getAvalableWorkHours((startDate),(deadline));
        if(h != null) {
            this.hoursOfPeriod = h;
        }else {
            this.hoursOfPeriod = 0;
        }
    }

}