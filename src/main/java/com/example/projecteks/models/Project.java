package com.example.projecteks.models;

public class Project {
    private String projectName;
    private int projectId;
    private String startDate;
    private String deadline;
    private  String subProjectName;
    private  int hoursOfTasks;

    public Project() {}

    public Project(int projectId, String projectName){
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate){
        this.startDate=startDate;
    }
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public int getHoursOfTasks() {
        return hoursOfTasks;
    }

    public void setHoursOfTasks(int hoursOfTasks) {
        this.hoursOfTasks = hoursOfTasks;
    }
}