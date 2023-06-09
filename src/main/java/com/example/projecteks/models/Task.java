package com.example.projecteks.models;

import com.example.projecteks.utilities.TimeCalc;

import java.time.LocalDate;

public class Task {
    private int projectId; // FK
    private String name;
    private int id;
    private int state; // 1=to do, 2= in progress, 3 = done
    private LocalDate startDate;
    private LocalDate deadline;
    private String creationDate; //skal ´Date´ være JAVA.UTIL eller JAVA.SQL?
    private int timeEstimate;
    private double hoursOfPeriod;
    private int empNeeded;
    private int star;


    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }




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
        Double h = TimeCalc.getAvalableWorkHours((startDate),(deadline));
        if(h != null) {
            this.hoursOfPeriod = h;
        }else {
            this.hoursOfPeriod = 0;
        }
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEmpNeeded() {
        return empNeeded;
    }

    public void setEmpNeeded() {

        this.empNeeded = (int)(timeEstimate/hoursOfPeriod)+1;
    }
}