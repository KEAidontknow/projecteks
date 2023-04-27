package com.example.projecteks.reposetory;

import com.example.projecteks.models.Task;

import java.util.ArrayList;

public interface DatabaseInterface {
    ArrayList<Task> getTasks();


    void addTask(Task task);

    void removeTask(int taskId);

    void updateTask(Task task);
}
